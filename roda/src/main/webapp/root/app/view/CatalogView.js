Ext
		.define(
				'databrowser.view.CatalogView',
				{
					extend : 'Ext.grid.Panel',
					alias : 'widget.catalogview',
					autoRender : true,
					// id: 'DetailsGridPanel',
					// itemId: 'DetailsGridPanel',
					width : '100%',
					header : true,
					hideHeaders : true,
					title : 'Catalog view',
					catalogid : 0,
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

						// asta e o varianta idioata determinata oarecum de
						// incapacitatea autorilor extjs de a explica cum se fac
						// rahaturile simple

						// var dgrid = Ext.getCmp('DetailsGridView');
						// var gridtab = Ext.getCmp('seriesstudies');
						var cStore = Ext.StoreManager.get('CatalogStore');
						cStore.load({
							// id: indice, //set the id here
							scope : this,
							callback : function(records, operation, success) {
								if (success) {
									var rec = cStore.first();
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
														id : 'DataBrowserToolbar',
														width : 300,
														items : [
																{
																	xtype : 'textfield',
																	id : 'LocalSearchTextField',
																	width : 190,
																	emptyText : 'Cautare locala'
																},
																{
																	xtype : 'button',
																	id : 'AdvancedSearchButton',
																	width : 156,
																	text : 'Cautare avansata'
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
																	id : 'SMCButtonGroup',
																	// width:
																	// 72,
																	header : false,
																	frame : false,
																	title : 'Buttons',
																	columns : 3,
																	items : [
																			{
																				xtype : 'button',
																				id : 'SButton',
																				enableToggle : true,
																				icon : 'img/simple.png',
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
																				id : 'MButton',
																				enableToggle : true,
																				icon : 'img/mediu.png',
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
																				id : 'CButton',
																				enableToggle : true,
																				icon : 'img/complex.png',
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
														width : 400,
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
													var headerCt = this.view.headerCt, colspan = headerCt
															.getColumnCount();
													var body;

													var sButton = Ext
															.getCmp('SButton'), mButton = Ext
															.getCmp('MButton'), cButton = Ext
															.getCmp('CButton');
													if (cButton.pressed
															|| mButton.pressed) {
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
																+ '<b>Archive date:</b> '
																+ record
																		.get("an")
																+ '<br/>'
																+ '<b>Metadata access:</b> Open<br/>'
																+ '<b>Data access:</b> Open<br/>'
																+ '<a href="">'
																+ '<b>Adauga la catalog</b>'
																+ '</a>'
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
																+ (mButton.pressed ? ''
																		: ('<tr>'
																				+ '<td colspan="2" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>Countries:</b> '
																				+ record
																						.get("countries")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>'
																				+ '<td colspan="2" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>Geographic coverage: </b>'
																				+ record
																						.get("geo_coverage")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>'
																				+ '<td colspan="2" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>Unitatea de analiza: </b>'
																				+ record
																						.get("unit_analysis")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>'
																				+ '<td colspan="3" valign="top">'
																				+ '<div style="word-wrap: break-word">'
																				+ '<p style="font-size: 10px">'
																				+ '<b>Univers:</b> '
																				+ record
																						.get("universe")
																				+ '</p>'
																				+ '</div>'
																				+ '</td>' + '</tr>'))
																+ '</table>'
																+ '</div>'
																+ '<hr style="width: 100%; border: 2px groove">';
													} else {
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
																+ '<a href="javascript:Ext.getCmp(\'catalogview\').loadStudy('
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
												width : 465,
												text : 'DetailGridColumn'
											} ]
										});

						me.callParent(arguments);
					},

					onSButtonClick : function(button, e, eOpts) {
						this.getView().refresh();

					},

					onMButtonClick : function(button, e, eOpts) {
						this.getView().refresh();

					},

					onCButtonClick : function(button, e, eOpts) {
						this.getView().refresh();
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