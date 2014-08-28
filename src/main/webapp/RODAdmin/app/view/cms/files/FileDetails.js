/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.FileDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.filedetails',
			store: 'cms.filedetails',
			itemId: 'filedetails',
            layout: {
                         type:'vbox',
                         padding:'5',
                         align:'center',
                         align:'stretch'
                         },            
			items : [
			{
				xtype: 'fileproperties',
//				height: '30%',
				flex:1
//				border : true,
//	            layout : 'fit',
//	            html: 'panel 1'
			},
			{
				xtype: 'filespecificproperties',
				collapsible: true,				
//				height: '30%',
				flex:1,
//				border : true,
//	            layout : 'fit',
				},
			{
				xtype: 'fileusage',
				collapsible: true,
//				height: '30%',				
				flex:1,
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