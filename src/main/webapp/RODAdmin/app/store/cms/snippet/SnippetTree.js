Ext.define('RODAdmin.store.cms.snippet.SnippetTree', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'RODAdmin.model.cms.snippet.Snippet'
    ],

    model: 'RODAdmin.model.cms.snippet.Snippet',
 //   folderSort: true,    
    proxy: {
        type: 'ajax',
//        url: 'data/snippet/snippettree.json',
//		url: 'http://roda.apiary.io/admin/snippet/snippettree',     
		url: 'http://localhost:8080/roda/admin/cmssnippettree',		
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