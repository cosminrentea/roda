/**
 * 
 */
Ext.define('RODAdmin.view.studies.StudyDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.studydetails',
//			store: 'cms.layoutdetails',
			itemId: 'studydetails',
            layout: {
                         type:'vbox',
 //                        padding:'5',
                         align:'center',
                         align:'stretch'
                         },            
			id: 'studydetails',
			items : [
			{
				xtype: 'studyproperties',
//				height: '80%',
				flex:3
//				border : true,
//	            layout : 'fit',
//	            html: 'panel 1'
			},
			{
				xtype: 'studyvariables',
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