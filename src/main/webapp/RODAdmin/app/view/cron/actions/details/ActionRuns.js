Ext.define('RODAdmin.view.cron.actions.details.ActionRuns', {
    extend : 'Ext.grid.Panel',
    alias : 'widget.actionruns',
    itemId : 'actionruns',
    title : 'Action Runs',
    // id: 'fileproperties',
    collapsible : true,

	columns : [{
		itemId : 'rtype',
		text : 'Run type',
		flex : 1,
		sortable : true,
		dataIndex : 'runtype',
		filterable : true
	}, {
		text : 'Start',
		flex : 1,
		dataIndex : 'start',
		sortable : true,
		filterable : true
	}, {
		text : 'Duration',
		flex : 1,
		dataIndex : 'duration',
		sortable : true,
		filterable : true
	}, {
		text : 'Success',
		flex : 1,
		dataIndex : 'success',
		sortable : true,
		filterable : true
	}, {
		text : 'Error type',
		flex : 1,
		dataIndex : 'status',
		sortable : true,
		filterable : true
	}, {
		text : 'View',
		flex : 1,
		dataIndex : '',
		sortable : true,
		filterable : true
	}
	
	
	]
});
