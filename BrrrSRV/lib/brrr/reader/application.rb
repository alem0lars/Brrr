module Brrr::Reader

	class Application

		include Brrr::Logging

		include BroccoliReader

		attr_reader :bro_addr, :bro_port, :listen_addr, :listen_port, :queue_size

		def initialize(args)
			@bro_addr = args[0] || "127.0.0.1"
			@bro_port = (args[1] || 47757).to_i
			@listen_addr = args[2] || "127.0.0.1"
			@listen_port = (args[3] || 7999).to_i
			@queue_size = (args[4] || 8192).to_i
		end

		def run
			logger.info "Reader started."
			Thread.abort_on_exception = true
			queue = CircularQueue.new(@queue_size)

			# { Accept incoming connection and push queue contents to socket.

			acceptor = Brrr::Commons::SequentialTcpAcceptor.new(@listen_addr, @listen_port)
			logger.info "Accepting connections on #{@listen_addr}:#{@listen_port}/tcp ..."

			acc_thr = Thread.new(logger, acceptor, queue) do |l, acc, q|
				# { Set acceptor hooks.
				acc.before_accept do
					l.info "[Acceptor] Accepting ..."
				end
				acc.on_conn_term do
					l.info "[Acceptor] Finishing to serve a client."
				end
				# }

				# Start serving clients.
				acc.start_accept do |sck|
					l.info "[Acceptor] Client connected."
					while s = q.deq # CircularQueue#deq blocks if the queue is empty.
				    sck.puts(s)
				    #l.debug "[Queue reader] deq: queue status: #{q.size}/#{q.capacity} ."
				  end
				end
			end

			# }

			# { Read traffic from Bro fill the queue.

			enq_lambda = lambda do |j_str|
				queue << j_str
				#logger.debug "[Queue writer] enq: queue status: #{queue.size}/#{queue.capacity} ."
			end
			result = start_reading(@bro_addr, @bro_port, enq_lambda)

			# }

			# TODO: How we deal with different exceptional cases?
			# TODO: Should we kill the acceptor thread in some cases?

			logger.info("Reader exiting with status: #{result} .")
		end

	end

end
