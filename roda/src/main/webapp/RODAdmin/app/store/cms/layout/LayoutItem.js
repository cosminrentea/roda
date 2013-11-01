Ext.define('RODAdmin.store.cms.layout.LayoutItem', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.layout.LayoutItem',
        'RODAdmin.model.cms.layout.LayoutUsage' 
    ],

    model: 'RODAdmin.model.cms.layout.LayoutItem',
    
    autoload: true,
    proxy: {
        type: 'rest',
        //url: 'http://roda.apiary.io//admin/layout/layoutinfo/',
        url: 'http://localhost:8080/roda/admin/cmslayoutinfo/',
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