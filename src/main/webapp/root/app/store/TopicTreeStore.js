Ext.define('databrowser.store.TopicTreeStore', {
    extend : 'Ext.data.TreeStore',
    storeId : 'TopicTreeStore',
    requires : [
	    'databrowser.model.TopicTreeModel'
    ],
    autoLoad : true,
    model : 'databrowser.model.TopicTreeModel',

    listeners : {
	    beforeload : function(store, operation, eOpts) {
		    if (store.isLoading()) return false;
	    }
    },

    root : {
        name : 'ELSST',
        expandable : true,
        expanded : true,
        loaded : true,
        type : 'T',
        indice : 0,
    },
    proxy : {
        type : 'ajax',
        url : '../topictree',
        timeout : 200000,
        extraParams : {
	        lang : translations.language
        },
        reader : {
            type : 'json',
            root : 'data'
        }
    },
});
