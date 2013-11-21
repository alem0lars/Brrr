module Bradme::Models

  class Component

    attr_accessor :status

    def initialize(name, component_status)
      @name = name
      @status = component_status
    end

  end

end
