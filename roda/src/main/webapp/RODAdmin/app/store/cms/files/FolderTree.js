Ext.define('RODAdmin.store.cms.files.FolderTree', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'RODAdmin.model.cms.files.Folder'
    ],

    model: 'RODAdmin.model.cms.files.Folder',
    //folderSort: true,    
    proxy: {
        type: 'ajax',
 //       url: 'data/foldertree.json',
        url: 'http://localhost:8080/roda/admin/cmsfoldertree',
// 		url: 'http://roda.apiary.io/admin/cms/foldertree',        
         reader: {
                type: 'json',
//                root: 'children'
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