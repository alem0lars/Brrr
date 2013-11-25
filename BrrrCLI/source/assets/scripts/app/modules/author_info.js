define([
  "jquery",
  "app/modules/module"
], function($, Module) {
  "use strict";

  function AuthorInfo() {
    var self = this;
    Module.call(self, "author-info");
                
    $("." + self.name).each(function(index, elem) {
      var $elem = $(elem);
      $elem.css("background-image", 'url("/assets/images/avatars/' + $elem.data('author-username') + '_avatar.png")');
    });

  };
  
  AuthorInfo.prototype = _.create(Module.prototype, { 'constructor': AuthorInfo });


  return AuthorInfo;
  
});
