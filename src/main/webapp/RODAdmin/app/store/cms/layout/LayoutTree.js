/**
 * 
 */
Ext.define('RODAdmin.store.cms.layout.LayoutTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.layout.Layout'
    ],
    autoLoad: false,
    model: 'RODAdmin.model.cms.layout.Layout',
    proxy: {type: 'mainajax', url: 'adminjson/cmslayouttree'},    

   folderSort: true,    

});