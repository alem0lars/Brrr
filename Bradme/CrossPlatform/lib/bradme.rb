require "ostruct"
require "Qt4"


module Bradme
end


%w[
  bradme/models
  bradme/ui
].each { |mod| require mod }
