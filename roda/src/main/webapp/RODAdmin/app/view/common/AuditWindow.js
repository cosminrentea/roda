Ext.define('RODAdmin.view.common.AuditWindow', {
			extend : 'RODAdmin.view.common.InformationWindow',			
			alias : 'widget.auditwindow',

			height : '70%',
			width : '50%',

			layout : {
				type : 'border'
			},

			initComponent : function() {
				var me = this;
				Ext.applyIf(me, {
							title : 'Audit',
							items : [{
										region : 'east',
										collapsible : true,
										width : '50%',
										split : true,
										layout : 'fit',
										items : [{
											xtype:'dataview',										
											itemId : 'auditdetails',
											tpl: [
													'<tpl for=".">',
													'<div style="border: 1px solid black;margin:5px;padding:5px;">',
													'<strong>{auditfield}</strong><br>',
													'{auditvalue}<br>',
													'</div>',
													'</tpl>'
												],											
											itemSelector: 'cdc'
										}]
									}, {
										region : 'center',
										collapsible : false,
										width : '50%',
										split : true,
										layout : 'fit',
										items : [{
											xtype : 'grid',
											itemId : 'auditgrid',
											store : 'common.Audit',
											features : [Ext.create(
															'Ext.ux.grid.FiltersFeature',
															{
																local : true
															})],
											columns : [
												{
														itemId : 'audittime',
														text : 'Timestamp',
														flex : 2,
														sortable : true,
														dataIndex : 'timestamp',
										//				filterable : true
													}, 
														{
														text : 'version',
														flex : 1,
														dataIndex : 'version',
														sortable : true,
									//					filterable : true
													}, {
														text : 'Username',
														flex : 1,
														dataIndex : 'username',
														sortable : true,
										//				filterable : true
													}, {
														text : 'Modiffication type',
														flex : 1,
														dataIndex : 'modtype',
														sortable : true,
											//			filterable : true
													}, {
														text : 'Fields',
														flex : 1,
														dataIndex : 'nrfields',
														sortable : true,
												//		filterable : true
													}
													]
										}]
									}],
									dockedItems : [{
										xtype : 'canceltb'
									}]									
						});
				me.callParent(arguments);
			}
		});