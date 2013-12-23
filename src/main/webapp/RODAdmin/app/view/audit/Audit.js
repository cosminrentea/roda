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
						width : '50%',
						split : true,
						layout : 'fit',
						dockedItems : [{
									dock : 'top',
									xtype : 'toolbar',
									itemid : 'audittoolbar',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'tbseparator'
											}, {
												xtype : 'button',
												itemId : 'bauditrevisions',
												iconCls : 'auditrevisions',
												text : 'revisions'
											}, {
												xtype : 'button',
												itemId : 'bauditobjects',
												iconCls : 'auditobjects',
												text : 'objects'
											}, {
												xtype : 'button',
												itemId : 'bauditusers',
												iconCls : 'auditusers',
												text : 'users'
											}, {
												xtype : 'button',
												itemId : 'bauditdates',
												iconCls : 'auditdates',
												text : 'days'
											}
											
											
											]
								}],
						items : [{
									xtype : 'audititemsview'

								}]
					}, {
						region : 'east',
						collapsible : true,
						width : '50%',
						xtype : 'panel',
						itemid : 'adetailscontainer',
						id : 'adetailscontainer',
						title : 'dontinkso',
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