/**
 * 
 */
Ext.define('RODAdmin.store.cms.files.FileItem', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.files.FileItem',
        'RODAdmin.model.cms.files.FileProperties', //?
        'RODAdmin.model.cms.files.FileUsage' //?
    ],

    model: 'RODAdmin.model.cms.files.FileItem',
    
    autoload: true,
    proxy: {type: 'main', url: 'adminjson/cmsfilelist'},     
});