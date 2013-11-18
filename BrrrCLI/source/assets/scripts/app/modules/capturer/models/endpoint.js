define([
  "ember-data"
], function(DS) {
  'use strict';

  return DS.Model.extend({
    address: DS.attr('string'),
    port: DS.attr('string'),
    connections: DS.hasMany('connection')
  });

});
