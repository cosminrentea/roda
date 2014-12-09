Ext.define('anax.store.Indicator', {
	
	extend : 'Ext.data.Store',
    storeId : 'GeoDataType',
    requires : [
	    'anax.model.Indicator'
    ],
    autoLoad : true,
    model : 'anax.model.Indicator',
    

proxy : {
        type : 'ajax',
        url :  '/roda/resources/RODAAnax/data/densitate.json',
        extraParams : {
	        lang : translations.language
        },
        reader : {
            type : 'json',
            root : 'data'
        }
    }
});