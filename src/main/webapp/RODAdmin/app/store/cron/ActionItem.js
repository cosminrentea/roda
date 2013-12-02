Ext.define('RODAdmin.store.cron.ActionItem', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cron.action.Action',
    ],

    model: 'RODAdmin.model.cron.action.Action',
    
    autoload: true,
    proxy: {
        type: 'rest',
        url: 'http://roda.apiary.io/admin/scheduler/tasks/',
//        url: 'http://localhost:8080/roda/admin/cmslayoutinfo/',
        //       url: 'data/layout/layoutinfo.json',
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