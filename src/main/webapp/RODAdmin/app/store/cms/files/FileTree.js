/**
 * 
 */
Ext.define('RODAdmin.store.cms.files.FileTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.files.File'
    ],

    model: 'RODAdmin.model.cms.files.File',
    proxy: {type: 'mainajax', url: 'cmsfiletree'}    
});