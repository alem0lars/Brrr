# Listen.
@load frameworks/communication/listen

# Set listen config.
redef Communication::listen_interface = 0.0.0.0;
redef Communication::listen_port = 47757/tcp;

# For SSL, uncomment and set these.
#redef Communication::listen_ssl = T;
#redef ssl_ca_certificate    = "<path>/ca_cert.pem";
#redef ssl_private_key       = "<path>/bro.pem";

# For clusters. Please read the documentation.
# TODO @Molari.Luca: Remove class
redef Communication::nodes += {
    ["brahkoli"] = [$host = 127.0.0.1, $class = "brahkoli", $events = /connection_tagged_pid/, $connect = F]
};
