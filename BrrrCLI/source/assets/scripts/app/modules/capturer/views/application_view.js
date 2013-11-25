define([
  "jquery",
  "logger",
  "text!app/modules/capturer/templates/application_template.handlebars"
], function ($, logger, application_template) {
  "use strict";
  
  function ApplicationView(rootSelector) {
    var self = this;

    self.view = Ember.View.create({
      template: Ember.Handlebars.compile(application_template),
      templateName: "application",
      didInsertElement: function() {
        logger.info("The application view has been loaded");
      }
    });

    self.view.appendTo($(rootSelector));
  }
  
  
  return ApplicationView;

});
