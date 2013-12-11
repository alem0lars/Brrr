#include <arpa/inet.h>
#include <openssl/bio.h>
#include <openssl/buffer.h>
#include <openssl/evp.h>
#include <sys/select.h>
#include <sys/stat.h>
#include <broccoli.h>
#include <errno.h>
#include <json.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "ruby.h"


// { Constants

#define SELECT_TIMEOUT_SECS 8

// }


// { Global variables

// Defining a space for information and references about the module to be stored internally
VALUE BroccoliReader = Qnil;
VALUE global_lambda = Qnil;

// }


// { Prototypes

// Prototype for the initialization method - Ruby calls this, not you
void Init_broccoli_reader();
// Wrapper around start_reading
VALUE method_start_reading(VALUE self, VALUE v_addr, VALUE v_port, VALUE lambda);
// Starts reading from broccoli
static int start_reading(struct in_addr bro_addr, int bro_port);
// Broccoli loop to process the input coming from the broccoli interface
static int process_input_loop(BroConn* bc);
// Fetches the connection identifiers
static int fetch_conn_ids(BroRecord* conn, BroString** b_conn_uid_p, BroAddr** b_orig_addr_p, BroPort** b_orig_port_p, BroAddr** b_resp_addr_p, BroPort** b_resp_port_p);
// Utility for encoding using base64
static int base64_encode(const char* str, unsigned int str_len, char** str_enc_p);
// Broccoli event handler
static void on_tcp_contents(BroConn* bc, void* user_data, BroRecord* conn, uint64* is_orig, uint64* seq, BroString* contents);

// }


// { Functions

void Init_broccoli_reader() {
    BroccoliReader = rb_define_module("BroccoliReader");
    rb_define_method(BroccoliReader, "start_reading", method_start_reading, 3);
}


VALUE method_start_reading(VALUE self, VALUE v_addr, VALUE v_port, VALUE lambda) {
    char* addr;
    unsigned int port;
    struct in_addr bro_addr;
    int bro_port;

    global_lambda = lambda;

    addr = RSTRING_PTR(v_addr);
    port = NUM2UINT(v_port);

    if (inet_pton(AF_INET, addr, &bro_addr) != 1) {
        if (errno != 0) {
            perror("Converting address");
        } else {
            fprintf(stderr, "Cannot convert address.\n");
        }
        return INT2NUM(-2);
    }

    bro_port = htons(port);

    fprintf(stderr, "Parsed args: %s:%d/tcp\n", addr, port);

    if (start_reading(bro_addr, bro_port) < 0) {
        return INT2NUM(-1);
    }

    return INT2NUM(0);
}


static int start_reading(struct in_addr bro_addr, int bro_port) {
    int ret = 0;
    BroConn* bc = NULL;

    //bro_debug_messages = 0; // TODO: for debug
    //bro_debug_calltrace = 0; // TODO: for debug

    bro_init(NULL);

    if ( (ret == 0) &&
        // Obtain a connection handle.
        !(bc = bro_conn_new(&bro_addr, bro_port, BRO_CFLAG_RECONNECT | BRO_CFLAG_ALWAYS_QUEUE)) ) {
        fprintf(stderr, "Cannot obtain connection handler.\n");
        ret = -1;
    }

    if (ret == 0) {
        // Register event handlers.
        bro_conn_set_class(bc, "brahkoli");
        //bro_event_registry_add(bc, "new_connection", (BroEventFunc) &on_new_connection, NULL);
        bro_event_registry_add(bc, "tcp_contents", (BroEventFunc) &on_tcp_contents, NULL);
    }

    if ( (ret == 0) &&
        // Connect to the peer.
        (!bro_conn_connect(bc)) ) {
        fprintf(stderr, "Cannot connect to Bro.\n");
        ret = -2;
    }

    // We are already connected.
    // From now on, new event handler registration must be done in this way:
    //   1. event_registry_add( ... );
    //   2. bro_event_registry_request(bc);

    if ( (ret == 0) &&
        // Send and receive events.
        (process_input_loop(bc) < 0) ) {
        ret = -1;
    }

    if (bc != NULL) {
        // Disconnect from Bro peer and clean up connection.
        bro_conn_delete(bc);
    }
    return ret;
}


static int process_input_loop(BroConn* bc) {
    int bro_fd;
    struct timeval timeout;
    fd_set master_fdset, work_fdset;
    int ready_count;

    bro_fd = bro_conn_get_fd(bc);

    FD_ZERO(&master_fdset);
    FD_SET(bro_fd, &master_fdset);

    do {
        memcpy(&work_fdset, &master_fdset, sizeof(fd_set));
        timeout.tv_sec  = SELECT_TIMEOUT_SECS;
        timeout.tv_usec = 0;

        ready_count = select(bro_fd + 1, &work_fdset, NULL, NULL, &timeout);

        if (ready_count < 0) {
            perror("Waiting for bro input: select()");
            return -1;
        } else if (ready_count == 0) {
            fprintf(stderr, "Waiting for Bro input: select() timed out.\n");
            // Don't return, redo select().
        } else {
            bro_conn_process_input(bc);
        }
    } while (1);
}


