define([
  "jquery",
  "lodash",
  "logger",
  "backbone/backbone",
  "text!app/modules/capturer/templates/app_template.tmpl"
], function ($, _, logger, Backbone, appTemplate) {
  "use strict";


  var AppView = Backbone.View.extend({
    template: _.template(appTemplate),
    render: function() {
      var self = this;
      
      self.$el.html(self.template());
    }
  });
  
  
  return AppView;

});
