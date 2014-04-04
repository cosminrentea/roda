/**
 * 
 */
Ext.define('RODAdmin.view.audit.details.RevisionProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.revisionproperties',
    itemId : 'revisionproperties',
    title : 'Revision Properties',
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
                itemId : 'revisiondata',
                flex : 1,
				xtype : 'grid',
				id : 'revisiondata',
				itemId : 'revisiondata',
//				store : 'audit.RevisionInfo',
				features : [Ext.create('Ext.ux.grid.FiltersFeature', {
							local : true
						})],
				columns : [{
							itemId : 'objname',
							text : 'Name',
							flex : 1,
							sortable : true,
							dataIndex : 'objname',
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
                            id : 'revisionrows',
                            itemId: 'revisionrows',
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
                        	id: 'revisionfields',
                        	autoScroll: true,
                        //	title: 'oho',
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
