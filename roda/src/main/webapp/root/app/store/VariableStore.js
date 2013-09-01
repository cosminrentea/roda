
Ext.define('databrowser.store.VariableStore', {
    extend: 'Ext.data.Store',

    requires: [
        'databrowser.model.VariableModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.VariableModel',
            storeId: 'VariableStore',
            proxy: {
                type: 'ajax',
                url: 'data/variable.json',
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});