/**
 * 
 */
Ext.define('RODAdmin.view.studies.AddStudyToGroupWindow', {
	             
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.studygadd',

	height : '90%',
	width : '60%',



	
	requires : ['RODAdmin.util.Util'],

	layout : {
		type : 'border'
	},

    config: {
        cnode: {}
    },


     initComponent : function() {
		var me = this;
		Ext.applyIf(me, {

			items : [{
				region : 'center',
				collapsible : false,
//				width : '50%',
				flex:3,
				split : true,
				layout : 'fit',
				items : [{
					xtype : 'form',
					itemId: 'estudyform',
					bodyPadding : 5,
					layout : {
						type : 'fit',
					},
					items : [{
								xtype : 'fieldset',
						//		flex : 2,
								title : 'Study information',
								defaults : {
									afterLabelTextTpl : RODAdmin.util.Util.required,
									anchor : '100%',
									xtype : 'textfield',
									allowBlank : false,
					layout : {
						type : 'vbox',
//						columns:2
//						align : 'stretch'
					},									
									labelWidth : 60
								},
								items : [
								       {
											xtype: 'textfield',
											fieldLabel : 'Name',
											name : 'name',
											itemId: 'studyname',
											value : ''
										},	{
									        xtype: 'textareafield',
        									fieldLabel: 'Description',
        									itemId: 'description',
        									name: 'description',
											colspan: 2,
        									value: ''
										},	{
											xtype : 'hiddenfield',
											fieldLabel : 'Label',
											name : 'group',
											value : '',
											itemId : 'groupid'
										},	{
											xtype : 'hiddenfield',
											fieldLabel : 'Label',
											name : 'id',
											value : '',
											itemId : 'id'
										},		
										{
										xtype: 'button',
										text: 'Insert collateral',
										listeners : {
										click: function(button,e,eOpts) {
//											button.up('fieldset').add({xtype:'filefield', name: 'file', label:'File'});
//											var toremove = button.up('fieldset').query('displayfield')[1];
//											button.up('fieldset').remove(toremove);
//											button.hide();
//											var values = this.form.getFieldValues();
											RODAdmin.util.Alert.msg('We try here');
											}
										}
										}]
							}
					]
				}]
			}]
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});