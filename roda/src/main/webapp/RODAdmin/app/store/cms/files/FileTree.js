Ext.define('RODAdmin.store.cms.files.FileTree', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'RODAdmin.model.cms.files.File'
    ],

    model: 'RODAdmin.model.cms.files.File',
 //   folderSort: true,    
    proxy: {
        type: 'ajax',
//        url: 'data/filetreegrid.json',
        url: 'http://localhost:8080/roda/admin/cmsfiletree',        
// 		url: 'http://roda.apiary.io/admin/cms/filetree/',	
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