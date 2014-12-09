/**
 * 
 */
Ext.define('RODAdmin.view.user.GroupDetails', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.groupdetails',
			itemId : 'groupdetails',
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
			        	 id:'groupinfo',
			        	 height:220,
			        	 title: 'Details',
						collapsible: true,
			     			tpl : [
				        		      '<table class="groupdetails">',
				        		      '<tr><td class="gdetailslabel">Nume:</td><td>{data.name} {data.lastname}</td></tr>',
				        		      '<tr><td class="gdetailslabel">Descriere:</td><td>{data.description}<br>{data.address2}</td></tr>',
				        		      '<tr><td class="gdetailslabel">Nr. Utilizatori:</td><td>{data.nrusers}</td></tr>',
				        		      '</table>',
			     			       ],
						dockedItems : [{
							xtype : 'toolbar',
							itemId : 'grouptoolbar',
							id : 'grouptoolbar',
							dock : 'bottom',
							items : [{
										xtype : 'tbfill'
									}, {
										xtype : 'button',
										itemId : 'groupedit',
										text : 'Edit Group',
										tooltip : 'Edit group'
									}, {
										text : 'Inactivate',
										xtype : 'button',
										itemId : 'clearfilterdata',
										tooltip : 'Makes the user inactive',
									}, {
										text : 'Delete',
										xtype : 'button',
										itemId : 'dropuser',
										tooltip : 'Deletes the group'
									}
									
									]
						}]
			         },
			         {

			        	    store : '',
							itemId : 'groupusers',
							xtype : 'grid',
							useArrows : true,
							layout: 'fit',
							loadMask:true,
							rootVisible : false,
							multiSelect : false,
							singleExpand : false,
							flex:1,
							title: 'Users in group',
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
												
												]
									}],
							columns : [{
										itemId : 'ft',
										text : 'Id',
										flex : 1,
										sortable : false,
										dataIndex : 'id',
									}, {
										text : 'user',
										flex : 2,
										dataIndex : 'username',
										sortable : false,
										filterable : true
									}, {
										text : 'email',
										flex : 2,
										dataIndex : 'email',
										sortable : false,
										filterable : true
									}, {
										xtype: "checkcolumn",
									    columnHeaderCheckbox: true,
									    store: 'user.Group',
									    sortable: false,
									    hideable: false,
									    menuDisabled: true,
									    dataIndex: "enabled",
									    text: 'Status',
									    listeners: {
									        checkchange: function(column, rowIndex, checked){
									             //code for whatever on checkchange here
									        }
									    }
									}
									]
			         }],
});