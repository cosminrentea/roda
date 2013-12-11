Ext.define('RODAdmin.store.cron.ExecutionList', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cron.action.Execution',
    ],

    model: 'RODAdmin.model.cron.action.Execution',
    
    autoLoad: false,
    proxy: {
        type: 'rest',
        url: 'http://roda.apiary.io/admin/scheduler/executionsbytask/',
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