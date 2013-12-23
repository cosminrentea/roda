/**
 * 
 */
Ext.define('RODAdmin.model.cron.action.Execution', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
    	type : 'int'
	}, {
        name: 'task_id',
        type: 'integer'
    }, {
        name: 'type',
        type: 'string'
    }, {
        name: 'result',
        type: 'integer'
    }, {
        name: 'timestamp_start',
        type: 'string'
    },{
        name: 'duration',
        type: 'integer'
    }  
    ]
});
