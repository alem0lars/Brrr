module Bradme::UI

  class ComponentWidget < Qt::Widget

    def initialize(app, name, component)

      @app = app
      @component = component

      @lbl_title = create_lbl_title
      @lbl_status = create_lbl_status
      @btn_status = create_btn_status

      layout = Qt::HBoxLayout.new
      layout.addWidget(@status_btn)

    end

    protected

      def create_lbl_title
        lbl_title = Qt::Label.new(@component.name)
        return lbl_title
      end

      def create_lbl_status
        lbl_title = Qt::Label.new(@component.status)
        return lbl_title
      end

      def create_btn_status
        btn_status = Qt::PushButton.new(@component.status.name)
        btn_status.connect(SIGNAL :clicked) do
          btn_status_clicked
        end
        return btn_status
      end

      def btn_status_clicked
        puts "Cliccato per componente: #{@component}"
      end

  end

end
