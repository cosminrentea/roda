
Ext.define('databrowser.store.TopicStore', {
    extend: 'Ext.data.Store',

    requires: [
        'databrowser.model.TopicsModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.TopicsModel',
            storeId: 'CatalogStore',
        proxy: {
            type: 'rest',
            url: '../studiesbytopic',
            extraParams : {
            	lang : translations.language
            },
            appendId: true,
            reader: {
                type: 'json',
                root: 'data'
            }
        }        
        }, cfg)]);
    }
});