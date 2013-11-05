
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
    items: [
	{
		id: 'initial',
		html: 'Selectati un element din arborele din dreapta',
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
    },{
        id: 'studyview',
        xtype: 'studyview',
    },{
        id: 'seriesview',
        xtype: 'seriesview',
    }, {
        id: 'studyseriesview',
        xtype: 'studyseriesview',    	
    }
    
    ],
});
    
