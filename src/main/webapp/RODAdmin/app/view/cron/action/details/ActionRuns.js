/**
 * 
 */
Ext.define('RODAdmin.view.cron.action.details.ActionRuns', {
    extend : 'Ext.grid.Panel',
    alias : 'widget.actionruns',
    itemId : 'actionruns',
    title : 'Action Runs',
    store: 'cron.ExecutionList',
    // id: 'fileproperties',

	columns : [{
		itemId : 'rtype',
		text : 'Run type',
		flex : 1,
		sortable : true,
		dataIndex : 'type',
		filterable : true
	}, {
		text : 'Start',
		flex : 1,
		dataIndex : 'timestamp_start',
		sortable : true,
		filterable : true
	}, {
		text : 'Duration',
		flex : 1,
		dataIndex : 'duration',
		sortable : true,
		filterable : true
	}, {
		text : 'Result',
		flex : 1,
		dataIndex : 'result',
		sortable : true,
		filterable : true
	}, {
		text : 'View',
  	    xtype: 'templatecolumn',
		dataIndex : '',
		flex : 1,
 	    tpl: '<tpl if="result == 0">View</tpl>',
	}
	
	
	]
});
