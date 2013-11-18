require([
  "main"
], function () {
    'use strict';
    
    require([
      "jquery",
      "app/modules/author_info"
    ], function($, AuthorInfo) {
      
      $(document).ready(function() {
        
        var author_info = new AuthorInfo();
            
      });
    
    });
    
});
