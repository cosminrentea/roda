/**
 * 
 */
Ext.define('RODAdmin.view.cron.action.Actions', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cronactions',
	itemId : 'cronactions',
	layout : {
		type : 'border',
		padding : 5
	},
	defaults : {
		split : true
	},

	items : [{
				region : 'west',
				collapsible : true,
				width : '50%',
				split : true,
				layout : 'fit',
				items : [{
							xtype : 'cronactionsview'
						}]
			}, {
				region : 'center',
				collapsible : false,
				width : '50%',
				xtype : 'panel',
				itemId : 'crondetailscontainer',
				id : 'crondetailscontainer',
				title : 'Actions details',
				layout : {
					type : 'fit'
					// padding:'5',
					// align:'center',
					// align:'stretch'
				},
				items : [{
							xtype : 'crondetails'
						}]
			}]
});
