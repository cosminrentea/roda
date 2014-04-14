/**
 * 
 */
Ext.define('RODAdmin.view.audit.details.UserProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.userproperties',
    itemId : 'userproperties',
    title : 'User Revisions',
    width : "100%",
    height : "100%",
    layout : 'border',
    collapsible : false,
    items : [
            {
                region : 'west',
                collapsible : true,
                width : '30%',
                split : true,
//                itemId : 'revisions',
                flex : 1,
				xtype : 'grid',
				loadMask: true, 
				title : 'revisions',
				itemId : 'revisiondata',
				store : '',
				features : [Ext.create('Ext.ux.grid.FiltersFeature', {
							local : true
						})],
				columns : [{
							itemId : 'revisionid',
							text : 'Id',
							flex : 1,
							sortable : true,
							dataIndex : 'revision',
							filterable : true
						}, 
						{
							itemId : 'timestamp',
							text : 'Timestamp',
							flex : 1,
							sortable : true,
							dataIndex : 'timestamp',
							filterable : true
						}, 
						{
							itemId : 'nrrows',
							text : 'Rows',
							flex : 1,
							sortable : true,
							dataIndex : 'nrrows',
							filterable : true
						} 
						],
            }, 
            {
                region : 'center',
                collapsible : false,
                loadMask: true, 
                resizable : true,
                width : '70%',
//                split : true,
                xtype: 'panel',
//                title : 'smth',
                flex : 3,
                layout : {
    				type : 'vbox',
//    				deferredRender : true,
    				align : 'stretch'
    			},
                items: [
                        {
                            xtype : 'grid',
                            itemId: 'userobjects',
                            title: 'Objects',
                            collapsible: true,
                        	flex: 1,
                            features : [Ext.create('Ext.ux.grid.FiltersFeature', {
            					local : true
            				})],
            		columns : [
            				{
            					itemId : 'objname',
            					text : 'Objname',
            					flex : 1,
            					sortable : true,
            					dataIndex : 'objname',
            					filterable : true
            				},
            				{
            					itemId : 'nrrows',
            					text : 'Nrrows',
            					flex : 1,
            					sortable : true,
            					dataIndex : 'nrrows',
            					filterable : true
            				}],	
                        },
                        {
                            xtype : 'grid',
                            itemId: 'revisionrows',
                            title: 'Rows',
                            collapsible: true,
                        	flex: 1,
                            features : [Ext.create('Ext.ux.grid.FiltersFeature', {
            					local : true
            				})],
            		columns : [{
            					itemId : 'id',
            					text : 'Id',
            					flex : 1,
            					sortable : true,
            					dataIndex : 'id',
            					filterable : true
            				}, 
            				{
            					itemId : 'modtype',
            					text : 'Modtype',
            					flex : 1,
            					sortable : true,
            					dataIndex : 'modtype',
            					filterable : true
            				},
            				{
            					itemId : 'nrfields',
            					text : 'Nrfields',
            					flex : 1,
            					sortable : true,
            					dataIndex : 'nrfields',
            					filterable : true
            				}],	
                        },
                        {
                        	xtype: 'panel',
                        	itemId: 'revisionfields',
                        	autoScroll: true,
                            collapsible: true,
                        	title: 'Fields',
                        	flex: 1,
                   //     	html: 'woppie',
                            tpl : [ '<tpl for=".">',
                                    '<div class="auditfield">Field:{data.auditfield}</div>',	
                                    '<div class="auditvalue">Value: {data.auditvalue}</div>',
                                    '</tpl>'
                           ]
                        	
                        }
                        ]
            }
    ]
});
