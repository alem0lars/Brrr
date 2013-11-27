define([
  "lodash",
  "logger",
  "backbone/backbone",
  "app/modules/capturer/models/connection"
], function(_, logger, Backbone, Connection) {
  "use strict";


  var Connections = Backbone.Collection.extend({
    model: Connection
  });
  
  
  return Connections;

});
