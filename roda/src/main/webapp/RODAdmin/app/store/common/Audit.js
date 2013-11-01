Ext.define('RODAdmin.store.common.Audit', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.common.Audit',
        'RODAdmin.model.common.AuditField'
    ],

    model: 'RODAdmin.model.common.Audit',
    
    autoload: true,
    proxy: {
        type: 'ajax',
        url: 'data/fileaudit.json',
        url: 'http://roda.apiary.io/admin/audit/object/',        
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