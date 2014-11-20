/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.FolderWindow', {
			extend : 'RODAdmin.view.common.WindowForm',
			alias : 'widget.folderadd',

			height : 250,
			width : 550,

			requires : ['RODAdmin.util.Util'],

			layout : {
				type : 'fit'
			},

			title : 'User',

			items : [{
						xtype : 'form',
						itemId : 'folderform',
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
												fieldLabel : 'folderid',
												name : 'id',
												value: '0'
											},
											{
												xtype : 'hiddenfield',
												fieldLabel : 'parent',
												name : translations.fl_parent,
												value: '0'
											},
											{
												fieldLabel : 'Folder name',
												name : translations.fl_foldername,
											}, {
												xtype : 'textarea',
												fieldLabel : 'Description',
												name : translations.fl_description,
												allowBlank : true,
											}]
								}
						]
					}]
		});