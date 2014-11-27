/**
 * 
 */
Ext.define('RODAdmin.store.user.UsersbyGroup', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.user.UserInfo',
    ],

    model: 'RODAdmin.model.user.UserInfo',
    autoLoad: false,    
    proxy: {type: 'main', url: 'adminjson/usersbygroup/'},      

});