/**
 *  Base Store for all non=tree data
 */
Ext.define('RODAdmin.store.Base', {
	extend: 'Ext.data.Store',

	listeners: {
		beforeload: function(store, operation, options){
			console.log('base url globals' + RODAdmin.util.Globals.baseurl);
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
				console.log('initial url ' + purl);	
//				console.log('add base url ' + RODAdmin.util.Globals.baseurl);
				console.log('final url ' + RODAdmin.util.Globals.baseurl + purl);
				if (purl.match(/^\/adminjson/)) {
					store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
				} else if (purl.match(/^\/userjson/)) {
					console.log('userjson url');
					store.getProxy().url = RODAdmin.util.Globals.baseurl + purl; 
//				} else if (purl.match(/\//)) {
//							store.getProxy().url = purl;
				} else {
					store.getProxy().url = RODAdmin.util.Globals.baseurl + purl;
				}
			}
		}
	}
});