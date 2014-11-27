Ext.define('databrowser.view.TopicView',
				{
					extend : 'Ext.grid.Panel',
					alias : 'widget.topicview',
					autoRender : true,
//					width : '100%',
					header : true,
					hideHeaders : true,
					title : translations.catalogview,
					catalogid : 0,
					config : {
						currentview : 'simple',
					},
					loadStudy : function(id) {
						var catalogtitle = this.title;
						var catalogindice = this.catalogid;
						console.log(catalogindice);
						var stitle = this.getView().store.findRecord('indice',
								id).data.name;
						var san = this.getView().store.findRecord('indice', id).data.an;
						var dbcard = Ext.getCmp('dbcard');
						dbcard.layout.setActiveItem('studyview');
						var studyviewob = Ext.getCmp('studyview');
						studyviewob.setTitle(catalogtitle + ' -> ' + san
								+ ' - ' + stitle);
						studyviewob.loaddata(id);
					},
					// store: 'CatalogStore',
					loaddata : function(id) {
						// var dgrid = Ext.getCmp('DetailsGridView');
						// var gridtab = Ext.getCmp('seriesstudies');
						this.setLoading(translations.loading);
						var cStore = Ext.StoreManager.get('TopicStore');
						cStore.load({
							id: id, //set the id here
							scope : this,

							callback : function(records, operation, success) {
								this.setLoading(false);
								console.log(success);
								if (success) {
									var rec = cStore.first();
									console.log('loaddata topics');
									console.log(rec.studiesStore);
									// dgrid.update(records[0].data);
									this.getView().bindStore(rec.studiesStore);
								}
							}
						});
					},
					// store: 'CatalogStore',
					initComponent : function() {
						var me = this;
						Ext
								.applyIf(
										me,
										{
											viewConfig : {
												getRowClass : function(record,
														rowIndex, rowParams,
														store) {
													return 'x-hide-display';
												},
												frame : true,
												id : 'DetailsGridView',
												trackOver : false,
												stripeRows : false

											},
											dockedItems : [
													{
														xtype : 'toolbar',
														dock : 'top',
														itemId : 'DataBrowserToolbar',
														width : 300,
														items : [
																{
																	xtype : 'textfield',
																	itemId : 'searchbox',
																	id : 'topicSearch',
																	enableKeyEvents : true,
																	width : 190,
																	emptyText : 'Cautare locala'
																},
																{
																	xtype : 'tbseparator',
																	id : 'DataBrowserToolbarSeparator1'
																},
																{
																	xtype : 'tbfill',
																},
																{
																	xtype : 'buttongroup',
																	autoRender : false,
																	itemId : 'SMCButtonGroup',
																	// width:
																	// 72,
																	header : false,
																	frame : false,
																	title : 'Buttons',
																	columns : 3,
																	items : [
																			{
																				xtype : 'button',
																				itemId : 'SButton',
																				enableToggle : true,
																				icon : databrowser.util.Globals['contextPath'] + '/resources/root/img/simple.png',
																				toggleGroup : 'SMCButtonGroup',
																				listeners : {
																					click : {
																						fn : me.onSButtonClick,
																						scope : me
																					}
																				}
																			},
																			{
																				xtype : 'button',
																				itemId : 'MButton',
																				enableToggle : true,
																				icon : databrowser.util.Globals['contextPath'] + '/resources/root/img/middle.png',
																				toggleGroup : 'SMCButtonGroup',
																				listeners : {
																					click : {
																						fn : me.onMButtonClick,
																						scope : me
																					}
																				}
																			},
																			{
																				xtype : 'button',
																				itemId : 'CButton',
																				enableToggle : true,
																				icon : databrowser.util.Globals['contextPath'] + '/resources/root/img/complex.png',
																				toggleGroup : 'SMCButtonGroup',
																				listeners : {
																					click : {
																						fn : me.onCButtonClick,
																						scope : me
																					}
																				}
																			} ]
																} ]
													},
													{
														xtype : 'pagingtoolbar',
														dock : 'bottom',
														id : 'PagingToolbar',
														//width : 800,
														afterPageText : 'din {0}',
														beforePageText : 'Pagina',
														displayInfo : true,
														displayMsg : 'Afisare {0} - {1} din {2}',
														emptyMsg : 'Nu exista date',
														firstText : 'Prima pag.',
														lastText : 'Ultima',
														nextText : 'Prima',
														prevText : 'Anterior',
														refreshText : 'Actualizare',
														// store:
														// 'CatalogDetailStore',
														items : [ {
															xtype : 'textfield',
															id : 'NumberOfRecords',
															width : 89,
															fieldLabel : 'Inregistrari',
															labelPad : 0,
															labelWidth : 60,
															value : 20,
															enableKeyEvents : true,
															vtype : '',
															listeners : {
																blur : {
																	fn : me.onNumberOfRecordsBlur,
																	scope : me
																},
																specialkey : {
																	fn : me.onNumberOfRecordsSpecialkey,
																	scope : me
																}
															}
														} ]
													} ],
											features : [ {
												ftype : 'rowbody',
												getAdditionalData : function(
														data, idx, record, orig) {
													var headerCt = this.view.headerCt, colspan = headerCt.getColumnCount();
													var body;
													
													
													
													
													var catview = Ext.ComponentQuery.query('topicview');
													var cview = catview[0].getCurrentview();
													if (cview == 'medium'|| cview == 'complex') {
														body = ''
																+ '<div style="margin: 0px 0px 0px 0px; width: 100%">'
																+ '<table style="table-layout: fixed; width: 100%">'
																+ '<colgroup>'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 11%" />'
																+ '<col style="width: 12%" />'
																+ '</colgroup>'
																+ '<tr>'
																+ '<td colspan="6" valign="top">'
																+ '<div style="word-wrap: break-word">'
																+ '<a href="">'
																+ '<p style="font-size: 14px">'
																+ record
																		.get("name")
																+ '<br/>'
																+ '</p>'
																+ '</a>'
																+ '<p style="font-size: 10px">'
																+ '<b>'
																+ record
																		.get("an")
																+ '</b>&nbsp&nbsp&nbsp&nbsp&nbsp'
																+ record
																		.get("author")
																+ '</p>'
																+ '</div>'
																+ '</td>'
																+ '<td colspan="3" valign="top">'
																+ '<div style="word-wrap: break-word">'
																+ '<p style="font-size: 10px">'
																+ '<b>' + translations.archivedate + ':</b> '
																+ record
																		.get("an")
																+ '<br/>'
																+ '<b>' + translations.metadataaccess + ':</b> Open<br/>'
																+ '<b>' + translations.dataaccess+ ':</b> Open<br/>'
																+ '</p>'
																+ '</div>'
																+ '</td>'
																+ '</tr>'
																+ '<tr>'
																+ '<td colspan="9" valign="middle">'
																+ '<div style="width: 100%; word-wrap: break-word">'
																+ '<p style="font-size: 10px">'
																+ record
																		.get("description")
																+ '</p>'
																+ '</div>'
																+ '</td>'
																+ '</tr>'
																+ (cview != 'complex' ? ''
																		: ('<tr>'
																				+ '<td colspan="2" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>' + translations.countries + ':</b> '
																				+ record
																						.get("countries")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>'
																				+ '<td colspan="2" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>'+translations.stdgeocover+': </b>'
																				+ record
																						.get("geo_coverage")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>'
																				+ '<td colspan="2" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>' + translations.stdunitanalysis+ ': </b>'
																				+ record
																						.get("unit_analysis")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>'
																				+ '<td colspan="3" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>'+ translations.stduniverse+':</b> '
																				+ record
																						.get("universe")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>' + '</tr>'))
																+ '</table>'
																+ '</div>'
																+ '<hr style="width: 100%; border: 2px groove">';
													} else {
														console.log('else--------');
														body = ''
																+ '<div margin: 0px 0px 0px 0px style="width: 100%">'
																+ '<table style="table-layout: fixed; width: 100%">'
																+ '<colgroup>'
																+ '<col style="width: 80%" />'
																+ '<col style="width: 20%" />'
																+ '</colgroup>'
																+ '<tr>'
																+ '<td colspan="1" valign="top">'
																+ '<div style="word-wrap: break-word">'
																+ '<p style="font-size: 12px">'
																+ '<b>'
																+ record
																		.get("an")
																+ '</b>&nbsp&nbsp&nbsp&nbsp&nbsp'
																+ '<a href="javascript:Ext.getCmp(\'topicview\').loadStudy('
																+ record
																		.get('indice')
																+ ')">'
																+ '<b>'
																+ record
																		.get("name")
																+ '</b>'
																+ '</a>'
																+ '</p>'
																+ '</div>'
																+ '</td>'
																+ '</tr>'
																+ '</table>'
																+ '</div>';
													}
													return {
														rowBody : body,
														rowBodyCls : this.rowBodyCls,
														rowBodyColspan : colspan
													};
												}
											} ],
											columns : [ {
												xtype : 'gridcolumn',
												flex: 2,
												// width : 465,
												text : 'DetailGridColumn'
											} ]
										});
						me.callParent(arguments);
					},

					onNumberOfRecordsBlur : function(component, e, eOpts) {
						this.store.pageSize = parseInt(component.value, 10);
						var pagingToolbar = Ext.getCmp('PagingToolbar');
						pagingToolbar.doRefresh();
					},

					onNumberOfRecordsSpecialkey : function(field, e, eOpts) {
						if (e.getKey() == e.ENTER) {
							this.store.pageSize = parseInt(field.value, 10);
							var pagingToolbar = Ext.getCmp('PagingToolbar');
							pagingToolbar.doRefresh();
						}
					}
				});