/**
 * 
 */
Ext.define('RODAdmin.store.user.UserInfo', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.user.UserInfo',
        'RODAdmin.model.user.UserProfile'        
    ],

    model: 'RODAdmin.model.user.UserInfo',
    autoLoad: false,    
    proxy: {type: 'main', url: 'userinfo/'},      

});