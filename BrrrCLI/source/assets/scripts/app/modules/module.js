define([
  "jquery",
  "lodash"
], function($, _) {
  'use strict';
  
  function Module(name) {
  
    this.name = name;
    
    if (Module.availables == undefined) {
      Module.availables = [];
    }
    Module.availables.push(this);
  
  };
  
  Module.prototype.refresh = function() {
    console.log(">>> Context " + this.name + " has been (generically) refreshed.");
  }
  
  
  return Module;
  
});
