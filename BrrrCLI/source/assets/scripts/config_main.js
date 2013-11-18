require([
  "main"
], function () {
  'use strict';
  
  require([
    "jquery",
    "app/modules/refresher",
    "app/modules/configurator",
    "app/modules/configurator/configuration_element"
  ], function($, Refresher, Configurator, ConfigurationElement) {

    $(document).ready(function() {

      var configElems = _.map([
        "port",
        "addr"
      ], function(configElemName) {
        return new ConfigurationElement("#config-" + configElemName + "-input", configElemName)
      });

      var configurator = new Configurator(configElems);
                
      var refresher = new Refresher();
      
    });

  });
    
});
