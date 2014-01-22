/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.FileWindow', {
			extend : 'RODAdmin.view.common.WindowForm',
			alias : 'widget.fileadd',

			height : 250,
			width : 550,

			requires : ['RODAdmin.util.Util'],

			layout : {
				type : 'fit'
			},

			title : 'User',

			items : [{
						xtype : 'form',
						bodyPadding : 5,
						frame: true,
						layout : {
							type : 'hbox',
							align : 'stretch'
						},
						items : [{
									xtype : 'fieldset',
									flex : 2,
									title : 'File information',
									defaults : {
										afterLabelTextTpl : RODAdmin.util.Util.required,
										anchor : '100%',
										xtype : 'textfield',
										allowBlank : false,
										labelWidth : 60
									},
									items : [{
												xtype : 'hiddenfield',
												fieldLabel : 'Label',
												name : 'id'
											}, {
												fieldLabel : 'Alias',
												name : 'fileAlias'
											},{
												xtype : 'hiddenfield',
												fieldLabel : 'Parent',
												name : 'folderid',
												value : '',
											},{
												xtype : 'filefield',
												fieldLabel : 'File',
												name : 'content',
												allowBlank : true,
												afterLabelTextTpl : ''

											}]
								}

						]
					}]
		});