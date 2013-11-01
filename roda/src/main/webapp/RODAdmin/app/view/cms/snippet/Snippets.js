Ext.define('RODAdmin.view.cms.snippet.Snippets', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cmssnippets',
    		itemId : 'cmssnippets',
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
						width : '30%',
						split : true,
						layout : 'fit',
						dockedItems : [{
									dock : 'top',
									xtype : 'toolbar',
									itemid : 'snippettoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											}, {
												xtype : 'button',
												itemId : 'tree-view',
												iconCls : 'file-tree-view',
												text : 'tree'
											}, {
												xtype : 'button',
												itemId : 'icon-view',
												iconCls : 'file-icon-view',
												text : 'icon'
											}]
								}],
						items : [{
									xtype : 'snippetitemsview'
								}]
					}, {
						region : 'center',
						collapsible : false,
						width : '70%',
						xtype : 'panel',
						itemid : 'sndetailscontainer',
						id : 'sndetailscontainer',
						title : 'dontinkso',
						layout : {
							type : 'fit'
							// padding:'5',
							// align:'center',
							// align:'stretch'
						},
						items : [{
									xtype : 'snippetdetails'
								}]
					}]
		});
