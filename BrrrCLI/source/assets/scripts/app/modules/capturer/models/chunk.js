define([
  "ember-data"
], function(DS) {
  'use strict';

  return DS.Model.extend({
    isOriginData: DS.attr('boolean'),
    sequenceNumber: DS.attr('integer'),
    data: DS.attr('string'),
    connection: DS.belongsTo('connection')
  });
  
});
