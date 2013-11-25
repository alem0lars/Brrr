// A facade over all of the models.
define([
  "logger",
  "app/modules/capturer/models/chunk",
  "app/modules/capturer/models/connection",
  "app/modules/capturer/models/endpoint"
], function(logger, Chunk, Connection, Endpoint) {
  "use strict";
  

  logger.info("Loading models Chunk, Connection, Endpoint");
  
  var models = {
    Chunk: Chunk,
    Connection: Connection,
    Endpoint: Endpoint
  };


  return models;
  
});
