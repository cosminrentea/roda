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
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
        	title: 'cool',
        	items : [
            {
                region : 'west',
                collapsible : true,
                width : '30%',
                split : true,
                layout : 'fit',
                items : [
	                {
						/**
						 * @xtypes studyitemsview RODAdmin.view.studies.StudyItemsview
						 */
	                //	 xtype : 'studyitemsview'
	                }
                ]
            }, {
                region : 'center',
                collapsible : false,
                width : '70%',
                xtype : 'panel',
                },
             
                ]
        });
        me.callParent(arguments);
     },
});
