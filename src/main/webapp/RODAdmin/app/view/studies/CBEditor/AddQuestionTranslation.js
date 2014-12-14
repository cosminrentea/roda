/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.AddQuestionTranslation', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.addquestiontrans',
	height : '60%',
	width : '40%',
	requires : ['RODAdmin.util.Util'],
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
				itemId : 'trquestionform',
				autoScroll : true,
				title : 'Question',
				bodyPadding : 5,
				items : [{
							xtype : 'fieldset',
							title : 'Question Information',
							itemId : 'questiontrans',
							anchor : '100%',
							collapsible : true,
							items : [{
										xtype : 'combo',
										fieldLabel : 'Original Question',
										itemId : 'origqcombo',
										name : 'oqtext',
										valueField : 'id',
										queryMode : 'local',
										store : 'studies.CBEditor.Questions',
										displayField : 'text',
										autoSelect : true,
										forceSelection : true,
										anchor : '100%'
									}, {
										xtype : 'combo',
										fieldLabel : 'Translation language',
										name : 'lang',
										valueField : 'indice',
										store : 'common.Language',								
										displayField : 'nameSelf',											
										autoSelect : true,
										forceSelection : true,
										anchor : '100%'	
									}, {
										xtype : 'textareafield',
										name : 'text',
										itemId: 'trtextq',
										fieldLabel : 'Translated text',
										anchor : '100%'
									}]
						}, {
							xtype : 'fieldset',
							title : 'Response information',
							itemId : 'qrinformation',
							layout : 'card',
							activeItem : 0,
							items : [{
										xtype : 'container',
										itemId : 'trempty',
										html : ''
									}, {
										xtype : 'panel',
										itemId : 'trcoderesp',
										title : 'Code response',
										items : [{
											xtype : 'grid',
											itemId : 'qcoderesp',
											store : 'studies.CBEditor.TransQcoderesp',
											plugins : [Ext
													.create(
															'Ext.grid.plugin.CellEditing',
															{
																clicksToEdit : 2
															})],
											selModel : {
												selType : 'cellmodel'
											},
											columns : [{
														name : 'id',
														dataIndex : 'id',
														hidden: true
													}, {
														itemId : 'originallabel',
														text : 'Original Label',
														flex : 2,
														sortable : true,
														dataIndex : 'origlabel'
													}, {
														itemId : 'translabel',
														text : 'Translated Label',
														flex : 2,
														sortable : true,
														dataIndex : 'label',
														editor : {
															xtype : 'textfield',
															allowBlank : false
														}
													}, {
														itemId : 'crespvalue',
														text : 'Value',
														sortable : true,
														flex : 1,
														dataIndex : 'value'
													}, {
														itemId : 'cresplang',
														text : 'language',
														sortable : true,
														flex : 1,
														dataIndex : 'origlang'
													}]
										}]

									}, {
										xtype : 'panel',
										itemId : 'trcategoryresp',
										title : 'Category response',
										items : [{
											xtype : 'grid',
											itemId : 'qcatresp',
											store : 'studies.CBEditor.TransQcatresp',
											plugins : [Ext
													.create(
															'Ext.grid.plugin.CellEditing',
															{
																clicksToEdit : 2
															})],
											selModel : {
												selType : 'cellmodel'
											},
											columns : [{
														name : 'id',
														dataIndex : 'id',
														hidden: true,
													}, {
														itemId : 'cresplabel',
														text : 'Translated label',
														sortable : true,
														flex : 3,
														dataIndex : 'label',
														editor : {
															xtype : 'textfield',
															allowBlank : false
														}
													}, {
														itemId : 'origlabel',
														text : 'Original label',
														sortable : true,
														flex : 3,
														dataIndex : 'origlabel',
														editor : {
															xtype : 'textfield',
															allowBlank : false
														}
													}, {
														itemId : 'cresplang',
														text : 'Original language',
														sortable : true,
														dataIndex : 'origlang',
													}
											]
										}]
									}, {
										xtype : 'panel',
										itemId : 'numericresp',
										title : 'Numeric response',
										html: 'No translation needed for numeric response domains'	
										}]
						},{
							xtype: 'fieldset',
							title : 'Missing values',
							itemId : 'qrmissing',
							items: [
{
										xtype : 'grid',
										itemId : 'missing',
										store : 'studies.CBEditor.Qmissing',
										plugins : [Ext.create(
												'Ext.grid.plugin.CellEditing',
												{
													clicksToEdit : 2
												})],
										selModel : {
											selType : 'cellmodel'
										},
										columns : [{
													itemId : 'missinglabel',
													text : 'Label',
													sortable : true,
													flex : 1,
													dataIndex : 'label'
												}, {
													itemId : 'missingvalue',
													text : 'Value',
													sortable : true,
													dataIndex : 'value',
													editor : {
														xtype : 'textfield',
														allowBlank : false
													}
												}]

									}							
							]
						}
						
						
						
						
						]
			}

			]
				// end window items
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});