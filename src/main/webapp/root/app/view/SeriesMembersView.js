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
											text : translations.sermviewan,
											width : 50,
											fixed : true
										},
										{
											xtype : 'gridcolumn',
											dataIndex : 'name',
											hideable : false,
											text : translations.sermviewname,
											flex : 1
										},
										{
											xtype : 'gridcolumn',
											hideable : false,
											text : translations.sermviewact,
											width : 100,
											fixed : true,
											renderer : function(value, meta,
													record, row, col) {
												console.log('lepus');
												console.log(record);
												return '<a href=javascript:Ext.getCmp(\'seriesview\').loadStudy('
														+ record.data.indice
														+ ');>'+ translations.view +'</a>';

											},
										} ],
								 bbar: new Ext.PagingToolbar({
								 pageSize: 5,
								 store: this.store,
								 displayInfo: true,
								 displayMsg: translations.displayitems + ' {0} - {1} '+ translations.of  +' {2}',
								 emptyMsg: translations.noitems
								 }),
 				        });			
								me.callParent(arguments);
					}
				});
