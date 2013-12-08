module Brrr::Pusher

  class Application

    include Brrr::Logging

    USAGE = <<-EOS
      Usage:
        brrr-websocket_multicast_pusher.rb <source_addr> <source_port> <websocket_listen_addr> <websocket_listen_port>
    EOS

    def initialize(argv)
      parse_config(argv)
    end

    def parse_config(argv)
      @src_addr = argv[0] || "127.0.0.1"
      @src_port = (argv[1] || 7999).to_i
      @ws_list_addr = argv[2] || "0.0.0.0"
      @ws_list_port = (argv[3] || 8000).to_i
    end

    private :parse_config

    def run
      logger.info("Started pushing")

      pusher = Pusher.new(@src_addr, @src_port, @ws_list_addr, @ws_list_port)
      pusher.serve

      logger.info("Finished pushing")
    end

  end

end
