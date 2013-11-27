define([
  "lodash",
  "logger",
  "backbone/backbone",
  "backbone/backbone-relation"
], function(_, logger, Backbone) {
  "use strict";


  var Connection = Backbone.Model.extend({
    initialize: function() {
      logger.debug("Connection created");
    },
    defaults: {
      uuid: null
    },
    relations: [{
      type: "HasMany",
      key: "chunks",
      relatedModel: "Chunk",
      reverseRelation: {
        key: "connection"
      }
    }, {
      type: "HasOne",
      key: "originator",
      relatedModel: "Endpoint"
    }, {
      type: "HasOne",
      key: "responder",
      relatedModel: "Endpoint"
    }],
    validate: function(attrs, options) {
      if (_.isNull(attrs.uuid) || _.isUndefined(attrs.uuid))
        return "Invalid uuid: it is required.";
      if (!_.isString(attrs.uuid))
        return "Invalid uuid: it must be a string."; 
    }
  });
  
  
  return Connection;
  
});
