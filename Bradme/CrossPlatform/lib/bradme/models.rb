module Bradme::Models
end


%w[
  models/component_status
  models/component_statuses
  models/component
  models/components
].each { |mod| require mod }