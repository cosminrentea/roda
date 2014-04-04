/**
 * 
 */
Ext.define('RODAdmin.store.cms.pages.PageItem', {
    extend: 'RODAdmin.store.Base',	

    requires: [
        'RODAdmin.model.cms.pages.PageItem',
    ],

    model: 'RODAdmin.model.cms.pages.PageItem',
    
    autoLoad: false,
    //proxy: {type: 'main', url: 'http://roda.apiary.io/admin/cmspageinfo/'},
    proxy: {type: 'main', url: 'cmspageinfo/'},

});