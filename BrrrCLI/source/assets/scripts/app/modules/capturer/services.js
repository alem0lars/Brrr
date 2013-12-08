define([
  "angular/angular"
], function(angular) {
  "use strict";
  

  angular.module("capturerApp.services")
    .service("capturerApp.services.dataLoader", ["$injector", function($injector) {
      require(["app/modules/capturer/services/data_loader"], function(dataLoader) {
        $injector.invoke(dataLoader, this);
      });
    }]);
  
  
  return {};
    
});
