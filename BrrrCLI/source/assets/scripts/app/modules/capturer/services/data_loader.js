define([
  "lodash",
  "logger"
], function(_, logger) {
  "use strict";

  
  angular.service("capturerApp.services.dataLoader", [
    "capturerApp.services.webSocket"
  ], function(webSocket) {

    var DataLoader = function(dataRepository) {
      var self = this;

      self.dataRepository = dataRepository || {};
      
      webSocket.subscribe({
        onOpenSuccess: self.onOpenSuccess,
        onOpenFailure: self.onOpenFailure,
        onData: self.onData
      });

    };

    DataLoader.prototype.onOpenSuccess = function() {
      logger.debug("OK CONNECT");
    };

    DataLoader.prototype.onOpenFailure = function() {
      logger.debug("FAILED");
    };

    DataLoader.prototype.onData = function(data) {
      logger.debug("GOT DATA");
    };

    return DataLoader;

  });


  return {};

}
