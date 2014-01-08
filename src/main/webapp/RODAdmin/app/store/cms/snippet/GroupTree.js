/**
 * 
 */
Ext.define('RODAdmin.store.cms.snippet.GroupTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.snippet.Snippet'
    ],

    model: 'RODAdmin.model.cms.snippet.Snippet',
   
    proxy: {type: 'mainajax', url: 'cmssnippetgrouptree'},    

});