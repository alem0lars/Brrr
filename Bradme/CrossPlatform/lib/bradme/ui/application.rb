module Bradme
  module UI

    class Application

      def initialize(opts)
        @opts = opts

        @app = Qt::Application.new ARGV

        @main_widget = MainWidget.new(@app)

        register_signals_handlers
      end

      def start
        pre_start
        @main_widget.show
        @app.exec
      end

      def stop
        @app.quit
      end

      protected

        def pre_start
          # Start a timer to call Application.event repeatedly
          @app.startTimer(256)
        end

        def register_signals_handlers
          %w(TERM INT).each do |sig_name|
            Signal.trap(sig_name) { stop }
          end
        end

      end

  end

end
