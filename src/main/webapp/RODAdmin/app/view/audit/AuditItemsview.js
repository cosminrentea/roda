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
					//	store : 'audit.revisions',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'id',
									text : 'Revision',
									flex : 1,
									sortable : true,
									dataIndex : 'name',
									filterable : true
								}, {
									text : 'Objects modified',
									flex : 1,
									//dataIndex : 'modobj',
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
					}, {
						store : 'cms.files.FileTree',
						itemId : 'auditobjects',
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
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit : 2,
									listeners : {
										beforeedit : function(editor, e) {
											console.log(e.record.data.filetype);
											if (e.record.data.filetype == 'folder') {
												return true;
											} else {
												return false;
											}
										}
									}
								})
						],
						columns : [{
									xtype : 'treecolumn',
									itemId : 'ft',
									text : 'Object',
									flex : 2,
									sortable : false,
									dataIndex : 'name',
									filter : {
										type : 'string'
									}
								}, {
									text : 'revisions',
									flex : 1,
									dataIndex : 'revisions',
									sortable : false,
									filterable : true
								}]
					}, {
//						store : 'cms.files.FileTree',
						itemId : 'auditusers',
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
									itemid : 'audituserstoolbar',
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
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit : 2,
									listeners : {
										beforeedit : function(editor, e) {
											console.log(e.record.data.filetype);
											if (e.record.data.filetype == 'folder') {
												return true;
											} else {
												return false;
											}
										}
									}
								})
						],
						columns : [{
									xtype : 'treecolumn',
									itemId : 'ft',
									text : 'Object',
									flex : 2,
									sortable : false,
									dataIndex : 'name',
									filter : {
										type : 'string'
									}
								}, {
									text : 'revisions',
									flex : 1,
									dataIndex : 'revisions',
									sortable : false,
									filterable : true
								}]
					}, {
//						store : 'cms.files.FileTree',
						itemId : 'auditdates',
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
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit : 2,
									listeners : {
										beforeedit : function(editor, e) {
											console.log(e.record.data.filetype);
											if (e.record.data.filetype == 'folder') {
												return true;
											} else {
												return false;
											}
										}
									}
								})
						],
						columns : [{
									xtype : 'treecolumn',
									itemId : 'ft',
									text : 'Object',
									flex : 2,
									sortable : false,
									dataIndex : 'name',
									filter : {
										type : 'string'
									}
								}, {
									text : 'revisions',
									flex : 1,
									dataIndex : 'revisions',
									sortable : false,
									filterable : true
								}]
					}


					
					
					
					
					]
		});
