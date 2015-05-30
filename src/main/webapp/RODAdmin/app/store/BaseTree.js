/**
 * Base Store for all tree data
 */
Ext.define('RODAdmin.store.BaseTree', {
	extend: 'Ext.data.TreeStore',

	listeners: {
		beforeload: function(store, operation, options){
			store.getProxy().extraParams = {
				lang : localStorage ? (localStorage.getItem('user-lang') || 'en') : 'en'
			};			
			var purl = store.getProxy().url;
			var baseurlreg = new RegExp('^'+RODAdmin.util.Globals.baseurl, '');
			if (purl.match(/^http:/i)) {
				console.log('leave url alone' + purl);
			} else if (purl.match(baseurlreg))	 {
				console.log('leave url alone' + purl);				
			} else {
				if (RODAdmin.util.Globals.baseurl) {
					store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
				} else {
					store.getProxy().url = purl;
				} 
				if (purl.match(/^\/adminjson/)) {
					store.getProxy().url =  purl;
				} else if (purl.match(/^\/userjson/)) {
					store.getProxy().url = purl;
				} else {
					if (RODAdmin.util.Globals.baseurl) {
						store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
					} else {
						store.getProxy().url = purl;
					} 
				}

			}
		}
	}
});