/**
 * 
 */
Ext.define('RODAdmin.store.cms.pages.PageTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.pages.Page'
    ],

    model: 'RODAdmin.model.cms.pages.Page',
    autoload: true,   
    proxy: {type: 'mainajax', url: 'http://roda.apiary.io/admin/cmspagestree'},    
});