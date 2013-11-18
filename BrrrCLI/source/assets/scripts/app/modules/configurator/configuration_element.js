define([
  "jquery",
  "store"
], function($, store) {
  'use strict';
  
  var ConfigurationElement = function(inputSelector, storageId) {
    this.inputSelector = inputSelector;
    this.storageId = storageId;
  };
  
  ConfigurationElement.prototype.save = function(value) {
    store.set(this.storageId, value);
    $(this.inputSelector).val(value);        
  };
  
  ConfigurationElement.prototype.load = function() {
    if (store.get(this.storageId) != undefined) {
      this.save(store.get(this.storageId));
    } else {
      this.save($(this.inputSelector).attr("value"));      
    }
  };
  
  ConfigurationElement.prototype.clear = function() {
    store.remove(this.storageId);
    this.load();
  };
  
  
  return ConfigurationElement;
  
});