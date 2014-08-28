/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.AddVariable', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.addvariable',
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
				itemId : 'variableform',
				autoScroll : true,
				title : 'Question',
				bodyPadding : 5,
				items : [{
							xtype : 'fieldset',
							title : 'Variable Information',
							itemId : 'varinfo',
							anchor : '100%',
							collapsible : true,
							items : [{
										xtype : 'combo',
										fieldLabel : 'Question',
										itemId : 'qcombo',
										name : 'oqtext',
										valueField : 'id',
										queryMode : 'local',
										store : 'studies.CBEditor.Questions',
										displayField : 'text',
										autoSelect : true,
										forceSelection : true,
										anchor : '100%'
									}, {
										xtype : 'textfield',
										name : 'name',
										fieldLabel : 'Name',
										anchor : '100%'
									}, {
										xtype : 'textareafield',
										name : 'label',
										fieldLabel : 'Label',
										anchor : '100%'
									}]
						}, {
							xtype : 'fieldset',
							title : 'Response information',
							itemId : 'vrinformation',
							layout : 'card',
							activeItem : 0,
							items : [{
										xtype : 'container',
										html : 'Please select a response type'
									}, {
										xtype : 'panel',
										itemId : 'coderesp',
										title : 'Code response',
										items : [{
											xtype : 'grid',
											itemId : 'qcoderesp',
											store : 'studies.CBEditor.Qcoderesp',
											columns : [{
														name : 'id',
														dataIndex : 'id',
														hidden : true
													}, {
														itemId : 'cresplabel',
														text : 'Label',
														flex : 1,
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
														flex : 2,
														dataIndex : 'value',
														editor : {
															xtype : 'textfield',
															allowBlank : false
														}
													}, {
														itemId : 'cresplang',
														text : 'Original language',
														sortable : true,
														flex : 1,
														dataIndex : 'lang'
														,
													}]
										}]
									}, {
										xtype : 'panel',
										itemId : 'categoryresp',
										title : 'Category response',
										items : [{
											xtype : 'grid',
											itemId : 'qcatresp',
											store : 'studies.CBEditor.Qcatresp',
											columns : [{
														name : 'id',
														dataIndex : 'id',
														hidden : true
													}, {
														itemId : 'cresplabel',
														text : 'Label',
														sortable : true,
														flex : 3,
														dataIndex : 'label',
														editor : {
															xtype : 'textfield',
															allowBlank : false
														}
													}, {
														itemId : 'cresplang',
														text : 'Original language',
														sortable : true,
														dataIndex : 'lang'
														,
													}]
										}]
									}, {
										xtype : 'panel',
										itemId : 'numericresp',
										layout : 'anchor',
										bodyPadding : 5,
										title : 'Numeric response',
										items : [{
											xtype : 'textfield',
											itemId : 'qnrtype',
											name : 'qnrtype',
											disabled : true,
											fieldLabel : 'Numeric response type',
											anchor : '100%'
										}, {
											xtype : 'textfield',
											name : 'qnrlow',
											itemId: 'qnrlow',
											disabled : true,
											fieldLabel : 'Low',
											anchor : '100%'
										}, {
											xtype : 'textfield',
											fieldLabel : 'High',
											itemId: 'qnrhigh',
											disabled : true,
											name : 'qnrhigh',
											anchor : '100%'
										}]
									}]
							// end response

						}, {
							xtype : 'fieldset',
							title : 'Missing values',
							itemId : 'vmissing',
							items : [{
										xtype : 'grid',
										itemId : 'missing',
										store : 'studies.CBEditor.Qmissing',
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
													dataIndex : 'value'
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