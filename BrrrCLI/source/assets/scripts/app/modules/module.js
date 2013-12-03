define([
  "jquery",
  "lodash",
  "logger"
], function($, _, logger) {
  "use strict";
  
  function Module(name) {
  
    this.name = name;
    
    if (Module.availables == undefined) {
      Module.availables = [];
    }
    Module.availables.push(this);
  
  };
  
  Module.prototype.refresh = function() {
    logger.info(">>> Module " + this.name + " has been (generically) refreshed.");
  }
  
  
  return Module;
  
});
