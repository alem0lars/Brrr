// A facade over all of the models.
define([
  "app/modules/capturer/models/chunk",
  "app/modules/capturer/models/connection",
  "app/modules/capturer/models/endpoint"
], function(Chunk, Connection, Endpoint) {
  'use strict';
  
  return {
    Chunk: Chunk,
    Connection: Connection,
    Endpoint: Endpoint
  };
  
});
