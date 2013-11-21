module Bradme::Models

  class ComponentStatus

    STARTING = ComponentStatus.new :starting
    STARTED = ComponentStatus.new :started
    STOPPED = ComponentStatus.new :stopped

    attr_accessor :name, :desc

    def initialize(name)
      unless %W[
        started
        starting
        stopped
      ].any? { |available_name| name == available_name }
        raise "Invalid status name"
      end

      @name = name
      @desc = "Status #{@name}"
    end

    def pretty_name
      @name.to_s.capitalize.to_sym
    end

  end

end
