Ext.define('databrowser.store.CatalogTreeStore', {
    extend : 'Ext.data.TreeStore',
    storeId : 'CatalogTreeStore',
    requires : [
	    'databrowser.model.CatalogsTreeModel'
    ],
    autoLoad : true,
    model : 'databrowser.model.CatalogsTreeModel',
    
    listeners : {
	    beforeload : function(store, operation, eOpts) {
		    if (store.isLoading()) return false;
	    }
    },

    root : {
        name : 'RODA',
        expandable : true,
        expanded : true,
        loaded : true,
        type : 'M',
        indice : 0,
    },
    proxy : {
        type : 'ajax',
        url : '../catalogtree',
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