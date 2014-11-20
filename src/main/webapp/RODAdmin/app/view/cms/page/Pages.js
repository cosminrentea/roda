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
									itemId : 'pagestoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											}, {
												xtype : 'button',
												itemId : 'tree-view',
												text : translations.treeview,
											}, {
												xtype : 'button',
												itemId : 'icon-view',
												text : translations.iconview
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
						itemId : 'pgdetailscontainer',
						title : translations.pg_details,
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
