Ext.define('RODAdmin.view.dashboard.portlets.TraficPortlet', {

    extend: 'Ext.panel.Panel',
    alias: 'widget.traficportlet',

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
                store: Ext.create('RODAdmin.store.dashboard.Trafic'),
                legend: {
                    position: 'bottom'
                },
                axes: [
                {
                    type: 'Numeric',
                    position: 'left',
                    fields: ['visitors', 'visits', 'views'],
                    title: 'Traffic',
                    label: {
                        font: '11px Arial'
                    }
                },
                {
                    type: 'Category',
                    position: 'bottom',
                    fields: ['day'],
                    title: 'Day',
//                    dateFormat: 'd-M-Y',
                    label: {
                        font: '11px Arial'
                    }
                }
                ],
                series: [{
                    type: 'line',
                    lineWidth: 1,
                    showMarkers: true,
                    fill: true,
                    axis: 'left',
                    xField: 'day',
                    yField: 'visitors',
                    style: {
                        'stroke-width': 1,
                        stroke: 'rgb(148, 174, 10)'

                    }
                }, {
                    type: 'line',
                    lineWidth: 1,
                    showMarkers: false,
                    axis: 'left',
                    xField: 'day',
                    yField: 'visits',
                    style: {
                        'stroke-width': 1,
                         stroke: 'rgb(17, 95, 166)'

                    },
                    
                },
                {
                    type: 'line',
                    lineWidth: 1,
                    showMarkers: false,
                    fill: true,
                    axis: 'left',
                    xField: 'day',
                    yField: 'views',
                    style: {
                        'stroke-width': 1,
                         stroke: 'rgb(17, 95, 166)'

                    },
                    
                }
                
                
                
                ]
            }
        });

        this.callParent(arguments);
    }
});
