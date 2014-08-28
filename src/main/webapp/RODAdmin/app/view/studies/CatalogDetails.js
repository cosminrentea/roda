/**
 * 
 */
Ext.define('RODAdmin.view.studies.CatalogDetails', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.catalogdetails',
//			store: 'cms.layoutdetails',
			itemId: 'catalogdetails',
			//            layout: {
//                         type:'vbox',
//                         align:'center',
//                         align:'stretch'
//                         },            
			items : [
			{
				xtype: 'panel',
				itemId: 'cdetails',
				title: 'Catalog details',
                tpl : [
                    	'<div style="padding:10px;">',
                       '<h1> {name}</h1>',
                       '<div class="year">ID: {indice}</div>',
                       '</div>',
               ]				
				
			},
			{
				xtype: 'gridpanel',
				title: 'Studies in catalog',
				itemId: 'catalogstudies',
 			    columns: [
				         {
				           text: 'Id',
				           width: 100,
				           dataIndex: 'indice'
				         },
				         {
				           text: 'Name',
				           flex: 2,
				           dataIndex: 'name'
				         },
				         {
				           text: 'Tip',
				           flex: 1,
				           dataIndex: 'itemtype'
				         }
			             ]	
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