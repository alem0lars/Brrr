define([
  "angular/angular"
], function(angular) {
  "use strict";


  angular.module("capturerApp.controllers", ["capturerApp.services"])
    .controller("MyCtrl1", ["$scope", "$injector", function($scope, $injector) {
      require(["app/modules/capturer/controllers/myctrl1"], function(myctrl1) {
        $injector.invoke(myctrl1, this, { "$scope": $scope });
      });
    }]);


  return {};
  
});
