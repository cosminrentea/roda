/**
 * 
 */
Ext.define('RODAdmin.store.cms.files.Folder', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.files.Folder'
    ],

    model: 'RODAdmin.model.cms.files.Folder',
    autoload: true,    
    proxy: {type: 'mainajax', url: 'cmsfolderinfo'},        

});