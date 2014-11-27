/**
 * 
 */
Ext.define('RODAdmin.store.cms.files.File', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.files.File'
    ],

    model: 'RODAdmin.model.cms.files.File',
    autoload: true,    
    proxy: {type: 'main', url: 'userjson/cmsfilelist'},  
});