/**
 */
Ext.define('RODAdmin.view.user.UserItemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.useritemsview',
			itemId : 'useritemsview',
			activeItem : 0,
			width : '100%',
			layout : {
				type : 'card',
//				deferredRender : true,
				align : 'stretch'
			},
			items : [{
						xtype : 'grid',
						itemId : 'usersgrid',
						itemSelector : 'div.thumb-wrap',
						store : 'user.User',
						features : [Ext.create('Ext.ux.grid.FiltersFeature', {
									local : true
								})],
						columns : [{
									itemId : 'id',
									text : 'Username',
									flex : 1,
									sortable : true,
									dataIndex : 'username',
									filterable : true
								}, {
									text : 'Firstname',
									flex : 1,
									dataIndex : 'firstname',
									sortable : true,
								},{
									text : 'Lastname',
									flex : 1,
									dataIndex : 'lastname',
									sortable : true,
								},{
									text : 'Email',
									flex : 1,
									dataIndex : 'email',
									sortable : true,
								}, {
									xtype: "checkcolumn",
								    columnHeaderCheckbox: true,
								    store: 'user.User',
								    sortable: false,
								    hideable: false,
								    menuDisabled: true,
								    dataIndex: "enabled",
								    listeners: {
								        checkchange: function(column, rowIndex, checked){
								             //code for whatever on checkchange here
								        }
								    }
								}
								
								
								
								],
						dockedItems : [{
									xtype : 'toolbar',
									itemId : 'usertoolbar',
							//		id : 'usertoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'button',
												itemId : 'showfilterdata',
												text : 'All Filter Data',
												tooltip : 'Get Filter Data for Grid'
											}, {
												text : 'Clear Filter Data',
												xtype : 'button',
												itemId : 'clearfilterdata'
											},{
												text : 'Reload',
												xtype : 'button',
												itemId : 'refreshusers'
											},
											
											]
								}]
					}, {
						store : 'user.Group',
						itemId : 'usergroups',
						xtype : 'grid',
						useArrows : true,
						loadMask:true,
						rootVisible : false,
						multiSelect : false,
						singleExpand : false,
						allowDeselect : true,
						autoheight : true,
						dockedItems : [{
									xtype : 'toolbar',
									itemId : 'ugroupstoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											},
											{
												text : 'Reload Grid',
												xtype : 'button',
												itemId : 'refreshgroups'
											}]
								}],
						columns : [{
									itemId : 'ft',
									text : 'Group',
									flex : 2,
									sortable : false,
									dataIndex : 'name',
									filter : {
										type : 'string'
									}
								}, {
									text : 'description',
									flex : 1,
									dataIndex : 'description',
									sortable : false,
									filterable : true
								}, {
									xtype: "checkcolumn",
								    columnHeaderCheckbox: true,
								    store: 'user.Group',
								    sortable: false,
								    hideable: false,
								    menuDisabled: true,
								    text: 'Status',
								    dataIndex: "enabled",
								    listeners: {
								        checkchange: function(column, rowIndex, checked){
								             //code for whatever on checkchange here
								        }
								    }
								}

								
								
								]
					}
					]
		});
