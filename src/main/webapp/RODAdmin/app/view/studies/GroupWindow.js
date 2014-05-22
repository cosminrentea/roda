/**
 * 
 */
Ext.define('RODAdmin.view.studies.GroupWindow', {
			extend : 'RODAdmin.view.common.WindowForm',
			alias : 'widget.stgroupadd',

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
												fieldLabel : 'Parent',
												name : 'parent',
												value: '0'
											}, {
												fieldLabel : 'Group name',
												name : 'groupname'
											}, {
												xtype : 'textarea',
												fieldLabel : 'Description',
												name : 'description',
												allowBlank : true,
											}
											
											
											]
								}
						]
					}]
		});