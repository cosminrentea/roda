Ext.define('RODAdmin.store.cms.layout.GroupTree', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'RODAdmin.model.cms.layout.Layout'
    ],

    model: 'RODAdmin.model.cms.layout.Layout',
 //   folderSort: true,    
    proxy: {
        type: 'ajax',
//        url: 'data/filetreegrid.json',
//		url: 'http://roda.apiary.io/admin/layout/layouttree/',   
		url: 'http://localhost:8080/roda/admin/cmslayouttree/',		
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