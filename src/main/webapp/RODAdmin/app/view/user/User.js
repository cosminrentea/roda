/**
 * 
 */
Ext.define('RODAdmin.view.user.User', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.users',
			itemId : 'users',
			id : 'users',
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
												itemId : 'users',
												iconCls : 'users',
												text : 'Users',
												toggleGroup : 'usersgroups'
													
											}, {
												xtype : 'button',
												itemId : 'groups',
												iconCls : 'groups',
												text : 'Groups',
												toggleGroup : 'usersgroups'
											}]
								}],
						items : [{
									xtype : 'useritemsview'

								}]
					}, {
						region : 'east',
						collapsible : true,
						width : '50%',
						xtype : 'panel',
						itemId : 'udetailscontainer',
						id : 'udetailscontainer',
					//	title : 'wtf',
						layout : {
							type : 'card',
							
						},
						items : [{
									xtype : 'userdetails'
								},
								{
									xtype : 'groupdetails'
//									xtype: 'panel',
//									id: 'groupdetails',
//									html: 'group',
//								    tpl : ['<tpl if="data.filetype == \'folder\'">',
//								           
//								           ]
									
								}
						
						]
					}]

		});