Ext
		.define('databrowser.view.SeriesView',
				{
					extend : 'Ext.tab.Panel',
					// extend: 'Ext.grid.Panel',
					alias : 'widget.seriesview',
					autoRender : true,
					id : 'seriesview',
					// itemId: 'seriesview',
					width : '100%',
					header : true,
					hideHeaders : false,
					store : 'SeriesStore',
					loadStudy : function(id) {
						console.log(id);
						var series = Ext.getCmp('sedetails').data.name;
						var gridtab = Ext.getCmp('seriesstudies');
						var stitle = gridtab.getView().store.findRecord('indice', id).data.name;
						var san = gridtab.getView().store.findRecord('indice',id).data.an;
						var dbcard = Ext.getCmp('dbcard');
						dbcard.layout.setActiveItem('studyview');
						var studyviewob = Ext.getCmp('studyview');
						studyviewob.setTitle(series + ' -> ' + san + ' - '	+ stitle);
						studyviewob.loaddata(id);
					},
					// asta pentru linkul de sus
					loadSeries : function(id) {

					},

					loaddata : function(id) {

						// asta e o varianta idioata determinata oarecum de
						// incapacitatea autorilor extjs de a explica cum se fac
						// rahaturile simple

						var dtab = Ext.getCmp('sedetails');
						var gridtab = Ext.getCmp('seriesstudies');
						var sStore = Ext.StoreManager.get(this.store);
						console.log(gridtab);
						sStore.load({
							id : id, // set the id here
							scope : this,
							callback : function(records, operation, success) {
								if (success) {
									var rec = sStore.first();
									console.log(rec.studiesStore);
									dtab.update(records[0].data);
									gridtab.getView().bindStore(
											rec.studiesStore);
								}
							}
						});
					},
					title : 'Series View',
					items : [
							{
								autoScroll : true,
								layout : 'fit',
								title : 'Detalii',
								id : 'sedetails',
								tpl : new Ext.XTemplate(
										'<div style="padding:10px;">',
										'<H2>{name}</H2>', '<H3>{author}</H3>',
										'<p>{description}</p>', '</div>'),
							},
							{
								autoScroll : true,
								layout : 'fit',
								title : 'Studii',
								id : 'seriesstudies',
								xtype : 'seriesmembersview',
							} ],
					initComponent : function() {
						var me = this;
						me.callParent(arguments);
					}

				});