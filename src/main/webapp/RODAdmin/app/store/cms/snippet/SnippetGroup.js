/**
 * 
 */
Ext.define('RODAdmin.store.cms.snippet.SnippetGroup', {
    extend: 'RODAdmin.store.Base',	

    requires: [
        'RODAdmin.model.cms.snippet.SnippetGroup',
    ],

    model: 'RODAdmin.model.cms.snippet.SnippetGroup',
    
    autoload: true,
    proxy: {type: 'main', url: '/adminjson/cmssnippetgroupinfo'},    

});