static int fetch_conn_ids(BroRecord* b_conn, BroString** b_conn_uid_p,
    BroAddr** b_orig_addr_p, BroPort** b_orig_port_p,
    BroAddr** b_resp_addr_p, BroPort** b_resp_port_p) {

    BroRecord* b_conn_id;
    int bro_type;

    bro_type = BRO_TYPE_STRING;
    *b_conn_uid_p = bro_record_get_named_val(b_conn, "uid", &bro_type); // conn$uid
    if (*b_conn_uid_p == NULL) {
        fprintf(stderr, "Cannot get conn$uid.\n");
        return -1;
    }
    bro_type = BRO_TYPE_RECORD;
    b_conn_id = bro_record_get_named_val(b_conn, "id", &bro_type); // conn$id
    if (b_conn_id == NULL) {
        fprintf(stderr, "Cannot get conn$id.\n");
        return -2;
    }
    bro_type = BRO_TYPE_IPADDR;
    *b_orig_addr_p = bro_record_get_named_val(b_conn_id, "orig_h", &bro_type); // conn$id$orig_h
    if (*b_orig_addr_p == NULL) {
        fprintf(stderr, "Cannot get conn$id$orig_h.\n");
        return -3;
    }
    bro_type = BRO_TYPE_PORT;
    *b_orig_port_p = bro_record_get_named_val(b_conn_id, "orig_p", &bro_type); // conn$id$orig_p
    if (*b_orig_port_p == NULL) {
        fprintf(stderr, "Cannot get conn$id$orig_p.\n");
        return -4;
    }
    bro_type = BRO_TYPE_IPADDR;
    *b_resp_addr_p = bro_record_get_named_val(b_conn_id, "resp_h", &bro_type); // conn$id$resp_h
    if (*b_resp_addr_p == NULL) {
        fprintf(stderr, "Cannot get conn$id$resp_h.\n");
        return -5;
    }
    bro_type = BRO_TYPE_PORT;
    *b_resp_port_p = bro_record_get_named_val(b_conn_id, "resp_p", &bro_type); // conn$id$resp_p
    if (*b_resp_port_p == NULL) {
        fprintf(stderr, "Cannot get conn$id$resp_p.\n");
        return -6;
    }

    return 0;
}


static int base64_encode(const char* str, unsigned int str_len, char** str_enc_p) {
    char *buff;

    BIO *bmem, *b64;
    BUF_MEM *bptr;

    b64 = BIO_new(BIO_f_base64());
    bmem = BIO_new(BIO_s_mem());
    b64 = BIO_push(b64, bmem);
    BIO_write(b64, str, str_len);
    BIO_flush(b64);
    BIO_get_mem_ptr(b64, &bptr);

    buff = (char*) malloc(bptr->length);
    memcpy(buff, bptr->data, bptr->length-1);
    buff[bptr->length-1] = 0;

    BIO_free_all(b64);

    *str_enc_p = buff;

    return 0;
}


static void on_tcp_contents(BroConn* bc, void* user_data, BroRecord* conn, uint64* is_orig, uint64* seq, BroString* contents) {
    BroString* b_conn_uid;
    BroAddr* b_orig_addr; BroPort* b_orig_port;
    BroAddr* b_resp_addr; BroPort* b_resp_port;
    char orig_addr_str[INET6_ADDRSTRLEN];
    char resp_addr_str[INET6_ADDRSTRLEN];
    char *data_b64;
    json_object* j_obj;
    json_object* j_originator;
    json_object* j_responder;
    char* out_jstr;
    VALUE r_str;

    fprintf(stderr, "[TCP_CONTENTS]\n");

    // { Get main infos about the connection.

    if (fetch_conn_ids(conn, &b_conn_uid,
        &b_orig_addr, &b_orig_port, &b_resp_addr, &b_resp_port) != 0) {
        fprintf(stderr, "Cannot get ids from connection.\n");
        exit(EXIT_FAILURE);
    }

    inet_ntop(AF_INET6, b_orig_addr->addr, orig_addr_str, INET6_ADDRSTRLEN);
    inet_ntop(AF_INET6, b_resp_addr->addr, resp_addr_str, INET6_ADDRSTRLEN);

    fprintf(stderr, "\t[uid: %s] [orig: %s:%lu] %s [resp: %s:%lu]\n",
        bro_string_get_data(b_conn_uid),
        orig_addr_str, b_orig_port->port_num,
        (*is_orig)? "-->" : "<--",
        resp_addr_str, b_resp_port->port_num
    );

    // }

    // { Get data.

    // This cannot be handled as a normal NULL-terminated string, as the payload
    // can contain NULLs. Bro already handles this, not stopping at the first NULL.
    if (base64_encode((char*) contents->str_val, contents->str_len, &data_b64) != 0) {
        fprintf(stderr, "Cannot encode data in base64.\n");
        exit(EXIT_FAILURE);
    }

    // }

    // { Create and pass the JSON string to the ruby block.

    j_obj = json_object_new_object();

    // Add connection id.
    json_object_object_add(j_obj, "connection_id", json_object_new_string(bro_string_get_data(b_conn_uid)));

    // Add originator.
    j_originator = json_object_new_object();
    json_object_object_add(j_originator, "addr", json_object_new_string(orig_addr_str));
    json_object_object_add(j_originator, "port", json_object_new_int(b_orig_port->port_num));
    json_object_object_add(j_obj, "originator", j_originator);

    // Add responder.
    j_responder = json_object_new_object();
    json_object_object_add(j_responder, "addr", json_object_new_string(resp_addr_str));
    json_object_object_add(j_responder, "port", json_object_new_int(b_resp_port->port_num));
    json_object_object_add(j_obj, "responder", j_responder);

    // Add data, data direction, and sequence number.
    json_object_object_add(j_obj, "data", json_object_new_string(data_b64));
    json_object_object_add(j_obj, "seq_num", json_object_new_int(*seq));
    json_object_object_add(j_obj, "is_originator_data", json_object_new_boolean(*is_orig));

    out_jstr = json_object_to_json_string(j_obj);

    // Call ruby block with out_jstr as argument.
    rb_funcall(global_lambda, rb_intern("call"), 1, rb_str_new2(out_jstr));

    // }

    // Cleanup.
    json_object_put(j_obj);
    free(data_b64);
    bc = NULL;
    user_data = NULL;
    return;
}

// }
