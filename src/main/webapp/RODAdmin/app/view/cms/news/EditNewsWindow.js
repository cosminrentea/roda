/**
 * 
 */
Ext.define('RODAdmin.view.cms.news.EditNewsWindow', {

	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.newsedit',

	height : '60%',
	width : '60%',

	requires : ['RODAdmin.util.Util'],

	layout : {
		type : 'fit',
//		align : 'stretch'
	},

	config: {
		cnode: {}
	},


	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'form',
				itemId: 'enewsform',
				bodyPadding : 5,
				layout : {
		            type : 'vbox',
		            align : 'stretch'
		        },
					defaults : {
						afterLabelTextTpl : RODAdmin.util.Util.required,
//						anchor : '100%',
						xtype : 'textfield',
						allowBlank : false,
//						layout : {
//							type : 'vbox',
////							columns:2
//							align : 'stretch'
//						},									
						labelWidth : 60
					},
					items : [
					         {
					        	 xtype: 'textfield',
					        	 fieldLabel : 'Title',
					        	 name : 'title',
					        	 itemId: 'newstitle',
					        	 value : ''
					         },{
					        	 xtype : 'hiddenfield',
					        	 fieldLabel : 'Label',
					        	 name : 'id',
					        	 value : '',
					        	 itemId : 'id'
					         },{
					        	 xtype : 'textarea',
					        	 fieldLabel : 'Content',
					        	 name : 'content',
					        	 value : '',
					        	 itemId : 'content',
					        	 flex: 3,
					        	 //anchor : '-98 70%',	 
					         }
					         
					         
					         ]
				}
				]

		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});