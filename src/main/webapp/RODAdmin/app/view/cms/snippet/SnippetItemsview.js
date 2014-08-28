/**
 * 
 */
Ext.define('RODAdmin.view.cms.snippet.SnippetItemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.snippetitemsview',
			itemId : 'snippetitemsview',
			activeItem : 1,
			width : '100%',
			layout : {
				type : 'card',
				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						itemId : 'sniconview',
						itemSelector : 'div.thumb-wrap',
						store : 'cms.snippet.Snippet',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [
						           	{	
						           		text : 'id',
						           		flex : 1,
						           		dataIndex : 'indice',
						           		sortable : true,
						           		filter : {
						           			type : 'integer'
						           		}
						           	},
						           {
									itemId : 'ft',
									text : 'Layout',
									flex : 1,
									sortable : true,
									dataIndex : 'name',
									filterable : true
								}, {
									text : 'Group',
									flex : 1,
									dataIndex : 'directory',
									sortable : true,
									filterable : true
								}],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'sniconviewtoolbar',
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
						store : 'cms.snippet.SnippetTree',
						itemId : 'snfolderview',
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
									itemid : 'snfolderviewtoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											},
//											{
//												xtype : 'button',
//												itemId : 'showfilterdata',
//												text : 'All Filter Data',
//												tooltip : 'Get Filter Data for Grid'
//											}, {
//												text : 'Clear Filter Data',
//												xtype : 'button',
//												itemId : 'clearfilterdata'
//											},

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
									text : 'Snippets',
									flex : 2,
									sortable : false,
									dataIndex : 'name',
									editor : {
										xtype : 'textfield'
									},
									filter : {
										type : 'string'
									}
								}]
					}]
		});
