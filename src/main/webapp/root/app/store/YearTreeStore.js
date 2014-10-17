Ext.define('databrowser.store.YearTreeStore', {
    extend : 'Ext.data.TreeStore',

    requires : [
	    'databrowser.model.YearsTreeModel'
    ],
    autoLoad: true,   
    model :  'databrowser.model.YearsTreeModel',
    
    listeners: {        
        beforeload: function (store, operation, eOpts) {            
             if(store.isLoading()) return false;        
        }    
   	},

	    root: {
	         name: 'RODA',
	         expandable: true,
	         expanded: true,
	         loaded: true,
	         type: 'M',
	         indice: 0,
//	         children: {
//	             
//	         }
	       },    
	    proxy: {
	    	type: 'ajax',
            url : '../yearstree',
	    	timeout: 200000,
	    	extraParams : {
	    		lang : translations.language
	    	},
	    	reader: {
	    		type: 'json',
	    		root: 'data'
	    	}
	  	},
	  	
	  	
//	  	
//    constructor : function(cfg) {
//	    var me = this;
//	    cfg = cfg || {};
//	    me.callParent([
//		    Ext.apply({
//		        autoLoad : true,
//		        model : 'databrowser.model.YearsTreeModel',
//		        storeId : 'YearTreeStore',
//		        root : {
//		            name : 'Root',
//		            expandable : true,
//		            children : {
//
//		            }
//		        },
//		        proxy : {
//		            type : 'ajax',
//		            timeout : 200000,
//		            extraParams : {
//			            lang : translations.language
//		            },
//		            url : '../../yearstree',
//		            reader : {
//		                type : 'json',
//		                root : 'data'
//		            }
//		        }
//		    }, cfg)
//	    ]);
//    }
});