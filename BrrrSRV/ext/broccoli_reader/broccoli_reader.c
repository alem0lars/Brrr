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

#define SELECT_TIMEOUT_SECS 3600

// }


// { Global variables

// Defining a space for information and references about the module to be stored internally
VALUE BroccoliReader = Qnil;

FILE* fifo_file;

// }


// { Prototypes

// Prototype for the initialization method - Ruby calls this, not you
void Init_broccoli_reader();
// Wrapper around start_reading
VALUE method_start_reading(VALUE self, VALUE v_addr, VALUE v_port, VALUE v_fifo_path);
// Starts reading from broccoli
int start_reading(struct in_addr bro_addr, int bro_port, FILE* fifo_file);
// Broccoli loop to process the input coming from the broccoli interface
int process_input_loop(BroConn* bc);
// Fetches the connection identifiers
int fetch_conn_ids(BroRecord* conn, BroString** b_conn_uid_p, BroAddr** b_orig_addr_p, BroPort** b_orig_port_p, BroAddr** b_resp_addr_p, BroPort** b_resp_port_p);
// Utility for encoding using base64
int base64_encode(const char* str, unsigned int str_len, char** str_enc_p);
// Broccoli event handler
void on_tcp_contents(BroConn* bc, void* user_data, BroRecord* conn, uint64* is_orig, uint64* seq, BroString* contents);

// }


// { Functions

void Init_broccoli_reader() {
    printf("CIAO\n");
    BroccoliReader = rb_define_module("BroccoliReader");
    printf("CIAOCIAO\n");
    rb_define_method(BroccoliReader, "start_reading", method_start_reading, 3);
    printf("CIAOCIAOCIAO\n");
}

VALUE method_start_reading(VALUE self, VALUE v_addr, VALUE v_port, VALUE v_fifo_path) {
    char* addr;
    unsigned int port;
    char* fifo_path;
    struct in_addr bro_addr;
    int bro_port;

    addr = RSTRING_PTR(v_addr);
    port = NUM2UINT(v_port);
    fifo_path = RSTRING_PTR(v_fifo_path);

    fprintf(stdout, "fifo_path = %s\n", fifo_path);

    if (inet_pton(AF_INET, addr, &bro_addr) != 1) {
        if (errno != 0) {
            perror("Converting address");
        } else {
            fprintf(stdout, "Cannot convert address.\n");
        }
        return INT2NUM(-2);
    }

    bro_port = htons(port);

    fifo_file = fopen(fifo_path, "w");
    if (fifo_file == NULL) {
        perror("Cannot open fifo.\n");
        return INT2NUM(-3);
    }

    if (start_reading(bro_addr, bro_port, fifo_file) < 0) {
        return INT2NUM(-1);
    }

    return INT2NUM(0);
}

