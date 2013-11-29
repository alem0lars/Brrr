define([
  "lodash",
  "logger",
  "backbone/backbone"
], function(_, logger, Backbone) {
  "use strict";


  var Chunk = Backbone.Model.extend({
    initialize: function() {
      logger.debug("Chunk created");
    },
    defaults: {
      isOriginData: null,
      sequenceNumber: null
      data: null
    },
    validate: function(attrs, options) {
      _.each(["isOriginData", "sequenceNumber", "data", "connection"], function(elem) {
        if (_.isNull(attrs[elem]) || _.isUndefined(attrs[elem]))
          return "Invalid " + elem + ": it is required.";
      });
      if (!_.isBoolean(attrs.isOriginData))
        return "Invalid isOriginData: it must be a boolean.";
      if (!_.isNumber(attrs.sequenceNumber))
        return "Invalid sequenceNumber: it must be a number.";
      if (!_.isString(attrs.data))
        return "Invalid data: it must be a string."; 
    }
  });
  
  
  return Chunk;
  
});
