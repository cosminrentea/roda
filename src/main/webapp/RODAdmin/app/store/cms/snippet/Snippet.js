Ext.define('RODAdmin.store.cms.snippet.Snippet', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.snippet.Snippet'
    ],

    model: 'RODAdmin.model.cms.snippet.Snippet',
    autoload: true,    
    proxy: {
        type: 'ajax',
//        url: 'data/snippet/snippetgrid.json',
//		url: 'http://roda.apiary.io/admin/snippet/snippetlist',
		url: 'http://localhost:8080/roda/admin/cmssnippetlist',
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