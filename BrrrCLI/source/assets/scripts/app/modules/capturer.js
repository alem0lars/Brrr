define([
  "jquery",
  "ember",
  "ember-data",
  "app/modules/module",
  "app/modules/capturer/router",
  "app/modules/capturer/models" 
], function($, Ember, DS, Module, Router, models) {
  'use strict';
  
  function Capturer(rootSelector) {
    Module.call(this, "capturer");
    
    this.rootSelector = rootSelector
    
    this.initializeApp();
    
    this.version = "1.0.0";
  
  };

  Capturer.prototype = _.create(Module.prototype, { 'constructor': Capturer });
  
  Capturer.prototype.initializeApp = function() {
    var self = this;
    
    self.app = Ember.Application.create({
      VERSION: self.version,
      rootElement: self.rootSelector,
      ready: function() {
        this.set("Router.enableLogging", true);
      }
    });
 
    // The adapter is a FixtureAdapter, because we don't need to communicate with the server.
    self.app.ApplicationAdapter = DS.FixtureAdapter.extend();
 
    // Initialize the routing.
    var router = new Router(self.app);
    
    _.each(models, function(model, modelName) {
      self.app[modelName] = model;
    })
    
    console.log(">>> Capturer app (version=" + this.version + ") has been initialized");
  }
  
  Capturer.prototype.refresh = function() {
    console.log(">>> Refreshing the capturer");
    // TODO
  };
  
  
  return Capturer;

});
