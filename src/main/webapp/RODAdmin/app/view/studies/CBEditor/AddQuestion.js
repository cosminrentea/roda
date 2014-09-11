/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.AddQuestion', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.addquestion',
	height : '80%',
	width : '40%',
	requires : ['RODAdmin.util.Util','Ext.ux.form.field.TreeCombo'],
	
	layout : {
		type : 'fit'
	},
	config : {
		cnode : {},
		mode : 'add',
		editId : 0
	},

	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			layout : 'fit',
			items : [{
				xtype : 'form',
				itemId : 'questionform',
				autoScroll : true,
				bodyPadding : 5,
				items : [{
					xtype : 'fieldset',
					title : 'Question Information',
					itemId : 'questioninfo',
					anchor : '100%',
					collapsible : true,
					items : [{
								xtype : 'textareafield',
								name : 'text',
								fieldLabel : 'Question text',
								anchor : '100%'
							}, {
								xtype : 'treecombo',
								selectChildren : false,
								fieldLabel : 'Concept',
								store : 'studies.CBEditor.ConceptsDisp',
								itemId : 'conceptselector',
								name : 'concept_id',
								displayField : 'text',
								valueField : 'id',
								canSelectFolders : true,
								anchor : '100%',
								itemTreeClick : function(view, record, item,
										index, e, eOpts, treeCombo) {
									var id = record.data.id;
									treeCombo.setValue(id);
									this.collapse();
								}

							}, {
								xtype : 'combo',
								fieldLabel : 'Original language',
								name : 'lang',
								valueField : 'id',
								store : 'studies.CBEditor.Lang',
								displayField : 'name',
								autoSelect : true,
								forceSelection : true,
								anchor : '100%'
							}, {
								xtype : 'combo',
								itemId : 'qrdomain',
								fieldLabel : 'Response domain',
								name : 'respdomain',
								valueField : 'qrdomain',
								queryMode : 'local',
								store : ['String', 'Category', 'Numeric'],
								displayField : 'qrdomain',
								autoSelect : true,
								forceSelection : true,
								anchor : '100%'
							}]
				}, {
					xtype : 'fieldset',
					title : 'Response information',
					collapsible : true,
					itemId : 'qrinformation',
					layout : 'card',
					activeItem : 0,
					items : [{
								xtype : 'container',
								html : 'Please select a response type'
							}, {
								xtype : 'panel',
								itemId : 'catresp',
								title : 'Category response',
								items : [
								         {
												xtype : 'checkbox',
												name : 'intnum',
												fieldLabel: 'Interpretabil numeric',
												anchor : '100%'
								         }, 
								         {
												xtype : 'checkbox',
												name : 'wvalues',
												fieldLabel: 'Cu valori',
												anchor : '100%',
												checked: true	
								         }, 
								         {
									xtype : 'grid',
									itemId : 'qcoderesp',
									store : 'studies.CBEditor.Qcoderesp',

									plugins : [Ext.create(
											'Ext.grid.plugin.CellEditing', {
												clicksToEdit : 2
											})],
									selModel : {
										selType : 'cellmodel'
									},
									tbar : [{
												text : 'Add response code',
												itemId : 'addrespcode'
											}],
									columns : [{
												name : 'id',
												dataIndex : 'id',
												hidden: true
											}, 
											{
												itemId : 'crespvalue',
												text : 'Value',
												sortable : true,
												flex : 1,
												dataIndex : 'value',
												enableLocking : true,
												editor : {
													xtype : 'textfield',
													allowBlank : false
												}
											},
											{
												itemId : 'cresplabel',
												text : 'Label',
												flex : 2,
												sortable : true,
												enableLocking : true,												
												dataIndex : 'label',
												editor : {
													xtype : 'textfield',
													allowBlank : false
												}
											}, {
												itemId : 'cresplang',
												text : 'Original language',
												sortable : true,
												flex : 1,
												dataIndex : 'lang',
												editor: new Ext.form.field.ComboBox({
                    									typeAhead: true,
                    									triggerAction: 'all',
														valueField : 'id',
														displayField : 'name',														
														store: 'studies.CBEditor.Lang'												
													})
											}, {
												xtype : 'actioncolumn',
												width : 30,
												sortable : false,
												menuDisabled : true,
												itemId : 'actionqcoderesp',
												items : [{
													icon : '/roda/admin/images/delete.gif',
													tooltip : 'Delete Plant',
													scope : this,
													handler : function(view,
															rowIndex, colIndex,
															item, e, record,
															row) {
														console
																.log('firing event delete');
														var mygrid = view
																.up('grid');
														mygrid.fireEvent(
																'deleteRecord',
																mygrid, record,
																rowIndex, row);
													}
												}]
											}

									]
								}]
							}, {
								xtype : 'panel',
								itemId : 'stringresp',
								title : 'String response',
								html: 'String response has no options'
							}, {
								xtype : 'panel',
								itemId : 'numericresp',
								layout: 'anchor',
								bodyPadding : 5,
								title : 'Numeric response',
								items : [{
									xtype : 'combo',
									itemId : 'qnrtype',
									fieldLabel : 'Numeric response type',
									name : 'qnrtype',
									valueField : 'qnrtype',
									queryMode : 'local',
									store : ['Integer', 'Decimal','Count', 'Incremental'],
									displayField : 'qnrtype',
									autoSelect : true,
									forceSelection : true,
									anchor : '100%'
								}, {
									xtype : 'checkbox',
									name : 'intcat',
									fieldLabel: 'Interpretabil categorial',
									anchor : '100%'
								},{
									xtype : 'textfield',
									name : 'qnrlow',
									fieldLabel: 'Low',
									anchor : '100%'
								}, {
									xtype : 'textfield',
									fieldLabel: 'High',
									name : 'qnrhigh',
									anchor : '100%'
								}]
							}]
				}, {
					xtype : 'fieldset',
					title : 'Missing information',
					itemId : 'missinginfo',
					collapsible : true,
					// activeItem : 0,
					items : [{
						xtype : 'grid',
						itemId : 'missing',
						store : 'studies.CBEditor.Qmissing',
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit : 2
								})],
						selModel : {
							selType : 'cellmodel'
						},
						tbar : [{
									text : 'Add missing value',
									itemId : 'addmissing'
								}],
						columns : [{
									itemId : 'missinglabel',
									text : 'Label',
									sortable : true,
									flex : 1,
									dataIndex : 'label',
									editor : {
										xtype : 'textfield',
										allowBlank : false
									}
								}, {
									itemId : 'missingvalue',
									text : 'Value',
									sortable : true,
									dataIndex : 'value',
									editor : {
										xtype : 'textfield',
										allowBlank : false
									}
								}, {
									xtype : 'actioncolumn',
									width : 30,
									sortable : false,
									menuDisabled : true,
									itemId : 'missingactions',
									items : [{
										icon : '/roda/admin/images/delete.gif',
										tooltip : 'Delete Missing',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event delete');
											var mygrid = view.up('grid');
											mygrid.fireEvent('deleteRecord',
													mygrid, record, rowIndex,
													row);
										}
									}]
								}]
					}]
				}]
			}

			]
				// end window items
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});