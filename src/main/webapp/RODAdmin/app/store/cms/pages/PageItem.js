/**
 * 
 */
Ext.define('RODAdmin.store.cms.pages.PageItem', {
    extend: 'RODAdmin.store.Base',	

    requires: [
        'RODAdmin.model.cms.pages.PageItem',
    ],

    model: 'RODAdmin.model.cms.pages.PageItem',
    
    autoload: true,
    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/cmspageinfo/'},      

});