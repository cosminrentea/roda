
Ext.define('databrowser.store.YearStore', {
    extend: 'Ext.data.Store',

    requires: [
        'databrowser.model.YearModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.YearModel',
            storeId: 'YearStore',
//            proxy: {
//                type: 'ajax',
//                url: 'data/years.json',
//                reader: {
//                    type: 'json',
//                    root: 'data'
//                }
//            }
        proxy: {
            type: 'rest',
            url: '../../j/studiesbyyear',  
            appendId: true,
            reader: {
                type: 'json',
                root: 'data'
            }
        }  
        
        
        }, cfg)]);
    }
});