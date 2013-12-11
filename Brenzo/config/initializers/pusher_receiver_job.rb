require "pusher_receiver_job"


Thread.new do
  Jobs::PusherReceiverJob[:default].start
end
