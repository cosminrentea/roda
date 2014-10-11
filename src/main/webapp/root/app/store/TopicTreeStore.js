Ext.define('databrowser.store.TopicTreeStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'databrowser.model.TopicTreeModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.TopicTreeModel',
            storeId: 'TopicTreeStore',
            root: {
                name: 'ELSST',
                expandable: true,
                indice : null,
                children: {
                    
                }
            },
            defaultRootId: null,
            proxy: {
                type: 'ajax',
                extraParams : {
                	lang : translations.language
                },
                timeout:200000,
                url: '../topics/tree',
                reader: {
                    type: 'json',
                    root: 'topics'
                }
            }
        }, cfg)]);
    }
});
