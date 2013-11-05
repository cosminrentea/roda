Ext.define('RODAdmin.store.cms.snippet.SnippetItem', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.snippet.SnippetItem',
        'RODAdmin.model.cms.snippet.SnippetUsage' 
    ],

    model: 'RODAdmin.model.cms.snippet.SnippetItem',
    
    autoload: true,
    proxy: {
        type: 'rest',
//        url:'data/snippet/snippetinfo.json',
//        url: 'http://roda.apiary.io//admin/snippet/snippetinfo/',
        url: 'http://localhost:8080/roda/admin/cmssnippetinfo',        
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