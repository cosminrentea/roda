Ext.define('databrowser.store.TopicTreeStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'databrowser.model.TopicTreeModel'
    ],
    autoLoad: false, 
    model: 'databrowser.model.TopicTreeModel',
    
    listeners: {        
        beforeload: function (store, operation, eOpts) {            
             if(store.isLoading()) return false;        
        }    
   	},

	    root: {
	         name: 'ELSST',
	         translation: 'ELSST',	         
	         expandable: true,
//	         leaf: false,
	         expanded: true,
	         loaded: true,
	         indice: null,
//	         children: {
//	             
//	         }
	       },    
	    proxy: {
	    	type: 'ajax',
	        url: '../topics/tree',
	    	timeout: 200000,
	    	extraParams : {
	    		lang : translations.language
	    	},
	    	reader: {
	    		type: 'json',
	    		root: 'topics'
	    	}
	  	},
    
//    
//    
//    constructor: function(cfg) {
//        var me = this;
//        cfg = cfg || {};
//        me.callParent([Ext.apply({
//            autoLoad: true,
//            model: 'databrowser.model.TopicTreeModel',
//            storeId: 'TopicTreeStore',
//            root: {
//                name: 'ELSST',
//                expandable: true,
//                indice : null,
//                children: {
//                    
//                }
//            },
//            defaultRootId: null,
//            proxy: {
//                type: 'ajax',
//                extraParams : {
//                	lang : translations.language
//                },
//                timeout:200000,
//                url: '../topics/tree',
//                reader: {
//                    type: 'json',
//                    root: 'topics'
//                }
//            }
//        }, cfg)]);
//    }
});
