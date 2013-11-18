require "bundler/setup"
require "eventmachine"
require "em-websocket"
require "circular_queue"
require "socket"
require "mkfifo"
require "fileutils"
require "logger"
require "ostruct"


$paths = OpenStruct.new
$paths.lib = Pathname.new File.expand_path("../../lib", __FILE__)
$paths.root = $paths.lib.dirname
$paths.tmp = $paths.root.join("tmp")
$paths.log = $paths.root.join("log")


module Brrr
end


%w[
	logging
  	bufferer
  	pusher
  	reader
].each { |mod| require "brrr/#{mod}" }
