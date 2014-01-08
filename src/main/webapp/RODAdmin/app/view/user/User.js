/**
 * 
 */
Ext.define('RODAdmin.view.user.User', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.usersmain',
			itemId : 'usersmain',
			id : 'usersmain',
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
									itemid : 'userstoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											}, {
												xtype : 'button',
												itemId : 'users',
												iconCls : 'users',
												text : 'Users'
											}, {
												xtype : 'button',
												itemId : 'groups',
												iconCls : 'groups',
												text : 'Groups'
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
						itemid : 'udetailscontainer',
						id : 'udetailscontainer',
						title : 'wtf',
						layout : {
							type : 'fit'
							,
							// padding:'5',
							// align:'center',
							// align:'stretch'
						},
						items : [{
									xtype : 'userdetails'
								}]
					}]

		});