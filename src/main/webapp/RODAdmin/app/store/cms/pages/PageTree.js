Ext.define('RODAdmin.store.cms.pages.PageTree', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'RODAdmin.model.cms.pages.Page'
    ],

    model: 'RODAdmin.model.cms.pages.Page',
    autoload: true,    
    proxy: {
        type: 'ajax',
 		url: 'http://roda.apiary.io/admin/cmspagestree',    
// 		url: 'http://localhost:8080/roda/admin/cmsfilelist',
         reader: {
                type: 'json',
                root: 'children'
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