/**
 * 
 */
Ext.define('RODAdmin.view.cron.action.CronDetails', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.crondetails',
			itemId: 'crondetails',
			id: 'crondetails',
		    dockedItems : [
		           	    {
		           	        xtype : 'toolbar',
		           	        itemId : 'cronproptoolbar',
		           	        id : 'cronproptoolbar',
		           	        dock : 'bottom',
		           	        items : [
		           	                {
		           		                xtype : 'tbfill'
		           	                }, {
		           	                    xtype : 'button',
		           	                    // id: 'editfile',
		           	                    itemId : 'editaction',
		           	                    text : 'Edit',
		           	                    tooltip : 'Edit this action'
		           	                }, {
		           	                    xtype : 'button',
		           	                    // id: 'deletefile',
		           	                    itemId : 'deleteaction',
		           	                    text : 'Delete',
		           	                    tooltip : 'Deletes this action'
		           	                }, {
		           	                    xtype : 'button',
		           	                    itemId : 'getactionaudit',
		           	                    text : 'Action History',
		           	                    tooltip : 'Get action History'
		           	                }
		           	        ]
		           	    }
		               ],
			items : [
			{
				title: 'Action properties',	
				xtype: 'actionproperties',
			//	xtype: 'panel',
				height: '100%',
			},
			{
				title: 'Action runs',
				xtype: 'actionruns',
			}
			]
	});