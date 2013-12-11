at_exit do
   finalizer_files = File.join(::Rails.root.to_s, "config/finalizers/*.rb")
   Dir.glob(finalizer_files).sort.each do |finalizer_file|
      require finalizer_file
   end
end
