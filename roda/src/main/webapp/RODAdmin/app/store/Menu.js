Ext.define('RODAdmin.store.Menu', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.menu.Root'
    ],

    model: 'RODAdmin.model.menu.Root',
    
    proxy: {
        type: 'ajax',
//        url: 'data/menu.json',
  		url: 'http://roda.apiary.io/admin/menu',	      
        reader: {
            type: 'json',
            root: 'items'
        },
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
    }
});