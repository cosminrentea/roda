/**
 * 
 */
Ext.define('RODAdmin.view.user.UserDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.userdetails',
			itemId : 'userdetails',
			activeItem : 0,
			width : '100%',
            layout: {
                type:'vbox',
                padding:'5',
 //               align:'center',
                align:'stretch'
                }, 
			items : [
			         {
			        	 xtype:'panel',
//			        	 xtype:'userproperties', //change with userview
			        	 id:'userinfo',
			        	 itemId: 'userinfo',
			        	 height:220,
			        	 title: 'Details',
						collapsible: true,
//			        	 html: 'Select an user or group'
			     			tpl : ['<tpl if="data.enabled == true">',
			     			       '<div class="useractive">',
			     			       '<table width="100%" border=0><tr><td width="128px">',
			     			       '<div class="userimage"></div>',
			     			       '</td><td>',
			     			       '<div class="username"><strong>Username:</strong> {data.username}</div>',
			     			       '<div class="useremail"><strong>Email:</strong> {data.email}</div>',
			     			       '</td></tr></table>',
			     			       '</div>',
			     			       '</tpl>',
			     			      '<tpl if="data.enabled == false">',
			     			      '<div class="userinactive">',
			     			       '<div class="userimage"></div>',
			     			      '<div class="username">Username: {data.username}</div>',
			     			       '<div class="useremail">Email: {data.email}</div>',
			     			       '</div>',
			     			       '</tpl>'
			     			     

						],
						dockedItems : [{
							xtype : 'toolbar',
							itemId : 'userptoolbar',
							id : 'userptoolbar',
							dock : 'bottom',
							items : [{
										xtype : 'tbfill'
									}, {
										xtype : 'button',
										itemId : 'useradd',
										text : 'Add User',
										tooltip : 'Add user'
									}, {
										xtype : 'button',
										itemId : 'useredit',
										text : 'Edit User',
										tooltip : 'Edit user email/name'
									}, {
										text : 'Inactivate',
										xtype : 'button',
										itemId : 'clearfilterdata',
										tooltip : 'Makes the user inactive',
									}, {
										text : 'Delete',
										xtype : 'button',
										itemId : 'dropuser',
										tooltip : 'Deletes the user'
									}, {
										text : 'Groups',
										xtype : 'button',
										itemId : 'usergroups',
										tooltip : 'Changes the user groups'
									}
									
									]
						}]
			         },
			         {
			        	 xtype:'panel',
//			        	 xtype:'userprofile', //change with userview
			        	 id: 'userprofile',
						collapsible: true,
						flex:2,
			        	 title: 'Profil',
			        		tpl: [
			        		      '<table class="uprofile">',
			        		      '<tr><td class="uprofilelabel">Nume:</td><td>{data.firstname} {data.lastname}</td></tr>',
			        		      '<tr><td class="uprofilelabel">Adresa:</td><td>{data.address1}<br>{data.address2}</td></tr>',
			        		      '<tr><td class="uprofilelabel">Oras:</td><td>{data.city}</td></tr>',
			        		      '<tr><td class="uprofilelabel">Tara:</td><td>{data.country}</td></tr>',
			        		      '<tr><td class="uprofilelabel">Data nasterii:</td><td>{data.birthdate}</td></tr></table>',
		     			       	],
								dockedItems : [{
									xtype : 'toolbar',
									itemId : 'userprofiletoolbar',
									id : 'userprofiletoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											}, {
												xtype : 'button',
												itemId : 'editprofile',
												text : 'Edit profile',
												tooltip : 'Edit user profile'
											}											]
								}]
			         },
			         {

			        	 //store : 'user.Group',
							itemId : 'usermessages',
							xtype : 'gridpanel',
							useArrows : true,
							layout: 'fit',
							loadMask:true,
							rootVisible : false,
							multiSelect : false,
							singleExpand : false,
							flex:1,
							title: 'Messages',
							collapsible: true,
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
													itemId : 'refreshgrid'
												},
												{
													text : 'All messages',
													xtype : 'button',
													itemId : 'all messages'
												}
												]
									}],
							columns : [{
										itemId : 'ft',
										text : 'Direction',
										flex : 2,
										sortable : false,
										dataIndex : 'direction',
										filter : {
											type : 'string'
										}
									}, {
										text : 'user',
										flex : 1,
										dataIndex : 'user',
										sortable : false,
										filterable : true
									}, {
										text : 'subject',
										flex : 1,
										dataIndex : 'subject',
										sortable : false,
										filterable : true
									}, {
										text : 'Date',
										flex : 1,
										dataIndex : 'date',
										sortable : false,
										filterable : true
									}
									]
			         }],
});