require.config({

  baseUrl: '/assets/scripts/vendor',
  
  paths: {
    app: '../app'
  },
  
  shim: {
  
    'jquery': { deps: [], exports: '$' },
    
    'modernizr': { },
    
    "foundation/foundation": { deps: ["jquery"] },
    
    "store": { deps: ["jquery"] },

    "logger": { },
    
    "lodash": { exports: ["_"] },
    
    "angular/angular": { exports: "angular" },
    "angular/angular-route": { deps: ["angular/angular"] },
    "angular/angular-mocks": { deps: ["angular/angular"], exports: "angular.mock" },
    
    "foundation/foundation-alerts": { deps: ["foundation/foundation"] },
    "foundation/foundation-clearing": { deps: ["foundation/foundation"] },
    "foundation/foundation-cookie": { deps: ["foundation/foundation"] },
    "foundation/foundation-dropdown": { deps: ["foundation/foundation"] },
    "foundation/foundation-forms": { deps: ["foundation/foundation"] },
    "foundation/foundation-joyride": { deps: ["foundation/foundation"] },
    "foundation/foundation-magellan": { deps: ["foundation/foundation"] },
    "foundation/foundation-orbit": { deps: ["foundation/foundation"] },
    "foundation/foundation-placeholder": { deps: ["foundation/foundation"] },
    "foundation/foundation-reveal": { deps: ["foundation/foundation"] },
    "foundation/foundation-section": { deps: ["foundation/foundation"] },
    "foundation/foundation-tooltips": { deps: ["foundation/foundation"] },
    "foundation/foundation-topbar": { deps: ["foundation/foundation"] },
    "foundation/foundation-interchange": { deps: ["foundation/foundation"] }
  },
  
  priority: [
    "angular"
  ]
  
});


require([
  "logger",
  "text",
  "modernizr",
  "app/misc/foundation"
], function(logger) {
  "use strict";
  
  logger.useDefaults();
  
  logger.info("BrrrCLI Started");

});
