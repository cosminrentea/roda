Ext.define('databrowser.store.YearStore', {
    extend : 'Ext.data.Store',

    requires : [
	    'databrowser.model.YearModel'
    ],

    constructor : function(cfg) {
	    var me = this;
	    cfg = cfg || {};
	    me.callParent([
		    Ext.apply({
		        autoLoad : true,
		        model : 'databrowser.model.YearModel',
		        storeId : 'YearStore',
		        proxy : {
		            type : 'rest',
		            url : '../../studiesbyyear',
		            appendId : true,
		            extraParams : {
			            lang : translations.language
		            },
		            reader : {
		                type : 'json',
		                root : 'data'
		            }
		        }
		    }, cfg)
	    ]);
    }
});