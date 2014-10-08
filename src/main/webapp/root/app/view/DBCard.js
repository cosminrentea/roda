
Ext.define('databrowser.view.DBCard', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dbcard',
    id: 'dbcard',
    itemId: 'dbcard',
    activeItem: 0,
    width: '100%',
    layout: {
        type: 'card',
        deferredRender: true,
    },
	
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {    
    items: [
	{
		id: 'initial',
		html: translations.selectelement,
	},     
    {
        id: 'catalogview',
        xtype: 'catalogview',
    },
    {
        id: 'yearview',
        xtype: 'yearview',
    },    
    {
        id: 'detailspanel',
        xtype: 'detailspanel',    
    },
    {
        id: 'studyview',
        xtype: 'studyview',
    },
    {
        id: 'seriesview',
        xtype: 'seriesview',
    }, 
    {
        id: 'studyseriesview',
        xtype: 'studyseriesview',    	
    }
    
    ],
    
    
    
});
        me.callParent(arguments);
    },
    
});
