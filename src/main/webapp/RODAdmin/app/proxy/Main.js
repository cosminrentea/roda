Ext.define('RODAdmin.proxy.Main', {
    extend: 'Ext.data.proxy.Rest',
    alias: 'proxy.main',
    
    type: 'rest',
    reader: {
        type: 'json',
        messageProperty: 'msg',
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

