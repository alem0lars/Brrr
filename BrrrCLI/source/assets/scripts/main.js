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
    
    "backbone/backbone": { deps: ["lodash", "jquery"], exports: "Backbone" },
    "backbone/backbone-relational": { deps: ["backbone/backbone"] },
    
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
  }
  
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
