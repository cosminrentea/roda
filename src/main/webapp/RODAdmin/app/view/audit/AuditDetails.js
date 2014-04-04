/**
 * 
 */
Ext.define('RODAdmin.view.audit.AuditDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.auditdetails',
			itemId: 'auditdetails',
            layout: {
                         type:'vbox',
 //                      padding:'5',
                         align:'center',
                         align:'stretch'
                         },            
			id: 'auditdetails',
			items : [
			{
				xtype: 'revisionproperties',
//				height: '80%',
				flex:3
//				border : true,
//	            layout : 'fit',
//	            html: 'panel 1'
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