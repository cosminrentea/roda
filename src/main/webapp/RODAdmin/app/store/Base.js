/**
 *  Base Store for all non=tree data
 */
Ext.define('RODAdmin.store.Base', {
	extend: 'Ext.data.Store',

	listeners: {
		beforeload: function(store, operation, options){
			store.getProxy().extraParams = {
				lang : localStorage ? (localStorage.getItem('user-lang') || 'en') : 'en'
			};
			var purl = store.getProxy().url;
			var baseurlreg = new RegExp('^'+RODAdmin.util.Globals.baseurl, '');
			if (purl.match(/^http:/i)) {
			} else if (purl.match(baseurlreg))	 {
			} else {
				if (RODAdmin.util.Globals.baseurl) {
					if (purl.match(/^\/adminjson/)) {
						store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
					} else if (purl.match(/^\/userjson/)) {
						store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
					} else {
						store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
					}
				} else {
						if (purl.match(/^\/adminjson/)) {
							store.getProxy().url = purl;
						} else if (purl.match(/^\/userjson/)) {
							store.getProxy().url = purl;
						} else {
							store.getProxy().url = purl;
						}
					}
			}
		}
	}
});