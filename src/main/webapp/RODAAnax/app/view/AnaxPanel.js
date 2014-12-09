Ext.define('anax.view.AnaxPanel', {
    extend: 'Ext.panel.Panel',
    frame: true,
    alias: 'widget.mainpanel',
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
                    xtype: 'anaxcontrol',
//                	xtype: 'panel',
                    region:'west',
                    //dock: 'left',
            //        id: 'controller',
                    width: '30%',
                    layout: 'fit',
                    animCollapse: false,
                    collapseDirection: 'left',
                    collapsible: true,
                    resizable:true,
                    title: 'select grouping',
                    tools:[{
                        id: 'search',
                    }],
                    activeTab: 0,
                },
                {
                	
                	 region : 'center',
                     collapsible : false,
                     itemId : 'anaxmapcont',
                     width : '70%',
                     items : [
                              {
                       	xtype: 'anaxmap',
                              }
                     ],
                    layout: 'fit',
                }
                ]
        });

        me.callParent(arguments);
    },
});