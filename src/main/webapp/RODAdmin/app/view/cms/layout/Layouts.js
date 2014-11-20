/**
 * @class RODAdmin.view.cms.layout.Layouts
 * Chestii layouts
 * @alias widget.cmslayouts
 */
Ext.define('RODAdmin.view.cms.layout.Layouts', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.cmslayouts',

    itemId : 'cmslayout',
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
	                    itemId : 'layouttoolbar',
	                    items : [
	                            {
	                            	xtype : 'tbfill'
	                            }, {
	                            	xtype : 'tbseparator'
	                            }, {
	                            	xtype : 'button',
	                                itemId : 'tree-view',
	                                iconCls : 'file-tree-view',
	                                text : translations.treeview,
	                            }, {
	                            	xtype : 'button',
	                                itemId : 'icon-view',
	                                iconCls : 'file-icon-view',
	                                text : translations.iconview
	                            }
	                    ]
	                }
                ],
                items : [
	                {
						/**
						 * @xtypes layoutitemsview RODAdmin.view.cms.layout.LayoutItemsview
						 */
	                	 xtype : 'layoutitemsview'
	                }
                ]
            }, {
                region : 'center',
                collapsible : false,
                width : '70%',
                xtype : 'panel',
                itemId : 'lydetailscontainer',
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
	                	xtype : 'layoutdetails'
	                }
                ]
            }
    ]
});
