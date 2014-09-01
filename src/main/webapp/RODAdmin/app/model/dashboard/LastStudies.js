/**
 * 
 */
Ext.define('RODAdmin.model.dashboard.LastStudies', {
    extend : 'Ext.data.Model',
    fields : [
            {
                name : 'id',
                type : 'int'
            }, {
                name : 'seriesId',
                type : 'int'
            }, {
                name : 'an',
                type : 'int'
            }, {
                name : 'name',
                type : 'string'
            }],
});
