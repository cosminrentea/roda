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
//						var series = Ext.getCmp('sedetails').data.name;
						var gridtab = Ext.getCmp('seriesstudies');
//						var stitle = gridtab.getView().store.findRecord('indice', id).data.name;
//						var san = gridtab.getView().store.findRecord('indice',id).data.an;
						var dbcard = Ext.getCmp('dbcard');
						dbcard.layout.setActiveItem('studyseriesview');
						var studyviewob = Ext.getCmp('studyseriesview');
//						studyviewob.setTitle(series + ' -> ' + san + ' - '	+ stitle);
						studyviewob.loaddata(id);
					},
					// asta pentru linkul de sus
					loadSeries : function(id) {

					},

					loaddata : function(id) {

						// asta e o varianta idioata determinata oarecum de
						// incapacitatea autorilor extjs de a explica cum se fac
						// rahaturile simple
				    	this.setActiveTab(0);
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
							        var templatedata = records[0].data;
							        if (rec.personsStore) {
							        	var personsdata = Ext.Array.pluck(rec.personsStore.data.items,'data');
							        }
							        if (rec.orgsStore) {
							        	var orgsdata = Ext.Array.pluck(rec.orgsStore.data.items,'data');
							        }
							        if (rec.keywordsStore) {
							        	var kwsdata = Ext.Array.pluck(rec.keywordsStore.data.items,'data');
							        }
							        if (rec.topicsStore) {
							        	var topicsdata = Ext.Array.pluck(rec.topicsStore.data.items,'data');
							        }
							        templatedata.orgs = orgsdata;
							        templatedata.persons = personsdata;
							        templatedata.keywords = kwsdata;
							        templatedata.topics = topicsdata;
							        dtab.update(templatedata);
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
										'<p>{description}</p>', '</div>',
                                        '<table width="100%" border="0" cellspacing="2" class="sdetailstbl"',
                                        '<tr><th>'+ translations.stduniverse +'</th><td> {universe}</td></tr>',
                                        '<tr><th>'+ translations.stdgeocover +'</th><td> {geo_coverage}</td></tr>',
                                        '<tr><th>'+ translations.stdgeounit +':</th><td> {geo_unit}</td></tr>',
                                        '<tr><th>'+ translations.topics +':</th><td>', 
                                        '<tpl for="topics">',
                                        '{translation}, ',
                                        '</tpl>',
                                        '</td></tr>',
                                        '<tr><th>'+ translations.keywords +':</th><td>', 
                                        '<tpl for="keywords">',
                                        '{name}, ',
                                        '</tpl>',
                                        '</td></tr>', '</table>',
                                        '</div>',
                                        '<table width="100%" border="0" cellspacing="2" class="persorgcontainer">',
                                        '<tr><td width="50%" valign="top"><div class="orgs"><table>',
                                        '<tpl for="orgs">',
                                       	'<tr><td class="orgic"></td><td>{fname} {lname}</td></tr>',
                                        '</tpl>',
                                        '</table></div></td>',
                                        '<td width="50%" valign="top">',
                						'<div class="persons"><table>',
                                        '<tpl for="persons">',
                                    		'<tr><td class="personic"></td><td>{fname} {lname}</td></tr>',
                                        '</tpl></table></div>',
                                        '</td>',
                                        '</tr>',
                						'</table>'
								),
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