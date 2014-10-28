/**
 * 
 */
Ext.define('RODAdmin.store.cms.snippet.SnippetItem', {
    extend: 'RODAdmin.store.Base',		

    requires: [
        'RODAdmin.model.cms.snippet.SnippetItem',
        'RODAdmin.model.cms.snippet.SnippetUsage' 
    ],

    model: 'RODAdmin.model.cms.snippet.SnippetItem',
    
    autoload: true,
    proxy: {type: 'main', url: '/adminjson/cmssnippetinfo'},    

});