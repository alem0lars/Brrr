module Brrr::Commons


  class SequentialTcpAcceptor

    def initialize(addr, port)
      @addr = addr
      @port = port
      @hooks = { :before_accept => nil,
                 :on_conn_term => nil }
    end

    def before_accept(&blk) @hooks[:before_accept] = blk; end
    def on_conn_term(&blk) @hooks[:on_conn_term] = blk; end

    def start_accept(&blk)
      conn_sck = TCPServer.new(@addr, @port)
      loop do
        trigger_hook(:before_accept, conn_sck)
        sck = conn_sck.accept
        begin
          blk.call(sck)
        # Rescue some disconnects.
        rescue Errno::EPIPE => epipe
          $stderr.puts epipe
        rescue Errno::ECONNRESET => econn
          $stderr.puts econn
        ensure
          trigger_hook(:on_conn_term, sck)
          # Cleanup socket resource.
          if !sck.nil? && sck.closed?
            sck.close
          end
        end
      end

    end

    protected

    def trigger_hook(hook_name, *args)
      hook = @hooks[hook_name.to_sym]
      unless hook.nil?
        hook.call(*args)
      end
    end

  end


end