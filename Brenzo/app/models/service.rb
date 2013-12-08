class Service
  include Mongoid::Document
  field :name, type: String
  field :url, type: String
  field :info, type: String
  embeds_many :netflows
  embeds_many :available_responders, class_name: "Endpoint", inverse_of: :can_be_in
end
