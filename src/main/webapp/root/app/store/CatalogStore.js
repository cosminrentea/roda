
Ext.define('databrowser.store.CatalogStore', {
    extend: 'Ext.data.Store',

    requires: [
        'databrowser.model.CatalogsModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.CatalogsModel',
            storeId: 'CatalogStore',
//            proxy: {
//                type: 'ajax',
//                url: 'data/catalogs.json',
//                reader: {
//                    type: 'json',
//                    root: 'data'
//                }
//            }
        proxy: {
            type: 'rest',
            url: '../../j/studiesbycatalog',  
            appendId: true,
            reader: {
                type: 'json',
                root: 'data'
            }
        }        
        }, cfg)]);
    }
});