Ext.define('RODAdmin.store.cms.files.File', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.files.File'
    ],

    model: 'RODAdmin.model.cms.files.File',
    autoload: true,    
    proxy: {
        type: 'ajax',
//        url: 'data/filegrid.json',
// 		url: 'http://roda.apiary.io/admin/cms/filegrid',    
 		url: 'http://localhost:8080/roda/admin/cmsfilelist',
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