class Chunk
  include Mongoid::Document
  field :data, type: String
  embedded_in :netflow
end
