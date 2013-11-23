module Brrr::Bufferer

	class Application

		include Brrr::Logging

		USAGE = <<-EOS
		  Usage:
		    brrr-bufferer.rb <listen_address> <listen_port> <queue_size>
		EOS

		def initialize(args)
			@addr = args[0] || "127.0.0.1"
			@port = (args[1] || 7999).to_i
			@queue_size = (args[2] || 128).to_i
		end

		def run
			logger.info("Started buffering")

			Thread.abort_on_exception = true

			queue = CircularQueue.new(@queue_size)

			cap_thr = Thread.new do
				# process capturer's output
				logger.info("Capturer started")

			  	while s = $stdin.gets()
			    	s.chop!
			    	queue.enq(s) # CircularQueue#enq has wrap-around behaviour.
			    	logger.debug "[Capturer]: enq queue status: #{queue.size}/#{queue.capacity}."
			  	end

				logger.info("Capturer ended")
			end

			srv_thr = Thread.new(TCPServer.new(@addr, @port)) do |conn_sck|
				logger.info("Acceptor started")
				logger.debug("[Acceptor] listening on #{@addr}:#{@port}")
				loop do
					logger.info("[Acceptor] Accepting ...")
					sck = conn_sck.accept

				    logger.info("[Acceptor] Client connected")

				    # serve the client
				    begin
				      	while s = queue.deq # CircularQueue#deq blocks if the queue is empty.
				          	sck.puts(s)
				          	logger.debug "[Acceptor] enq: queue status: #{queue.size}/#{queue.capacity}."
				      	end
				    rescue
				    ensure
				      	sck.close
				      	logger.info("Disconnected")
				    end

				end # accept loop
				logger.info("Acceptor ended")
			end

			cap_thr.join

			logger.info("Finished buffering with status: #{result}")
		end

	end

end
