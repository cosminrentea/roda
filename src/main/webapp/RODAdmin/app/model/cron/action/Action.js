/**
 * 
 */
Ext.define('RODAdmin.model.cron.action.Action', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
    	type : 'int'
	}, {
        name: 'name',
        type: 'string'
    }, {
        name: 'description',
        type: 'string'
    }, {
        name: 'classname',
        type: 'string'
    }, {
        name: 'cron',
        type: 'string'
    }, {
        name: 'enabled',
        type: 'boolean'
    }, {
        name: 'timestamp_next_execution',
        type: 'string'
    },{
        name: 'timestamp_last_execution',
        type: 'string'
    }  
    ]
});
