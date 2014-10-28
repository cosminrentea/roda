/**
 * 
 */
Ext.define('RODAdmin.store.cms.pages.PageTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.cms.pages.Page'
    ],

    model: 'RODAdmin.model.cms.pages.Page',
    autoLoad: false,   
    //proxy: {type: 'mainajax', url: 'http://roda.apiary.io/admin/cmspagestree'},   
    proxy: {type: 'mainajax', url: 'adminjson/cmspagestree'}
});