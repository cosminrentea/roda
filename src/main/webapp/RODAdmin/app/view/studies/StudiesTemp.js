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
                dockedItems : [
           	                {
           	                    dock : 'top',
           	                    xtype : 'toolbar',
           	                    itemId : 'studytoolbar',
           	                    items : [
           	                            {
           	                            	xtype : 'tbfill'
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
	                	 xtype : 'studyitemstempview'
	                }
                ]
            }, {
                region : 'center',
                collapsible : false,
                width : '50%',
                xtype : 'panel',
                itemId : 'stdetailstempcontainer',
                dockedItems : [
              	                {
              	                    dock : 'top',
              	                    xtype : 'toolbar',
              	                    itemId : 'studyedittoolbar',
              	                    items : [
              	                            {
              	                            	xtype : 'tbfill'
              	                            }, {
              	                            	xtype : 'button',
              	                                itemId : 'edit-tstudy',
              	                                iconCls : 'file-icon-view',
              	                                text : 'Edit temporary Study'
              	                            },
              	                            	{
              	                            	xtype : 'button',
              	                                itemId : 'convert-tstudy',
              	                                iconCls : 'file-icon-view',
              	                                text : 'Convert temporary Study'
              	                            	}
              	                    ]
              	                }
                              ],

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
                         		xtype: 'tabpanel',
                         		itemId : 'studytempdetails'	,
                         		items : [
                         		        {xtype:'stempproposal', itemId:'sproposal'},
                         		        {xtype:'stempfunding', itemId:'sfunding'},
                         		        {xtype:'stempconcepts', itemId:'sconcepts'},
                         		        {xtype:'stempquestions', itemId:'squestions'},
                         		        {xtype:'stempdatacollection', itemId:'sdatacollection'},
                         		        {xtype:'stempdataprod', itemId:'sdataprod'},	
                         		        {xtype:'stprocessinfo', itemId:'sprocessinfo'}
                         		         ]
                         	},
     	                ]
            	}
            ]	
});
