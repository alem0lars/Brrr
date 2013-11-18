module Brrr::Pusher

  class Pusher

    def initialize(src_addr, src_port, ws_list_addr, ws_list_port)
      @src_addr = src_addr
      @src_port = src_port
      @ws_list_addr = ws_list_addr
      @ws_list_port = ws_list_port
    end

    def serve

      EventMachine.run do

        @channel = EventMachine::Channel.new

        EventMachine::WebSocket.run(:host => @ws_list_addr, :port => @ws_list_port) do |ws|
          ws.onopen do
            sid = @channel.subscribe { |msg| ws.send msg }

            ws.onclose { @channel.unsubscribe sid }
          end
        end

        conn = EventMachine.connect(@src_addr, @src_port, Handler, @channel)

      end

    end

  end

end
