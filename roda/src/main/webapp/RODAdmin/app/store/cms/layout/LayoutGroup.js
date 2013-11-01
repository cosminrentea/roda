Ext.define('RODAdmin.store.cms.layout.LayoutGroup', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.layout.LayoutGroup',
    ],

    model: 'RODAdmin.model.cms.layout.LayoutGroup',
    
    autoload: true,
    proxy: {
        type: 'rest',
//        url: 'http://roda.apiary.io/admin/layout/groupinfo/',
        url: 'http://localhost:8080/roda/admin/cmslayoutgroupinfo',
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