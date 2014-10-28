/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.EditLayoutWindow', {
	             
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.layoutedit',

	height : '90%',
	width : '60%',
//	singleton:true,
	closeAction: 'destroy',
	id:'layoutedit',
	
	requires : ['RODAdmin.util.Util','Ext.ux.form.field.CodeMirror'],

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
									},{
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
        									allowBlank : true,
											colspan: 2,
        									value: ''
										},{
									        xtype: 'codemirror',
        									fieldLabel: 'Content',
        									anchor : '-98 80%',
        									itemId: 'content',
        									name: 'content',
        									mode: 'htmlmixed',
        									listModes:'',
        									showModes: false,
        									pathModes: 'CodeMirror-2.02/mode',
        									pathExtensions: 'CodeMirror-2.02/lib/util',
        									flex:1,
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