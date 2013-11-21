module Bradme::UI
end


%w[
  bradme/ui/component_widget
  bradme/ui/main_widget
  bradme/ui/application
].each { |mod| require mod }
