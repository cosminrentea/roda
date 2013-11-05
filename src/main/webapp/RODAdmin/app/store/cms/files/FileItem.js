Ext.define('RODAdmin.store.cms.files.FileItem', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.files.FileItem',
        'RODAdmin.model.cms.files.FileProperties', //?
        'RODAdmin.model.cms.files.FileUsage' //?
    ],

    model: 'RODAdmin.model.cms.files.FileItem',
    
    autoload: true,
    proxy: {
        type: 'rest',
//        url: 'data/fileitem.json',
 		url: 'http://roda.apiary.io/admin/cms/fileinfo/',            
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