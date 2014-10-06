/**
 * Base Store for all tree data
 */
Ext.define('RODAdmin.store.BaseTree', {
	extend: 'Ext.data.TreeStore',

	listeners: {
		beforeload: function(store, operation, options){
			var purl = store.getProxy().url;
			if (purl.match(/^http:/i)) {
				console.log('leave url alone' + purl);
			} else {
//				console.log('initial url ' + purl);	
//				console.log('add base url ' + RODAdmin.util.Globals.baseurl);
//				console.log('final url ' + RODAdmin.util.Globals.baseurl + purl);
				store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
				if (purl.match(/\/roda\/adminjson/)) {
					store.getProxy().url =  purl;
				} else if (purl.match(/\/roda\/userjson/)) {
					store.getProxy().url = purl;
				} else if (purl.match(/\/roda/)) {
							store.getProxy().url = purl;
				} else {
					store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
				}
			}
		}
	}
});