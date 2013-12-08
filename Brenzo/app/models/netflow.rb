class Netflow
  include Mongoid::Document
  field :connection_id, type: String
  embedded_in :service
  embeds_many :chunks
  embedded_in :responder, class_name: "Endpoint", inverse_of: :used_as_responder_in
  embedded_in :originator, class_name: "Endpoint", inverse_of: :used_as_originator_in
end
