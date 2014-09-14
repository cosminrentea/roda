
Ext.define('databrowser.store.SrMemoryVariableStore', {
    extend: 'Ext.data.Store',

    requires: [
        'databrowser.model.VariableModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'databrowser.model.VariableModel',
            itemId: 'srmemoryvariablestore',
            proxy: {
            	type: 'memory',
            }
        }, cfg)]);
    }
});