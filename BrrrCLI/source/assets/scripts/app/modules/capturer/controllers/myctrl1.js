define([
], function() {
  "use strict";
  
  
  var controller = ["$scope", "$http", function($scope, $http) {
    $scope.welcomeMessage = "Hello World!!!!!";
    
    $scope.$apply();
  }];
  
  
  return controller;
  
});