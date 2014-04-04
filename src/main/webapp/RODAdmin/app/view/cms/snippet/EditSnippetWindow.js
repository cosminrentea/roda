/**
 * 
 */
Ext.define('RODAdmin.view.cms.snippet.EditSnippetWindow', {
	             
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.snippetedit',

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
					itemId: 'esnippetform',
					bodyPadding : 5,
					layout : {
						type : 'fit',
					},
					items : [{
								xtype : 'fieldset',
						//		flex : 2,
								title : 'Snippet information',
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
											itemId: 'snippetname',
											value : ''
										}, {
									        xtype: 'codemirror',
        									fieldLabel: 'Content',
											anchor: '-98, 80%',
        									itemId: 'content',
        									name: 'content',
        									mode: 'htmlmixed',
        									listModes:'',
        									showModes: false,
        									pathModes: 'CodeMirror-2.02/mode',
        									pathExtensions: 'CodeMirror-2.02/lib/util',
        									flex:1,
        									value: ''
										},{
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
				flex:1,
				split : true,
				layout : 'fit',
				items : [{
							xtype : 'treepanel',
							store : Ext.create('RODAdmin.store.cms.snippet.GroupTree'),
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