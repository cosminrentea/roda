/**
 * 
 */
Ext.define('RODAdmin.view.user.Admin', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.admins',
			itemId : 'admins',
			id : 'admins',
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
									itemId : 'userstoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											}, {
												xtype : 'button',
												itemId : 'Admins',
												iconCls : 'admins',
												text : 'Admins'
											}, {
												xtype : 'button',
												itemId : 'permissions',
												iconCls : 'permissions',
												text : 'Permissions'
											}]
								}],
						items : [{
									xtype : 'adminitemsview'

								}]
					}, {
						region : 'east',
						collapsible : true,
						width : '50%',
						xtype : 'panel',
						itemId : 'adetailscontainer',
						id : 'adetailscontainer',
						title : 'wtf',
						layout : {
							type : 'fit'
							,
							// padding:'5',
							// align:'center',
							// align:'stretch'
						},
						items : [{
//									xtype : 'filedetails'
								}]
					}]

		});