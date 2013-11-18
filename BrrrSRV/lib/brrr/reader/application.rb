module Brrr::Reader

	class Application

		include Brrr::Logging

		include BroccoliReader

		def initialize(args)
			@fifo_path = args[0] || "tmp/brrr_fifo"
			@bro_addr = args[1] || "127.0.0.1"
			@bro_port = args[2] || 47757
		end
		
		def run
			logger.info("Started reading")
			
			FileUtils.rm(@fifo_path) if File.exists? @fifo_path
			fifo_file = File.mkfifo(@fifo_path)
			
			result = start_reading(@bro_addr, @bro_port, @fifo_path)
			
			logger.info("Finished reading with status: #{result}")
		end

	end

end
