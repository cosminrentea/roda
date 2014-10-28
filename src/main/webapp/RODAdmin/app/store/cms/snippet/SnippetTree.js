/**
 * 
 */
Ext.define('RODAdmin.store.cms.snippet.SnippetTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.snippet.Snippet'
    ],
    autoLoad: false,
    model: 'RODAdmin.model.cms.snippet.Snippet',
    proxy: {type: 'mainajax', url: 'adminjson/cmssnippettree'},    
    folderSort: true,    

});