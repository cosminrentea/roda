/**
 * @class RODAdmin.view.studies.Studies
 * Chestii layouts
 * @alias widget.cmslayouts
 */
Ext.define('RODAdmin.view.studies.StudiesTemp', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.studiestemp',
    itemId : 'studiestemp',
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
                width : '50%',
                split : true,
                layout : 'fit',
                items : [
	                {
	                	 xtype : 'studyitemstempview'
	                }
                ]
            }, {
                region : 'center',
                collapsible : false,
                width : '50%',
                xtype : 'panel',
                itemId : 'stdetailstempcontainer',
                layout : {
	                type : 'card'
                },
                items : [
                         	{
                         		xtype : 'panel',
                         		html:'Please select an item from the left panel'
                         	},
                         	{
                        // 		xtype : 'studytempdetails',
                         		xtype: 'panel',
                         		itemId : 'studytempdetails'	
                         	},
     	                ]
            	}
            ]	
});
