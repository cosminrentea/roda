/**
 * @class RODAdmin.view.studies.Studies
 * Chestii layouts
 * @alias widget.cmslayouts
 */
Ext.define('RODAdmin.view.studies.Studies', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.studiesmain',
    itemId : 'studiesmain',
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
	                    itemid : 'studytoolbar',
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
	                            }, {
	                            	xtype : 'button',
	                                itemId : 'add-study',
	                                iconCls : 'file-icon-view',
	                                text : 'Add Study'
	                            }
	                    ]
	                }
                ],
                items : [
	                {
						/**
						 * @xtypes studyitemsview RODAdmin.view.studies.StudyItemsview
						 */
	                	 xtype : 'studyitemsview'
	                }
                ]
            }, {
                region : 'center',
                collapsible : false,
                width : '70%',
                xtype : 'panel',
                itemId : 'stdetailscontainer',
                layout : {
	                type : 'card'
                // padding:'5',
                // align:'center',
                // align:'stretch'
                },
                items : [
                         {
     						/**
     						 * @xtypes studydetails RODAdmin.view.studies.StudyDetails
     						 */
     	                	xtype : 'panel',
     	                	html:'Please select an item from the left panel'
     	                },
                    {
						/**
						 * @xtypes studydetails RODAdmin.view.studies.StudyDetails
						 */
	                	xtype : 'catalogdetails',
	                	itemId: 'catalogdetails'	
	                },
	                {
						/**
						 * @xtypes studydetails RODAdmin.view.studies.StudyDetails
						 */
	                	xtype : 'studydetails',
	                	itemid : 'studydetails'	
	                //	xtype : 'panel',
	                //	title: 'study details'
	                },
                ]
            }
    ]
});
