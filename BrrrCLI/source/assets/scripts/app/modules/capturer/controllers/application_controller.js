define([
  "ember",
  "app/modules/capturer/views/application_view"
], function(ember, ApplicationView) {
  "use strict";
  

  function ApplicationController(rootSelector) {
    var self = this;

    self.view = new ApplicationView(rootSelector);

    self.controller = ember.Controller.extend({

    });
  }


  return ApplicationController;

});
