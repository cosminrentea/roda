/**
 * 
 */
Ext.define('RODAdmin.view.audit.Audit', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.auditmain',
			itemId : 'auditmain',
			id : 'auditmain',
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
						width : '30%',
						split : true,
						layout : 'fit',
						dockedItems : [{
									dock : 'top',
									xtype : 'toolbar',
									itemId : 'audittoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											}, {
												xtype : 'button',
												itemId : 'bauditrevisions',
												iconCls : 'auditrevisions',
												pressed : true,
												enableToggle: true,
												text : 'revisions',
											}, {
												xtype : 'button',
												itemId : 'bauditobjects',
												iconCls : 'auditobjects',
												text : 'objects',
													enableToggle: true,
											}, {
												xtype : 'button',
												itemId : 'bauditusers',
												iconCls : 'auditusers',
												enableToggle: true,
												text : 'users',
											}, {
												xtype : 'button',
												itemId : 'bauditdates',
												iconCls : 'auditdates',
												enableToggle: true,											
												text : 'days',
											}
											
											
											]
								}],
						items : [{
									xtype : 'audititemsview'

								}]
					}, {
						region : 'east',
						collapsible : true,
						width : '70%',
						xtype : 'panel',
						itemId : 'adetailscontainer',
						id : 'adetailscontainer',
						title : '',
						layout : {
							type : 'fit'
							,
							// padding:'5',
							// align:'center',
							// align:'stretch'
						},
						items : [{
									xtype : 'auditdetails'
//								xtype : 'revisionproperties'
								}]
					}]

		});