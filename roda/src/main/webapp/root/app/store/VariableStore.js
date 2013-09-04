
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
                type: 'rest',
                url: '../../variables',  
                appendId: true,
                reader: {
                    type: 'json',
                    root: 'data'
                },
                headers : {
          		    'Content-type' : 'application/json',
        		    'Accept': 'application/json'		    
        		}
            }
        }, cfg)]);
    }
});