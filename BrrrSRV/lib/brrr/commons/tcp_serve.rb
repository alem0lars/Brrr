module Brrr::Commons


  class SequentialTcpAcceptor

    def initialize(addr, port)
      @addr = addr
      @port = port
    end

    def each_accept
      raise 'No block given' unless block_given?

      conn_sck = TCPServer.new(@addr, @port)
      loop do
        sck = conn_sck.accept
        begin
          yield sck
        ensure
          sck.close unless sck.nil?
        end
      end
    end

  end


end