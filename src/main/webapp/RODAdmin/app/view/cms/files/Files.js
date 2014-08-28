/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.Files', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.cmsfiles',
			itemId : 'cmsfiles',
			layout : {
				type : 'border',
				padding : 5
			},
			defaults : {
				split : true
			},

			items : [{
						region : 'center',
						collapsible : false,
						width : '50%',
						split : true,
						layout : 'fit',
						dockedItems : [{
									dock : 'top',
									xtype : 'toolbar',
									itemid : 'filestoolbar',
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
							/**
							 * @xtypes itemsview RODAdmin.view.cms.files.Itemsview
							 */
							xtype : 'itemsview'

								}]
					}, {
						region : 'east',
						collapsible : true,
						width : '50%',
						xtype : 'panel',
						itemid : 'fdetailscontainer',
//						title : 'dontinkso',
						layout : {
							type : 'fit'
							,
							// padding:'5',
							// align:'center',
							// align:'stretch'
						},
						items : [{
							/**
							 * @xtypes filedetails RODAdmin.view.cms.files.FileDetails
							 */
							xtype : 'filedetails'
								}]
					}]

		});