Ext
		.define(
				'databrowser.view.SeriesMembersView',
				{
					extend : 'Ext.grid.Panel',
					alias : 'widget.seriesmembersview',
					autoRender : true,
					width : '100%',
					header : true,
					hideHeaders : false,
					initComponent : function() {
						var me = this;
 				        Ext.applyIf(me, {
						columns:
								[
										{
											xtype : 'gridcolumn',
											dataIndex : 'an',
											hideable : false,
											text : 'an',
											width : 50,
											fixed : true
										},
										{
											xtype : 'gridcolumn',
											dataIndex : 'name',
											hideable : false,
											text : 'name',
											flex : 1
										},
										{
											xtype : 'gridcolumn',
											dataIndex : 'countries',
											hideable : false,
											text : 'Country',
											width : 100,
											fixed : true
										},
										{
											xtype : 'gridcolumn',
											hideable : false,
											text : 'Act',
											width : 50,
											fixed : true,
											renderer : function(value, meta,
													record, row, col) {
												console.log(record);
												return '<a href=javascript:Ext.getCmp(\'seriesview\').loadStudy('
														+ record.data.indice
														+ ');>View</a>';

											},
										} ],
								 bbar: new Ext.PagingToolbar({
								 pageSize: 5,
								 store: this.store,
								 displayInfo: true,
								 displayMsg: 'Displaying items {0} - {1} of {2}',
								 emptyMsg: "No items"
								 }),
 				        });			
								me.callParent(arguments);
					}
				});
