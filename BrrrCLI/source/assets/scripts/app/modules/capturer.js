define([
  "jquery",
  "logger",
  "backbone/backbone",
  "app/modules/module",
  "app/modules/capturer/views/app_view",
  "app/modules/capturer/router"
], function($, logger, Backbone, Module, AppView, Router) {
  "use strict";
  
  function Capturer(rootSelector) {
    Module.call(this, "capturer");
    
    this.rootSelector = rootSelector;
    
    this.version = "1.0.0";
    
    this.initializeApp();
  
  };

  Capturer.prototype = _.create(Module.prototype, { 'constructor': Capturer });
  
  Capturer.prototype.initializeApp = function() {
    var self = this;
    
    self.router = new Router();
    
    Backbone.history.start();
    
    self.appView = new AppView({
      el: $(self.rootSelector)
    });
    
    self.appView.render();
    
    logger.info("Capturer app (version=" + self.version + ") has been initialized");
  }
  
  Capturer.prototype.refresh = function() {
    logger.info("Refreshing the capturer");
  };
  
  
  return Capturer;

});
