Ext.define('RODAdmin.proxy.MainAjax', {
    extend: 'Ext.data.proxy.Ajax',
    alias: 'proxy.mainajax',
    extraParams : {
		lang : localStorage ? (localStorage.getItem('user-lang') || 'en') : 'en'
    },
    reader: {
        type: 'json',
        messageProperty: 'msg',
//        root: 'children'
        root: 'data'        
    },
    listeners: {
        exception: function(proxy, response, operation){
        	if (response.responseText.match(/login\.js/)) {
          		window.location = RODAdmin.util.Globals.baseurl + 'admin/login.html';
        	} else {
            Ext.MessageBox.show({
                title: 'REMOTE EXCEPTION',
                msg: operation.getError(),
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        	}
        }
    }
});

