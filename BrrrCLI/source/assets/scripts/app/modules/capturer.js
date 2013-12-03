window.name = "NG_DEFER_BOOTSTRAP!";
  
define([
  "jquery",
  "logger",
  "angular/angular",
  "app/modules/module",
  "app/modules/capturer/app",
  "app/modules/capturer/routes"
], function($, logger, angular, Module, app) {
  "use strict";
  
  
  function Capturer(rootSelector) {
    var self = this;
    Module.call(self, "capturer");
    
    self.rootSelector = rootSelector;
    
    self.version = "1.0.0";
    
    self.initializeApp();
  
  };

  Capturer.prototype = _.create(Module.prototype, { 'constructor': Capturer });
  
  Capturer.prototype.initializeApp = function() {
    var self = this;
    
    var $rootSelector = angular.element($(self.rootSelector)[0]);
    
    self.app = app;
    
    angular.element().ready(function() {
      angular.resumeBootstrap([self.app["name"]]);
    });
    
    logger.info("Capturer app (version=" + self.version + ") has been initialized");
  }
  
  Capturer.prototype.refresh = function() {
    logger.info("Refreshing the capturer");
  };
  
  
  return Capturer;

});
