define([
  "ember-data"
], function(DS) {
  'use strict';
  
  return DS.Model.extend({
    uuid: DS.attr('string'),
    chunks: DS.hasMany('chunk'),
    originator: DS.belongsTo('endpoint'),
    responder: DS.belongsTo('endpoint')
  });
  
});
