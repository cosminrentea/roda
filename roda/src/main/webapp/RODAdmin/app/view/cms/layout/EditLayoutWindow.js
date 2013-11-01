Ext.define('RODAdmin.view.cms.layout.EditLayoutWindow', {
	             
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.layoutedit',

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
					itemId: 'elayoutform',
					bodyPadding : 5,
					layout : {
						type : 'fit',
					},
					items : [{
								xtype : 'fieldset',
						//		flex : 2,
								title : 'Layout information',
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
									        xtype: 'displayfield',
									        itemId: 'parentdfield',
        									fieldLabel: 'Parent Folder',
        									name: 'directory',
        									value: ''
									},	{
											xtype: 'textfield',
											fieldLabel : 'Name',
											name : 'name',
											itemId: 'layoutname',
											value : ''
										},	{
									        xtype: 'textareafield',
        									fieldLabel: 'Description',
        									itemId: 'description',
        									name: 'description',
											colspan: 2,
        									value: ''
										},{
									        xtype: 'textareafield',
        									fieldLabel: 'Content',
											anchor: '100%, 70%',
        									itemId: 'content',
        									name: 'content',
											//colspan: 2,
        									flex:1,
        									value: ''
										},	{
											xtype : 'hiddenfield',
											fieldLabel : 'Label',
											name : 'parent',
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
			}, {
				region : 'west',
				collapsible : true,
//				width : '50%',
				flex:1,
				split : true,
				layout : 'fit',
				items : [{
							xtype : 'treepanel',
							store : Ext.create('RODAdmin.store.cms.layout.GroupTree'),
							itemId : 'groupselect',
							displayField : 'name',
							useArrows : true,
							rootVisible : false,
							multiSelect : false,
							singleExpand : false,
							allowDeselect : true,
							autoheight : true
						}]
			}]
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});