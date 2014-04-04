/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.PageDetails', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.pagedetails',
//			store: 'cms.layoutdetails',
			itemId: 'pagedetails',
            
			id: 'pagedetails',
		    dockedItems : [
		           	    {
		           	        xtype : 'toolbar',
		           	        itemid : 'pgproptoolbar',
		           	        id : 'pgproptoolbar',
		           	        dock : 'bottom',
		           	        items : [
		           	                {
		           		                xtype : 'tbfill'
		           	                }, {
		           	                    xtype : 'button',
		           	                    // id: 'editfile',
		           	                    itemId : 'editpage',
		           	                    text : 'Edit',
		           	                    tooltip : 'Edit this page'
		           	                }, {
		           	                    xtype : 'button',
		           	                    // id: 'deletefile',
		           	                    itemId : 'deletepage',
		           	                    text : 'Delete',
		           	                    tooltip : 'Deletes the page'
		           	                }, {
		           	                    xtype : 'button',
		           	                    itemId : 'getpageaudit',
		           	                    text : 'Page History',
		           	                    tooltip : 'Get Page History'
		           	                }
		           	        ]
		           	    }
		               ],
			items : [
			{
				title: 'Page properties',	
				xtype: 'pageproperties',
				height: '100%',
			},
			{
				title: 'Page code',	
				xtype: 'pagecode',
				height: '100%',
			},
			{
				title: 'Page preview',
				
			},
			{
				title: 'Page Access',
				
			}

			]
	});