/**
 * 
 */
Ext.define('RODAdmin.store.cms.files.FolderTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.files.Folder'
    ],

    model: 'RODAdmin.model.cms.files.Folder',
    //folderSort: true,    
    proxy: {type: 'mainajax', url: 'adminjson/cmsfoldertree'}

});