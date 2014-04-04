/**
 * 
 */
Ext.define('RODAdmin.store.user.User', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.user.User'
    ],

    model: 'RODAdmin.model.user.User',
    autoLoad: false,    
    proxy: {type: 'main', url: 'userslist/'},      

});