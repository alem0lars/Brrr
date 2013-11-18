define([
  "jquery",
  "app/modules/module"
], function($, Module) {
  'use strict';
  
  var Refresher = function() {
    var self = this;
    Module.call(self, "refresher");

    $("." + this.name).click(function(e) {
      e.preventDefault();
    
      _.each(Module.availables, function(module, index) {
        if (module != self) {
          module.refresh();
        }
      });
    });
    
  };
  
  Refresher.prototype = _.create(Module.prototype, { 'constructor': Refresher });


  return Refresher;
  
});
