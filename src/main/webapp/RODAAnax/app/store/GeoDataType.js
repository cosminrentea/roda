Ext.define('anax.store.GeoDataType', {
	
	extend : 'Ext.data.Store',
    storeId : 'GeoDataType',
    requires : [
	    'anax.model.GeoDatatype'
    ],
    autoLoad : true,
    model : 'anax.model.GeoDatatype',
    

proxy : {
        type : 'ajax',
        url: 'http://private-cfbcc-rodaanax.apiary-mock.com/anax/geodatatype/12',
        timeout : 20000,
        extraParams : {
	        lang : translations.language
        },
        reader : {
            type : 'json',
            root : 'data'
        }
    }
});