require([
  "main"
], function () {
  'use strict';

  require([
    "jquery",
    "app/modules/refresher",
    "app/modules/capturer"
  ], function($, Refresher, Capturer) {
    
    $(document).ready(function() {
      
      var capturer = new Capturer("#capturer");
      
      var refresher = new Refresher();
            
    });
    
  });
  
});
