/**
 * 
 */
Ext.define('RODAdmin.store.cms.Collateral', {
     extend: 'RODAdmin.store.BaseTree',


    requires: [
        'RODAdmin.model.cms.Collateral'
    ],

    model: 'RODAdmin.model.cms.Collateral',
    autoload: true,   
    proxy: {type: 'mainajax', url: 'http://roda.apiary.io/admin/cmscollaterals'}
 
});