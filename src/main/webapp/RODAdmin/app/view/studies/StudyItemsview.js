/**
 * 
 */
Ext.define('RODAdmin.view.studies.StudyItemsview', {
			extend : 'Ext.panel.Panel',
			/**
			 * @config
			 */
			alias : 'widget.studyitemsview',
			itemId : 'studyitemsview',
			activeItem : 1,
			width : '100%',
			layout : {
				type : 'card',
				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						itemId : 'sticonview',
						itemSelector : 'div.thumb-wrap',
						store : 'studies.Study',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [
						           	{
						           		text : 'id',
						           		width : 25,
						           		dataIndex : 'indice',
						           		sortable : true,
						           		filter : {
						           			type : 'integer'
						           		}
						           	},
						           {
									itemId : 'ft',
									text : 'Study',
									flex : 1,
									sortable : true,
									dataIndex : 'name',
									filterable : true
								}],
						dockedItems : [{
									xtype : 'toolbar',
									itemid : 'sticonviewtoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											},{
												text : 'Reload Grid',
												xtype : 'button',
												itemId : 'reloadgrid'
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
											}]
								}]
					}
					, {
						store : 'studies.StudyTree',
						itemId : 'stfolderview',
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
									itemid : 'stfolderviewtoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											},
											/*{
												xtype : 'button',
												itemId : 'showfilterdata',
												text : 'All Filter Data',
												tooltip : 'Get Filter Data for Grid'
											}, {
												text : 'Clear Filter Data',
												xtype : 'button',
												itemId : 'clearfilterdata'
											},*/

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
									text : 'Studies',
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
									text : 'description',
									flex : 1,
									dataIndex : 'description',
									sortable : false,
									filterable : true
								}]
					}]
		});
