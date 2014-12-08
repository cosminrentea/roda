/**
 * 
 */
Ext.define('anax.store.ItStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
               'anax.model.ItemsModel'  
               ],
    model: 'anax.model.ItemsModel',

    listeners : {
	    beforeload : function(store, operation, eOpts) {
		    if (store.isLoading()) return false;
	    }
    },

	autoLoad : true,
    
  root : {
  name : 'RODA',
  expandable : true,
  expanded : true,
  text : 'Maps',
  type : 'M',
  id : 0,
  },
    
    proxy : {
        type : 'ajax',
        url : 'http://private-cfbcc-rodaanax.apiary-mock.com/geodatatypetree',
        timeout : 200000,
        extraParams : {
	        lang : translations.language
        },
        reader : {
            type : 'json',
            root : 'data'
        }
    }
});

