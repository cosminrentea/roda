/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.Pages', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cmspages',
    		itemId : 'cmspages',
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
									itemid : 'pagestoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											}, {
												xtype : 'button',
												itemId : 'tree-view',
												text : 'tree'
											}, {
												xtype : 'button',
												itemId : 'icon-view',
												text : 'icon'
											}]
								}],
						items : [{
									xtype : 'pagesitemsview'
								}]
					}, {
						region : 'center',
						collapsible : false,
						width : '50%',
						xtype : 'panel',
						itemid : 'pgdetailscontainer',
						title : 'Page details',
						layout : {
							type : 'fit'
							// padding:'5',
							// align:'center',
							// align:'stretch'
						},
						items : [{
									xtype : 'pagedetails'
								}]
					}]
		});
