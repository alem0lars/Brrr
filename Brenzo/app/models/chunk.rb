class Chunk
  include Mongoid::Document
  field :data, type: BSON::Binary
  belongs_to :netflow
end
