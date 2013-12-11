class Netflow
  include Mongoid::Document
  field :connection_id, type: String
  belongs_to :service
  belongs_to :responder, class_name: "Endpoint", inverse_of: :used_as_responder_in
  belongs_to :originator, class_name: "Endpoint", inverse_of: :used_as_originator_in
  has_many :chunks
end
