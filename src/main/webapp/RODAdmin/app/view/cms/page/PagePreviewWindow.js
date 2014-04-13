/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.PagePreviewWindow', {

//    extend : 'RODAdmin.view.common.WindowForm',
  extend : 'Ext.window.Window',
	alias : 'widget.pagepreview',

    height : '70%',
    width : '60%',

    requires : [
	    'RODAdmin.util.Util'
    ],

    layout : {
	    type : 'fit'
    },

    config : {
	    cnode : {}
    },

    initComponent : function() {
	    var me = this;
	    Ext.applyIf(me, {
	    	items : [
		            {
		            	xtype: 'panel',
		            	itemId: 'pgpreview',
		        //    	title: 'preview',
		            	overflowY:'scroll',
		            	overflowX:'scroll',
		            	
		            }
		            ],
		            
//			dockedItems : 
//			{
//				dock : 'top',
//				xtype : 'toolbar',
//					items : [{
//							xtype : 'tbfill'
//							},
//							{
//					            xtype: 'button',
//					            text: 'Cancel',
//					            itemId: 'cancel',
//					            iconCls: 'cancel'
//					        },
//			               ]
//			}
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});