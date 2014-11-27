/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.EditFileWindow', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.fileedit',

	height : 250,
	width : 550,

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
				width : '50%',
				split : true,
				layout : 'fit',
				items : [{
					xtype : 'form',
					itemId: 'editfileform',
					bodyPadding : 5,
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
								items : [
									{
									        xtype: 'displayfield',
									        itemId: 'directory',
        									fieldLabel: 'Parent Folder',
        									name: 'directory',
        									value: ''
									},	{
											xtype : 'hiddenfield',
											fieldLabel : 'Label',
											name : 'folderid',
											value : '',
											itemId : 'hdparent'
										},	{
											xtype : 'hiddenfield',
											fieldLabel : 'Label',
											name : 'id',
											value : '',
											itemId : 'hdid'
										},	{
											xtype: 'textfield',
											fieldLabel : 'Alias',
											name : 'alias',
											itemId: 'filealias',
											value : ''
										},	{
									        xtype: 'displayfield',
        									fieldLabel: 'File',
									        itemId: 'filedfield',
        									name: 'CurrentFile',
        									value: ''
										
										},{
										xtype: 'button',
										text: 'Change attached file',
										listeners : {
										click: function(button,e,eOpts) {
											button.up('fieldset').add({xtype:'filefield', name: 'file', label:'File'});
											var toremove = button.up('fieldset').query('displayfield')[1];
											button.up('fieldset').remove(toremove);
											button.hide();

//											var values = this.form.getFieldValues();
										}
										}
										}]
							}
					]
				}]
			}, {
				region : 'west',
				collapsible : true,
				width : '50%',
				split : true,
				layout : 'fit',
				items : [{
							xtype : 'treepanel',
							store : Ext.create('RODAdmin.store.cms.files.FolderTree'),
							itemId : 'folderselect',
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