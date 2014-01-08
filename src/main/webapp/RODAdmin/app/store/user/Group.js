/**
 * 
 */
Ext.define('RODAdmin.store.user.Group', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.user.Group'
    ],

    model: 'RODAdmin.model.user.Group',
    autoLoad: true,  
    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/grouplist/'},     
});