/**
 * 
 */
Ext.define('RODAdmin.view.user.AdminItemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.adminitemsview',
			itemId : 'adminitemsview',
			activeItem : 0,
			width : '100%',
			layout : {
				type : 'card',
				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						id : 'admingrid',
						itemId : 'admingrid',
						itemSelector : 'div.thumb-wrap',
					//	store : 'audit.revisions',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'id',
									text : 'Username',
									flex : 1,
									sortable : true,
									dataIndex : 'username',
									filterable : true
								}, {
									text : 'Firstname',
									flex : 1,
									dataIndex : 'firstname',
									sortable : true,
								},{
									text : 'Lastname',
									flex : 1,
									dataIndex : 'lastname',
									sortable : true,
								} ],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'admintoolbar',
									id : 'admintoolbar',
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
					}, {
//						store : 'cms.files.FileTree',
						itemId : 'adminpermissions',
						xtype : 'treepanel',
						useArrows : true,
						loadMask:true,
						rootVisible : false,
						multiSelect : false,
						singleExpand : false,
						allowDeselect : true,
						autoheight : true,
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'auditobjectstoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											},
											{
												text : 'Reload Tree',
												xtype : 'button',
												itemId : 'reloadtree'
											}, {
												text : 'Collapse Tree',
												xtype : 'button',
												itemId : 'collapsetree'
											}, {
												text : 'Expand Tree',
												xtype : 'button',
												itemId : 'expandtree'
											}]
								}],
						features : [{
									ftype : 'treeGridFilter'
								}],
						columns : [{
									xtype : 'treecolumn',
									itemId : 'ft',
									text : 'Permissions',
									flex : 2,
									sortable : false,
									dataIndex : 'name',
									filter : {
										type : 'string'
									}
								}, {
									text : 'description',
									flex : 1,
									dataIndex : 'description',
									sortable : false,
									filterable : true
								}]
					}
					]
		});
