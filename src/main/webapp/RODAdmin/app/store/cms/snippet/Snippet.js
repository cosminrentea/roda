/**
 * 
 */
Ext.define('RODAdmin.store.cms.snippet.Snippet', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.snippet.Snippet'
    ],

    model: 'RODAdmin.model.cms.snippet.Snippet',
    autoload: true,   
    proxy: {type: 'main', url: 'cmssnippetlist'},    
 
});