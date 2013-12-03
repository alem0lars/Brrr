define([
  "angular/angular",
  "app/modules/capturer/filters",
  "app/modules/capturer/services",
  "app/modules/capturer/directives",
  "app/modules/capturer/controllers",
  "angular/angular-route"
], function(angular) {
  "use strict";
  
  
  var appModule = angular.module("capturerApp", [
    "ngRoute",
    "capturerApp.controllers",
    "capturerApp.filters",
    "capturerApp.services",
    "capturerApp.directives"
  ]);
  
  
  return appModule;

});
