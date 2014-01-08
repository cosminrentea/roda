/**
 * 
 */
Ext.define('RODAdmin.store.user.User', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.user.User'
    ],

    model: 'RODAdmin.model.user.User',
    autoLoad: true,    
    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/userslist/'},      

});