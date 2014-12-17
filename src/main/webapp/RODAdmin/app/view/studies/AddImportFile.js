/**
 * 
 */
Ext.define('RODAdmin.view.studies.AddImportFile', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.addimportfile',
	height : '50%',
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
				itemId : 'fileform',
				autoScroll : true,
				title : 'Question',
				bodyPadding : 5,
				items : [{
							xtype : 'fieldset',
							title : 'File Information',
							itemId : 'fileinfo',
							anchor : '100%',
							collapsible : true,
							items : [
									{
										xtype : 'textfield',
										name : 'uri',
										fieldLabel : 'URI',
										anchor : '100%'
									},	
									{
										xtype : 'filefield',
										name : 'fupload',
										fieldLabel : 'File',
										anchor : '100%',
										itemId : 'fupload'
											
									},	
									{
										xtype : 'combo',
										fieldLabel : 'File type',
										itemId : 'ftcombo',
										name : 'ftype',
										valueField : 'id',
										store : 'studies.CBEditor.FileTypes',
										displayField : 'name',
										autoSelect : true,
										forceSelection : true,
										anchor : '100%'
									}, {
										xtype : 'textfield',
										name : 'name',
										fieldLabel : 'File Name',
										anchor : '100%'
									}, {
										xtype : 'textarea',
										name : 'name',
										fieldLabel : 'File description',
										anchor : '100%'
									}
									
									
									
									]
						}, {
							xtype : 'fieldset',
							title : 'Data File Information',
							itemId : 'datafinfo',
							anchor : '100%',
							collapsible : true,
							items:[
							{
										xtype : 'textfield',
										name : 'cases',
										fieldLabel : 'Number of cases',
										anchor : '100%'
							},{
										xtype : 'textfield',
										name : 'reccount',
										fieldLabel : 'Record count',
										anchor : '100%'
							}
							]
						}
						]
			}]
				// end window items
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});