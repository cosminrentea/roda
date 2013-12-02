Ext.define('RODAdmin.store.cron.ActionList', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cron.action.Action',
    ],

    model: 'RODAdmin.model.cron.action.Action',
    
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: 'http://roda.apiary.io/admin/scheduler/tasks',
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
