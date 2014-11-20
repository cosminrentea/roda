/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.PageDetails', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.pagedetails',
//			store: 'cms.layoutdetails',
			itemId: 'pagedetails',
		    dockedItems : [
		           	    {
		           	        xtype : 'toolbar',
		           	        itemId : 'pgproptoolbar',
		           	        dock : 'bottom',
		           	        items : [
		           	                {
		           		                xtype : 'tbfill'
		           	                }, {
		           	                    xtype : 'button',
		           	                    itemId : 'editpage',
		           	                    text : translations.tedit,
		           	                    tooltip : 'Edit this page'
		           	                }, {
		           	                    xtype : 'button',
		           	                    itemId : 'deletepage',
		           	                    text : translations.tdelete,
		           	                    tooltip : 'Deletes the page'
		           	                }
//		           	                , {
//		           	                    xtype : 'button',
//		           	                    itemId : 'getpageaudit',
//		           	                    text : 'Page History',
//		           	                    tooltip : 'Get Page History'
//		           	                }
		           	        ]
		           	    }
		               ],
			items : [
			{
				title: translations.pg_properties,	
				xtype: 'pageproperties',
				height: '100%',
			},
			{
				title: translations.pg_code,	
				xtype: 'pagecode',
				height: '100%',
			},
//			{
//				title: 'Page preview',
//				
//			},
//			{
//				title: 'Page Access',
//				
//			}

			]
	});