require "em-websocket-client"
require "json"
require "logger"


module Jobs
  class PusherReceiverJob

    class << self
      attr_accessor :jobs
      attr_reader :default_options

      def initialize
        @jobs = {}
        @default_options = {
          :host => "127.0.0.1",
          :port => 8000,
          :logger => Rails.logger
        }
      end

      def [] name
        if jobs.has_key?(name)
          jobs[name]
        else
          jobs[name] = PusherReceiverJob.new
        end
      end

      def []= name, job
        jobs[name] = job
      end
    end

  end
end


class Jobs::PusherReceiverJob

  attr_reader :options, :logger, :host, :port

  def initialize(options = {})
    opts = PusherReceiverJob.default_options.merge(options)
    
    @host = opts[:host]
    @port = opts[:port]
    @logger = opts[:logger]
    @options = opts
  end

  def start
    EM.run do
      conn_str = "ws://#{@host}:#{@port}/"

      conn = EventMachine::WebSocketClient.connect(conn_str)

      conn.callback do
        @logger.info "Connected to '#{conn_str}'."
      end

      conn.errback do |error|
        @logger.error "Got error: '#{error}'."
      end

      conn.stream do |msg|
        @logger.debug { "Got message: '#{msg.inspect}'." }
        handle_data(msg.data)
      end

      conn.disconnect do
        @logger.info "Disconnected from '#{conn_str}'."
        EM::stop_event_loop
      end
    end
  end

  def stop
    EM.stop if EM.reactor_running?
  end

  protected

    def handle_data(data)
      json = JSON.parse(data, :symbolize_names => true)

      chunk = new_chunk(json[:data])

      originator = new_endpoint(json[:originator])
      responder = new_endpoint(json[:responder])
      netflow = new_netflow(json[:connection_id])
      service = new_service() # TODO: we don't have service infos yet.

      netflow.chunks << chunk

      originator.used_as_originator_in << netflow
      originator.save

      responder.used_as_responder_in << netflow
      responder.save
    end

    def new_endpoint(j_endp)
      Endpoint.new(address: j_endp[:addr], port: j_endp[:port])
    end

    def new_chunk(chunk_data)
      Chunk.new(data: Base64.decode64(chunk_data))
    end

    def new_netflow(connection_id)
      Netflow.new(connection_id: connection_id)
    end

    # TODO: implement (we don't have service infos yet).
    def new_service()
      nil
    end

end
