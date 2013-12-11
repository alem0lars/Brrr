class Endpoint
  include Mongoid::Document
  field :address, type: String
  field :port, type: Integer
  has_many :can_be_in, class_name: "Service", inverse_of: :available_responders
  has_many :used_as_responder_in, class_name: "Netflow", inverse_of: :responder
  has_many :used_as_originator_in, class_name: "Netflow", inverse_of: :originator
end
