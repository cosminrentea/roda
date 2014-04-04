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
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'id',
									text : 'Revision',
									flex : 1,
									sortable : true,
									dataIndex : 'revision',
									filterable : true
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
					}
					
					
					
					]
		});
