/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.LayoutDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.layoutdetails',
//			store: 'cms.layoutdetails',
			itemId: 'layoutdetails',
            layout: {
                         type:'vbox',
 //                        padding:'5',
                         align:'center',
                         align:'stretch'
                         },            
			items : [
			{
				xtype: 'layoutproperties',
//				height: '80%',
				flex:3
//				border : true,
//	            layout : 'fit',
//	            html: 'panel 1'
			},
			{
				xtype: 'layoutusage',
				collapsible: true,
				//height: '20%',				
				flex:1
//				border : true,
//	            layout : 'fit',
				}
			]
	});