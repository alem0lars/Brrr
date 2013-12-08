class Endpoint
  include Mongoid::Document
  field :address, type: String
  field :port, type: String
  embeds_many :can_be_in, class_name: "Service", inverse_of: :available_responders
  embeds_many :used_as_responder_in, class_name: "Netflow", inverse_of: :responder
  embeds_many :used_as_originator_in, class_name: "Netflow", inverse_of: :originator
end
