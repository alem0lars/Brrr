module Bradme::Models

  module Components

    BROL = Component.new(:brol, ComponentStatuses::STOPPED)
    READER = Component.new(:reader, ComponentStatuses::STOPPED)
    BUFFERER = Component.new(:bufferer, ComponentStatuses::STOPPED)
    PUSHER = Component.new(:pusher, ComponentStatuses::STOPPED)

  end

end
