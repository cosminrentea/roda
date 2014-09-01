/**
 * 
 */
Ext.define('RODAdmin.model.dashboard.TempStudies', {
    extend : 'Ext.data.Model',
    fields : [
            {
                name : 'started',
                type : 'string'
            },{
                name : 'startedby',
                type : 'string'
            },{
                name : 'title',
                type : 'string'
            }],
});
