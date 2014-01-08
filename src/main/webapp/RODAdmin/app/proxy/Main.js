Ext.define('RODAdmin.proxy.Main', {
    extend: 'Ext.data.proxy.Rest',
    alias: 'proxy.main',
    
    type: 'rest',
    reader: {
        type: 'json',
        messageProperty: 'msg',
        root: 'data'
    },

//    writer: {
//        type: 'json',
//        writeAllFields: true,
//        encode: true,
//        allowSingle: false,
//        root: 'data'
//    },

    listeners: {
        exception: function(proxy, response, operation){
            Ext.MessageBox.show({
                title: 'REMOTE EXCEPTION',
                msg: operation.getError(),
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    }
});

