Ext.define('RODAdmin.store.cms.files.Folder', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.files.Folder'
    ],

    model: 'RODAdmin.model.cms.files.Folder',
    autoload: true,    
    proxy: {
        type: 'ajax',
//        url: 'data/filegrid.json',
// 		url: 'http://roda.apiary.io/admin/cms/filegrid',    
 		url: 'http://localhost:8080/roda/admin/cmsfolderinfo',
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