Ext.define('RODAdmin.store.cms.pages.PageItem', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.pages.PageItem',
    ],

    model: 'RODAdmin.model.cms.pages.PageItem',
    
    autoload: true,
    proxy: {
        type: 'rest',
        url: 'http://roda.apiary.io//admin/cmspageinfo/',
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