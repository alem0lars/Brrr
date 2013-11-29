define([
  "lodash",
  "logger",
  "backbone/backbone"
], function(_, logger, Backbone) {
  "use strict";


  var Endpoint = Backbone.Model.extend({
    initialize: function() {
      logger.debug("Endpoint created");
    },
    defaults: {
      address: null,
      port: null
    },
    validate: function(attrs, options) {
      _.each(["address", "port"], function(elem) {
        if (_.isNull(attrs[elem]) || _.isUndefined(attrs[elem]))
          return "Invalid " + elem + ": it is required.";
      });
      if (!_.isString(attrs.address))
        return "Invalid address: it must be a string.";
      if (!_.isString(attrs.port))
        return "Invalid port: it must be a string.";
    }
  });
  
  
  return Endpoint;
  
});
