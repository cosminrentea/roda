/**
 * 
 */
Ext.define('RODAdmin.view.cron.action.Cronactionsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.cronactionsview',
			itemId : 'cronactionsview',
			activeItem : 0,
			width : '100%',
			layout : {
				type : 'card',
//				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						itemId : 'croniconview',
						store : 'cron.ActionList',
						itemSelector : 'div.thumb-wrap',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'actionname',
									text : 'Name',
									flex : 1,
									sortable : true,
									dataIndex : 'name',
									filterable : true
								}, {
									text : 'Cron',
									flex : 1,
									dataIndex : 'cron',
									sortable : true,
									filterable : true
								}, {
									text : 'Last execution time',
									flex : 1,
									dataIndex : 'timestamp_last_execution',
									sortable : true,
									filterable : true
								}, {
									text : 'Status',
									flex : 1,
									dataIndex : 'enabled',
									sortable : true,
									filterable : true
								}, {
									text : 'Next execution',
									flex : 1,
									dataIndex : 'timestamp_next_execution',
									sortable : true,
									filterable : true
								}
								
								
								],
						dockedItems : [{
									xtype : 'toolbar',
									itemId : 'croniconviewtoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'button',
												itemId : 'showfilterdata',
												text : 'All Filter Data',
												tooltip : 'Get Filter Data for Grid'
											}, {
												text : 'Clear Filter Data',
												xtype : 'button',
												itemId : 'clearfilterdata'
											}, {
												text : 'Refresh',
												xtype : 'button',
												itemId : 'refreshgrid'
											}
											
											]
								}]
					
					
			}
			]
		});