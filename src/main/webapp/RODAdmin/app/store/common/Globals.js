/**
 * Store pentru setari generale
 */
Ext.define('RODAdmin.store.common.Globals', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.common.Globals',
    ],

    model: 'RODAdmin.model.common.Globals',
    
    autoload: true,
    proxy: {
        type: 'ajax',
        url: 'http://roda.apiary.io/admin/globals',        
         reader: {
                type: 'json',
                root: 'data'
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