Ext.define('databrowser.store.SeriesStore', {
    extend: 'Ext.data.JsonStore',

    requires: [
        'databrowser.model.SeriesModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.SeriesModel',
            storeId: 'SeriesStore',
            proxy: {
                type: 'ajax',
                url: 'data/series.json',
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});