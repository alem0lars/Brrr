define([
  "lodash",
  "logger",
  "store"
], function(_, logger, store) {
  "use strict";
  

  angular.service("capturerApp.services.webSocket", [
  ], function() {

    var webSocketService = {};
 
    webSocketService.connect = function() {
      if(webSocketService.ws) {
        return;
      }

      var addr = store.get("addr");
      var port = store.get("port");
   
      if (addr && port) {
        var ws = new WebSocket("ws://" + addr + ":" + port);
   
        ws.onopen = function() {
          if (!_.isNull(webSocketService.callbacks.onOpenSuccess)) {
            webSocketService.callbacks.onOpenSuccess();
          }
        };
     
        ws.onerror = function() {
          if (!_.isNull(webSocketService.callbacks.onOpenFailure)) {
            webSocketService.callbacks.onOpenFailure();
          } else {
            logger.warn("Unhandled websocket registration failure");
          }
        }
     
        ws.onmessage = function(message) {
          if (!_.isNull(webSocketService.callbacks.onData)) {
            webSocketService.callbacks.onData(message.data);
          }
        };
     
        webSocketService.ws = ws;
      }
    }

    webSocketService.subscribe = function(callbacks) {
      _.defaults(callbacks, {
        onOpenSuccess: null,
        onOpenFailure: null,
        onData: null
      });
      webSocketService.callbacks = callbacks;
    }
   
    return webSocketService;

  });


  return {};
  
});
