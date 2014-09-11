/**
 * @class RODAdmin.view.studies.Studies
 * Chestii layouts
 * @alias widget.cmslayouts
 */
Ext.define('RODAdmin.view.studies.StudyEdit', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.studyedit',
    layout : {
        type : 'border',
        padding : 5
    },
    defaults : {
	    split : true
    },
	config : {
		editId : 0
	},
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
        	title: 'cool',
        	items : [
            {
                region : 'east',
                collapsible : true,
                width : 200,
                layout : 'anchor',
                items : [
	                	 {
					        	xtype:'button',
					        	text: 'Save',
					        	itemId: 'stsavebutton',
								enableToggle : false,				        	
					        	anchor:'100%'
					        },{
					        	xtype:'button',
					        	text: 'Reset',
					        	itemId: 'stresetbutton',
								enableToggle : false,
					        	anchor:'100%'				        	
					        },
					        {
					        	xtype:'button',
					        	text: 'Cancel',
					        	itemId: 'stcancelbutton',
								enableToggle : false,
					        	anchor:'100%'				        	
					        }
	                	
                ]
            }, {
                region : 'center',
                collapsible : false,
                width : '70%',
                xtype : 'tabpanel',
                layout : 'fit',
                items : [
            	         {xtype:'sesproposal', itemId:'sesproposal'},
            	         {xtype:'sesfunding', itemId:'sesfunding'},
            	         {xtype:'sesconcepts', itemId:'sesconcepts'},
            	         {xtype:'sesquestions', itemId:'sesquestions'},
            	         {xtype:'sesdatacollection', itemId:'sesdatacollection'},
            	         {xtype:'sesdataprod', itemId:'sesdataprod'},				         
            	         ]                
                },
                ]
        });
        me.callParent(arguments);
     },
});
