/**
 * @class RODAdmin.view.studies.Studies
 * Chestii layouts
 * @alias widget.cmslayouts
 */
Ext.define('RODAdmin.view.studies.Studies', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.studiesmain',

    itemId : 'studiesmain',
    id : 'studiesmain',
    layout : {
        type : 'border',
        padding : 5
    },
    defaults : {
	    split : true
    },

    items : [
            {
                region : 'west',
                collapsible : true,
                width : '30%',
                split : true,
                layout : 'fit',
                dockedItems : [
	                {
	                    dock : 'top',
	                    xtype : 'toolbar',
	                    itemid : 'layouttoolbar',
	                    items : [
	                            {
	                            	xtype : 'tbfill'
	                            }, {
	                            	xtype : 'tbseparator'
	                            }, {
	                            	xtype : 'button',
	                                itemId : 'tree-view',
	                                iconCls : 'file-tree-view',
	                                text : 'tree'
	                            }, {
	                            	xtype : 'button',
	                                itemId : 'icon-view',
	                                iconCls : 'file-icon-view',
	                                text : 'icon'
	                            }
	                    ]
	                }
                ],
                items : [
	                {
						/**
						 * @xtypes layoutitemsview RODAdmin.view.cms.layout.LayoutItemsview
						 */
	                	 xtype : 'studyitemsview'
	                }
                ]
            }, {
                region : 'center',
                collapsible : false,
                width : '70%',
                xtype : 'panel',
                itemid : 'stdetailscontainer',
                id : 'stdetailscontainer',
//                title : 'dontinkso',
                layout : {
	                type : 'fit'
                // padding:'5',
                // align:'center',
                // align:'stretch'
                },
                items : [
	                {
						/**
						 * @xtypes layoutdetails RODAdmin.view.cms.layout.LayoutDetails
						 */
	                	xtype : 'studydetails'
	                }
                ]
            }
    ]
});
