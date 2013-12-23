/**
 * 
 */
Ext.define('RODAdmin.view.cms.snippet.SnippetDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.snippetdetails',
//			store: 'cms.layoutdetails',
			itemId: 'snippetdetails',
            layout: {
                         type:'vbox',
 //                        padding:'5',
                         align:'center',
                         align:'stretch'
                         },            
//			id: 'snippetdetails',
			items : [
			{
				xtype: 'snippetproperties',
//				height: '80%',
				flex:3
//				border : true,
//	            layout : 'fit',
//	            html: 'panel 1'
			},
			{
				xtype: 'snippetusage',
				collapsible: true,
				//height: '20%',				
				flex:1
//				border : true,
//	            layout : 'fit',
				}
			]
			
			
			
			
//			tpl : ['<tpl if="data.filetype == \'folder\'">',
//					'Folder: {data.text} - {data.filesize}',
//					'</tpl>',
//					'<tpl if="data.filetype != \'folder\'">',
//					'File: {data.text} - {data.filesize}',
//					'</tpl>'
//			]
	});