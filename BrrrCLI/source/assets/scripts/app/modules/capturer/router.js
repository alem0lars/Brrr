define([
  "ember"
], function(Ember) {
  'use strict';


  function Router(app, rootSelector) {

    app.ApplicationRoute = Ember.Route.extend({
      setupController: function(controller, model) {
        require([
          "app/modules/capturer/controllers/application_controller"
        ], function(ApplicationController) {
          var application_controller = new ApplicationController(rootSelector);
        })
      }
    });

  };
  

  return Router; 

});
