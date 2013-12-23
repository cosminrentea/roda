/**
 * 
 */
Ext.define('RODAdmin.store.cms.snippet.SnippetTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.snippet.Snippet'
    ],

    model: 'RODAdmin.model.cms.snippet.Snippet',
    proxy: {type: 'mainajax', url: 'cmssnippettree'},    
    folderSort: true,    

});