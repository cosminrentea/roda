Ext.define('RODAdmin.store.cms.snippet.SnippetGroup', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.cms.snippet.SnippetGroup',
    ],

    model: 'RODAdmin.model.cms.snippet.SnippetGroup',
    
    autoload: true,
    proxy: {
        type: 'ajax',
//          url: 'data/snippet/snippetgroup.json',	
//        url: 'http://roda.apiary.io/admin/snippet/groupinfo/',
        url: 'http://localhost:8080/roda/admin/cmssnippetgroupinfo',
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