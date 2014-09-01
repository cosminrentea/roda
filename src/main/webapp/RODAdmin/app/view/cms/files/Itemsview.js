/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.Itemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.itemsview',
			itemId : 'itemsview',
			activeItem : 1,
			width : '100%',
			layout : {
				type : 'card',
				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						itemId : 'iconview',
						itemSelector : 'div.thumb-wrap',
						store : 'cms.files.File',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'ft',
									text : 'File',
									flex : 2,
									sortable : true,
									dataIndex : 'name',
									filterable : true
								}, {
									text : 'alias',
									flex : 1,
									dataIndex : 'alias',
									sortable : true,
									filter : {
										type : 'string'
									}
								}, {
									text : 'type',
									flex : 1,
									dataIndex : 'filetype',
									sortable : true,
									filterable : true
								}, {
									text : 'size',
									flex : 1,
									dataIndex : 'filesize',
									sortable : true,
									filterable : true
								}, {
									text : 'Directory',
									flex : 1,
									dataIndex : 'directory',
									sortable : true,
									filterable : true
								}],
						dockedItems : [{
									xtype : 'toolbar',
									itemId : 'iconviewtoolbar',
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
						itemId : 'folderview',
						xtype : 'treepanel',
						useArrows : true,
						rootVisible : false,
						multiSelect : false,
						singleExpand : false,
						allowDeselect : true,
						autoheight : true,
						dockedItems : [{
									xtype : 'toolbar',
									itemId : 'folderviewtoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											},
											{
												xtype : 'button',
												itemId : 'showfilterdata',
												text : 'All Filter Data',
												tooltip : 'Get Filter Data for Grid'
											}, {
												text : 'Clear Filter Data',
												xtype : 'button',
												itemId : 'clearfilterdata'
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
						viewConfig : {
							plugins : {
								ptype : 'treeviewdragdrop'
							},
							forceFit : true
						},
						columns : [{
									xtype : 'treecolumn',
									itemId : 'ft',
									text : 'Files',
									flex : 2,
									sortable : false,
									dataIndex : 'name',
									editor : {
										xtype : 'textfield'
									},
									filter : {
										type : 'string'
									}
								}, {
									text : 'ID',
									flex : 1,
									dataIndex : 'indice',
									sortable : false
								}, {
									text : 'type',
									flex : 1,
									dataIndex : 'filetype',
									sortable : false,
									filterable : true
								}, {
									text : 'alias',
									flex : 1,
									dataIndex : 'alias',
									sortable : false,
									filterable : true
								}, {
									text : 'size',
									flex : 1,
									dataIndex : 'filesize',
									sortable : false,
									filterable : true
								}]
					}]
		});
