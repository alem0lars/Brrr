module Brrr::Pusher

  class Handler < EventMachine::Connection

    def initialize(channel)
      @channel = channel
    end

    def receive_data(data)
      (@buffer ||= BufferedTokenizer.new("\n")).extract(data).each do |line|
        receive_line(line)
      end
    end

    def receive_line(line)
      @channel.push(line)
    end

  end

end
