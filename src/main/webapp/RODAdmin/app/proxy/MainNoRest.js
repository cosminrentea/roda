Ext.define('RODAdmin.proxy.MainNoRest', {
    extend: 'Ext.data.proxy.Ajax',
    alias: 'proxy.mainnorest',
    reader: {
        type: 'json',
        messageProperty: 'msg',
        root: 'data'
    },
    listeners: {
        exception: function(proxy, response, operation){
        	console.log(response.responseText);
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

