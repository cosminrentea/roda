Ext.define('RODAdmin.view.cron.actions.Cronactionsview', {
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
						itemSelector : 'div.thumb-wrap',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'ft',
									text : 'Name',
									flex : 1,
									sortable : true,
									dataIndex : 'name',
									filterable : true
								}, {
									text : 'Type',
									flex : 1,
									dataIndex : 'type',
									sortable : true,
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
									dataIndex : 'lastextime',
									sortable : true,
									filterable : true
								}, {
									text : 'Status',
									flex : 1,
									dataIndex : 'status',
									sortable : true,
									filterable : true
								}, {
									text : 'Next execution',
									flex : 1,
									dataIndex : 'nextexec',
									sortable : true,
									filterable : true
								}
								
								
								],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'croniconviewtoolbar',
									id : 'croniconviewtoolbar',
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
											}]
								}]
					
					
			}
			]
		});