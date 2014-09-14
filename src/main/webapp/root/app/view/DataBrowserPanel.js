Ext.define('databrowser.view.DataBrowserPanel', {
    extend: 'Ext.panel.Panel',
    frame: true,
    alias: 'widget.dbmainp',
    layout: {
        type: 'border'
    },
    closable: false,
    header:false,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
        	items: [
                {
                    xtype: 'browser',
                    region:'west',
                    //dock: 'left',
                    id: 'NavigatorTabPanel',
                    width: '30%',
                    layout: 'fit',
                    animCollapse: false,
                    collapseDirection: 'left',
                    collapsible: true,
                    resizable:true,
                    activeTab: 0,
                },
                {
                	
                	 region : 'center',
                     collapsible : false,
                     itemId : 'dbcardcont',
                     width : '70%',
                     items : [
                              {
                       	xtype: 'dbcard',
                              }
                     ],
                    layout: 'fit',
                }
                ]
        });

        me.callParent(arguments);
    },
});