int start_reading(struct in_addr bro_addr, int bro_port, FILE* fifo_file) {
    int ret = 0;
    BroConn* bc = NULL;

    //bro_debug_messages = 0; // TODO: for debug
    //bro_debug_calltrace = 0; // TODO: for debug

    bro_init(NULL);

    if ( (ret == 0) &&
        // Obtain a connection handle.
        !(bc = bro_conn_new(&bro_addr, bro_port, BRO_CFLAG_RECONNECT | BRO_CFLAG_ALWAYS_QUEUE)) ) {
        fprintf(stdout, "Cannot obtain connection handler.\n");
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
        fprintf(stdout, "Cannot connect to Bro.\n");
        ret = -2;
    }

    // We are already connected.
    // From now on, new event handler registration must be done in this way:
    // 1. event_registry_add( ... );
    // 2. bro_event_registry_request(bc);

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

int process_input_loop(BroConn* bc) {
    int bro_fd;
    struct timeval timeout;
    fd_set master_fdset, work_fdset;
    int ready_count;

    bro_fd = bro_conn_get_fd(bc);
    timeout.tv_sec  = SELECT_TIMEOUT_SECS;
    timeout.tv_usec = 0;

    FD_ZERO(&master_fdset);
    FD_SET(bro_fd, &master_fdset);

    do {
        memcpy(&work_fdset, &master_fdset, sizeof(fd_set));

        ready_count = select(bro_fd + 1, &work_fdset, NULL, NULL, &timeout);

        if (ready_count < 0) {
            perror("Waiting for bro input: select()");
            return -1;
        } else if (ready_count == 0) {
            fprintf(stdout, "Waiting for Bro input: select() timed out.\n");
            return 1;
        } else {
            bro_conn_process_input(bc);
        }
    } while (1);
}

int fetch_conn_ids(BroRecord* b_conn, BroString** b_conn_uid_p,
    BroAddr** b_orig_addr_p, BroPort** b_orig_port_p,
    BroAddr** b_resp_addr_p, BroPort** b_resp_port_p) {

    BroRecord* b_conn_id;
    int bro_type;

    bro_type = BRO_TYPE_STRING;
    *b_conn_uid_p = bro_record_get_named_val(b_conn, "uid", &bro_type); // conn$uid
    if (*b_conn_uid_p == NULL) {
        fprintf(stdout, "Cannot get conn$uid.\n");
        return -1;
    }
    bro_type = BRO_TYPE_RECORD;
    b_conn_id = bro_record_get_named_val(b_conn, "id", &bro_type); // conn$id
    if (b_conn_id == NULL) {
        fprintf(stdout, "Cannot get conn$id.\n");
        return -2;
    }
    bro_type = BRO_TYPE_IPADDR;
    *b_orig_addr_p = bro_record_get_named_val(b_conn_id, "orig_h", &bro_type); // conn$id$orig_h
    if (*b_orig_addr_p == NULL) {
        fprintf(stdout, "Cannot get conn$id$orig_h.\n");
        return -3;
    }
    bro_type = BRO_TYPE_PORT;
    *b_orig_port_p = bro_record_get_named_val(b_conn_id, "orig_p", &bro_type); // conn$id$orig_p
    if (*b_orig_port_p == NULL) {
        fprintf(stdout, "Cannot get conn$id$orig_p.\n");
        return -4;
    }
    bro_type = BRO_TYPE_IPADDR;
    *b_resp_addr_p = bro_record_get_named_val(b_conn_id, "resp_h", &bro_type); // conn$id$resp_h
    if (*b_resp_addr_p == NULL) {
        fprintf(stdout, "Cannot get conn$id$resp_h.\n");
        return -5;
    }
    bro_type = BRO_TYPE_PORT;
    *b_resp_port_p = bro_record_get_named_val(b_conn_id, "resp_p", &bro_type); // conn$id$resp_p
    if (*b_resp_port_p == NULL) {
        fprintf(stdout, "Cannot get conn$id$resp_p.\n");
        return -6;
    }

    return 0;
}

int base64_encode(const char* str, unsigned int str_len, char** str_enc_p)
{
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

// int base64_encode(const char* str, unsigned int str_len, char** str_enc_p) {
//     BIO *bio, *b64;
//     FILE* stream;
//     int enc_size;

//     // TODO: add checks.

//     enc_size = 4 * ceil((double) str_len / 3.0);
//     *str_enc_p = (char *)malloc(enc_size + 1);
//     stream = fmemopen(*str_enc_p, enc_size + 1, "w");

//     b64 = BIO_new(BIO_f_base64());
//     bio = BIO_new_fp(stream, BIO_NOCLOSE);
//     bio = BIO_push(b64, bio);
//     BIO_set_flags(bio, BIO_FLAGS_BASE64_NO_NL); //Ignore newlines - write everything in one line
//     BIO_write(bio, str, str_len);

//     BIO_flush(bio);
//     BIO_free_all(bio);
//     fclose(stream);
//     return 0;
// }

void on_tcp_contents(BroConn* bc, void* user_data, BroRecord* conn, uint64* is_orig, uint64* seq, BroString* contents) {
    BroString* b_conn_uid;
    BroAddr* b_orig_addr; BroPort* b_orig_port;
    BroAddr* b_resp_addr; BroPort* b_resp_port;
    char orig_addr_str[INET6_ADDRSTRLEN];
    char resp_addr_str[INET6_ADDRSTRLEN];
    char *data_b64;
    json_object* j_obj;
    json_object* j_origin;
    json_object* j_responder;
    char* out_jstr;
    int ret;

    fprintf(stdout, "[TCP_CONTENTS]\n");

    // { Get main infos about the connection.

    if (fetch_conn_ids(conn, &b_conn_uid,
        &b_orig_addr, &b_orig_port, &b_resp_addr, &b_resp_port) != 0) {
        fprintf(stdout, "Cannot get ids from connection.\n");
        exit(EXIT_FAILURE);
    }

    inet_ntop(AF_INET6, b_orig_addr->addr, orig_addr_str, INET6_ADDRSTRLEN);
    inet_ntop(AF_INET6, b_resp_addr->addr, resp_addr_str, INET6_ADDRSTRLEN);

    fprintf(stdout, "\t[uid: %s] [orig: %s:%lu] %s [resp: %s:%lu]\n",
        bro_string_get_data(b_conn_uid),
        orig_addr_str, b_orig_port->port_num,
        (*is_orig)? "-->" : "<--",
        resp_addr_str, b_resp_port->port_num
    );

    // }

    // { Get data.

    // This cannot be handled as a normal NULL-terminated string, as the payload
    // can contain NULLs. Bro already handles this, not stopping at the first NULL.
    if (base64_encode(contents->str_val, contents->str_len, &data_b64) != 0) {
        fprintf(stdout, "Cannot encode data in base64.\n");
        exit(EXIT_FAILURE);
    }

    // }

    // { Create and dump json to the fifo.

    j_obj = json_object_new_object();

    // Add origin.
    j_origin = json_object_new_object();
    json_object_object_add(j_origin, "addr", json_object_new_string(orig_addr_str));
    json_object_object_add(j_origin, "port", json_object_new_int(b_orig_port->port_num));
    json_object_object_add(j_obj, "origin", j_origin);

    // Add responder.
    j_responder = json_object_new_object();
    json_object_object_add(j_responder, "addr", json_object_new_string(resp_addr_str));
    json_object_object_add(j_responder, "port", json_object_new_int(b_resp_port->port_num));
    json_object_object_add(j_obj, "responder", j_responder);

    // Add data, data direction, and sequence number.
    json_object_object_add(j_obj, "data", json_object_new_string(data_b64));
    json_object_object_add(j_obj, "seq_num", json_object_new_int(*seq));
    json_object_object_add(j_obj, "is_origin_data", json_object_new_boolean(*is_orig));

    out_jstr = json_object_to_json_string(j_obj);

    ret = fprintf(fifo_file, "%s\n", out_jstr);

    if (ret < 0) {
        fprintf(stdout, "Cannot write to output fifo.\n");
        exit(EXIT_FAILURE);
    } else if (ret !=  strlen(out_jstr) + 1) {
        fprintf(stdout, "Truncated write on output fifo!\n");
        exit(EXIT_FAILURE);
    }

    // }

    fprintf(stdout, "--------------\n");

    // Cleanup
    bc = NULL;
    user_data = NULL;
    return;
}

// }
