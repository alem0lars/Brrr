define([
  "jquery",
  "app/modules/module"
], function($, Module) {
  'use strict';

  var Configurator = function(configElems) {
    var self = this;
    Module.call(self, "configurator");
    
    self.configElems = configElems || [];
    
    self.loadConfigs();
    
    self.bindConfigElems();
    
    self.version = "1.0.0";
    
  };
  
  Configurator.prototype = _.create(Module.prototype, { 'constructor': Configurator });
  
  Configurator.prototype.bindConfigElems = function() {
    var self = this;
    
    // On save action
    $(".configurator").submit(function(e) {
      e.preventDefault();
      self.saveConfigs();
    });
    
      // On load action
    $(".configurator .load-config-action").click(function(e) {
      e.preventDefault();
      self.loadConfigs();
    });

    // On clear action
    $(".configurator .clear-config-action").click(function(e) {
      e.preventDefault();
      self.clearConfigs();
    });
  };
  
  Configurator.prototype.saveConfigs = function() {  
    console.log(">>> Updating the stored configurations");
    _.each(this.configElems, function(configElem, index) {
      configElem.save($(configElem.inputSelector).val());
    });
  };
  
  Configurator.prototype.loadConfigs = function() {
    console.log(">>> Loading the stored configurations");
    _.each(this.configElems, function(configElem, index) {
      configElem.load();
    });
  };
  
  Configurator.prototype.clearConfigs = function() {    
    console.log(">>> Clearing the stored configurations");
    _.each(this.configElems, function(configElem, index) {
      configElem.clear();
    });
  };
  
  Configurator.prototype.refresh = function() {
    this.loadConfigs();
  };
  
  
  return Configurator;

});
