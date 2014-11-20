/**
 * 
 */
Ext.define('RODAdmin.view.cms.news.News', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cmsnews',
    		itemId : 'cmsnews',
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
						dockedItems : [{
									dock : 'top',
									xtype : 'toolbar',
									itemId : 'newstoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											} ]
								}],
						items : [{
									xtype : 'newsitemsview'
								}]
					}, {
						region : 'center',
						collapsible : false,
						width : '50%',
						xtype : 'panel',
						itemId : 'newsdetailscontainer',
						layout : {
							type : 'fit'
						},
						items : [{
									xtype : 'newsdetails'
								}]
					}]
		});
