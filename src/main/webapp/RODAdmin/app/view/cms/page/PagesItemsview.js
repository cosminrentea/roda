/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.PagesItemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.pagesitemsview',
			itemId : 'pagesitemsview',
			activeItem : 1,
			width : '100%',
			layout : {
				type : 'card',
				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						itemId : 'pgiconview',
						itemSelector : 'div.thumb-wrap',
		//				store : 'cms.page.PageList',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'ft',
									text : 'Layout',
									flex : 1,
									sortable : true,
									dataIndex : 'name',
									filterable : true
								}, {
									text : 'id',
									flex : 1,
									dataIndex : 'id',
									sortable : true,
									filter : {
										type : 'integer'
									}
								}, {
									text : 'Group',
									flex : 1,
									dataIndex : 'directory',
									sortable : true,
									filterable : true
								}, {
									text : 'Pages',
									flex : 1,
									dataIndex : 'pagesnumber',
									sortable : true,
									filterable : true
								}],
						dockedItems : [{
									xtype : 'toolbar',
									itemId : 'pgiconviewtoolbar',
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
						store : 'cms.pages.PageTree',
						itemId : 'pgfolderview',
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
									itemId : 'pgfolderviewtoolbar',
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
									text : 'Pages',
									flex : 2,
									sortable : false,
									dataIndex : 'title',
									editor : {
										xtype : 'textfield'
									},
									filter : {
										type : 'string'
									}
								}, {
									xtype: 'templatecolumn',
									text: 'lang',
									tpl: '<div class="lang_{lang}">&nbsp;</div>',
									flex : 1,
									sortable : false,
									filterable : false,
									width:'20'
								},{
									text : 'url',
									flex : 2,
									dataIndex : 'url',
									sortable : false,
									filterable : true
								},{
									text : 'layout',
									flex : 1,
									dataIndex : 'layout',
									sortable : false,
									filterable : true
								},{
									text : 'cacheable',
									flex : 1,
									dataIndex : 'cacheable',
									sortable : false,
									filterable : true
								},{
					            	xtype: 'booleancolumn',	
									text : 'searchable',
						            trueText: 'Yes',
						            falseText: 'No', 
									flex : 1,
									dataIndex : 'searchable',
									sortable : false,
									filterable : true
								}
								
								
								
								]
					}]
		});
