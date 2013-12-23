/**
 *  Base Store for all tree data
 */
Ext.define('RODAdmin.store.BaseTree', {
    extend: 'Ext.data.TreeStore',
    
    listeners: {
        beforeload: function(store, operation, options){
        	var purl = store.getProxy().url;
        	if (purl.match(/^http:/i)) {
            	console.log('leave url alone' + purl);
        	} else {
        	console.log('add base url' + purl);
        	store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
        	}
        }
    }
});