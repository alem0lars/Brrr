module Bradme::UI

  class MainWidget < Qt::Widget

    def initialize(app)

      @app = app

      @component_widgets = [
        ComponentWidget.new(@app, Components::BROL),
        ComponentWidget.new(@app, Components::READER),
        ComponentWidget.new(@app, Components::BUFFERER),
        ComponentWidget.new(@app, Components::PUSHER)
      ]

      layout = Qt::VBoxLayout.new

      @component_widgets.each do |component_widget|
        layout.addWidget(component_widget)
      end

    end

  end

end
