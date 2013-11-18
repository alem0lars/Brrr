module Brrr::Bufferer

	class Application

		include Brrr::Logging

		USAGE = <<-EOS
		  Usage:
		    brrr-bufferer.rb <listen_address> <listen_port> <queue_size>
		EOS

		def initialize(args)
			@addr = args[0] || "127.0.0.1"
			@port = args[1].to_i || 7999
			@queue_size = args[2].to_i || 1
		end
		
		def run
			logger.info("Started buffering")
			
			queue = CircularQueue.new(@queue_size)
			
			cap_thr = Thread.new do
				# process capturer's output
				logger.info("Capturer started")

			  	while s = $stdin.gets()
			    	s.chop!
			    	queue.enq(s) # CircularQueue#enq has wrap-around behaviour.
			    	#$stderr.puts "[BUFFER] enq: queue status: #{queue.size}/#{queue.capacity}."
			  	end

				logger.info("Capturer ended")
			end

			srv_thr = Thread.new(TCPServer.new(@addr, @port)) do |conn_sck|
				loop do
					sck = conn_sck.accept

				    logger.info("Client connected")
				    
				    # serve the client
				    begin
				      	while s = queue.deq # CircularQueue#deq blocks if the queue is empty.
				          	sck.puts(s)
				          	#$stderr.puts "[BUFFER] enq: queue status: #{queue.size}/#{queue.capacity}."
				      	end
				    rescue
				    ensure
				      	sck.close
				      	logger.info("Disconnected")
				    end

				end # accept loop
			end

			cap_thr.join

			logger.info("Finished buffering with status: #{result}")
		end

	end

end
