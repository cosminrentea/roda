
Ext.define('databrowser.store.VariableStore', {
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
            storeId: 'VariableStore',
            proxy: {
                type: 'rest',
                url: '../variables',  
                extraParams : {
                	lang : translations.language
                },
                appendId: true,
                reader: {
                    type: 'json',
                    root: 'data'
                },
                headers : {
        		    'Accept': 'application/json'		    
        		}
            }
        }, cfg)]);
    }
});