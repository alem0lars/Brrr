# If true, all TCP originator-side traffic is reported via tcp_contents.
redef tcp_content_deliver_all_orig = T;

# If true, all TCP responder-side traffic is reported via tcp_contents.
redef tcp_content_deliver_all_resp = T;

event connection_established(c: connection) {
  print fmt("[%s] [orig: %s:%s] <-> [resp: %s:%s] ESTABLISHED", c$uid,
      c$id$orig_h, c$id$orig_p,
      c$id$resp_h, c$id$resp_p);
}

event tcp_contents(c: connection, is_orig: bool, seq: count, contents: string) {
  local dir_str : string = (is_orig)? "-->" : "<--";
  print fmt("[%s] [orig: %s:%s] %s [resp: %s:%s] seq: %d", c$uid,
      c$id$orig_h, c$id$orig_p,
      dir_str,
      c$id$resp_h, c$id$resp_p,
      seq);
}

event connection_state_remove(c: connection) {
  print fmt("[%s] [orig: %s:%s] --- [resp: %s:%s] REMOVED", c$uid,
      c$id$orig_h, c$id$orig_p,
      c$id$resp_h, c$id$resp_p);
}
