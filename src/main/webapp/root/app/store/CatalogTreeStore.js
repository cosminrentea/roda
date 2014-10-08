Ext.define('databrowser.store.CatalogTreeStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'databrowser.model.CatalogsTreeModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.CatalogsTreeModel',
            storeId: 'CatalogTreeStore',
            root: {
                name: 'Root',
                expandable: true,
                children: {
                    
                }
            },
            proxy: {
                type: 'ajax',
                url: '../../catalogtree',  
                timeout: 200000,
                extraParams : {
                	lang : translations.language
                },
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});