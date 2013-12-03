define([
  "angular/angular",
  "app/modules/capturer/app",
  "text!app/modules/capturer/templates/view1.html"
], function(angular, app, view1) {
  "use strict";
  
  
  app.config(["$routeProvider", function($routeProvider) {
    
    $routeProvider.when("/view1", {
      template: view1,
      controller: "MyCtrl1"
    });
    
    $routeProvider.otherwise({ redirectTo: "/view1" });
    
  }]);


  return {};
    
});
