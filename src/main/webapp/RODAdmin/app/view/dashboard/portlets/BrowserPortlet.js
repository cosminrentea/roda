Ext.define('RODAdmin.view.dashboard.portlets.BrowserPortlet', {

    extend: 'Ext.panel.Panel',
    alias: 'widget.browserportlet',

    requires: [
        'Ext.data.JsonStore',
        'Ext.chart.theme.Base',
        'Ext.chart.series.Series',
        'Ext.chart.series.Line',
        'Ext.chart.axis.Numeric'
    ],

    initComponent: function(){

        Ext.apply(this, {
            layout: 'fit',
            height: 300,
            items: {
                xtype: 'chart',
                animate: false,
                shadow: false,
                store: Ext.create('RODAdmin.store.dashboard.Browser'),
                shadow: true,
                legend: {
                    position: 'right'
                },
                series: [{
                    type: 'pie',
                    field: 'value',
                    showInLegend: true,
                    label: {
                    	field: 'name',
                    },
                }]
            }
        });

        this.callParent(arguments);
    }
});
