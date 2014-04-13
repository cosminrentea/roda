/**
 * 
 */
Ext.define('RODAdmin.view.audit.AuditItemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.audititemsview',
			itemId : 'audititemsview',
			activeItem : 0,
			width : '100%',
			layout : {
				type : 'card',
				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						id : 'auditrevisions',
						itemId : 'auditrevisions',
						itemSelector : 'div.thumb-wrap',
						store : 'audit.Revisions',
						features : [
						            Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})
								],
						columns : [{
									itemId : 'id',
									text : 'Revision',
									flex : 1,
									sortable : true,
									dataIndex : 'revision',
									filterable : true,
									filter : {
										type : 'integer'
									}
								}, 
								{
									itemId : 'timestamp',
									text : 'Timestamp',
									flex : 2,
									sortable : true,
									dataIndex : 'timestamp',
									filterable : true
								}, 
								{
									text : 'Objects modified',
									flex : 1,
									dataIndex : 'nrobjects',
									sortable : true,
									filter : {
										type : 'integer'
									}
								},
								{
									text : 'User',
									flex : 1,
									dataIndex : 'username',
									sortable : true,
									filter : {
										type : 'string'
									}
								}
								
								],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'auditiconviewtoolbar',
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
					},
					
					{
						xtype : 'grid',
						id : 'auditobjects',
						itemId : 'auditobjects',
						itemSelector : 'div.thumb-wrap',
						store : 'audit.RevisedObjects',
						features : [
						            Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})
								],
						columns : [{
									itemId : 'id',
									text : 'Object',
									flex : 1,
									sortable : true,
									dataIndex : 'object',
									filterable : true,
								},
								{
									text : 'Last revision',
									flex : 1,
									sortable : true,
									dataIndex : 'lastrevision',
									filterable : true,
								},
								{
									text : 'Nr. revisions',
									flex : 1,
									sortable : true,
									dataIndex : 'nrrev',
									filterable : true,
								}
						
						],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'auditiconviewtoolbar',
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
						
						
					}, 
					
					{
						xtype : 'grid',
						id : 'auditusers',
						itemId : 'auditusers',
						itemSelector : 'div.thumb-wrap',
						store : 'audit.RevisedUsers',
						features : [
						            Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})
								],
						columns : [{
									itemId : 'id',
									text : 'User',
									flex : 1,
									sortable : true,
									dataIndex : 'user',
									filterable : true,
								}],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'auditiconviewtoolbar',
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
						
						
					}, 
					{
						xtype : 'grid',
						id : 'auditdays',
						itemId : 'auditdays',
						itemSelector : 'div.thumb-wrap',
						store : 'audit.RevisedDates',
						features : [
						            Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})
								],
						columns : [{
									itemId : 'id',
									text : 'Date',
									flex : 1,
									sortable : true,
									dataIndex : 'date',
									filterable : true,
								} ],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'auditiconviewtoolbar',
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
						
						
					}, 
					]
		});
