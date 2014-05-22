/**
 * 
 */
Ext.define('RODAdmin.view.studies.EditStudyWindow', {
	             
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.studyedit',

	height : '90%',
	width : '60%',
//	singleton:true,
	closeAction: 'destroy',
	id:'studyedit',
	
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
									        itemId: 'studyyear',
        									fieldLabel: 'Year',
        									name: 'an',
        									value: ''
									},{
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
										},{
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
							store : Ext.create('RODAdmin.store.studies.StudyInfo'),
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