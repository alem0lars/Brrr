module Brrr::Logging

	def log_file_name
		class_name = self.class.to_s
		class_name = class_name.gsub("::", "_").gsub("_Application", "").downcase
		@log_file_name ||= "#{class_name}_log.txt"
	end

	def log_file_path
		FileUtils.mkdir_p($paths.log) unless File.directory? $paths.log
		@log_file_path ||= $paths.log.join(log_file_name)
	end

	def logger
		@logger = Logger.new(log_file_path)
	end

end
