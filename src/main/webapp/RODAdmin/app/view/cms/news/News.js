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
									itemid : 'newstoolbar',
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
						itemid : 'newsdetailscontainer',
						id : 'newsdetailscontainer',
//						title : 'dontinkso',
						layout : {
							type : 'fit'
							// padding:'5',
							// align:'center',
							// align:'stretch'
						},
						items : [{
									xtype : 'newsdetails'
								}]
					}]
		});
