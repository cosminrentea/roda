/**
 * 
 */
Ext.define('RODAdmin.store.user.GroupInfo', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.user.GroupInfo'
    ],

    model: 'RODAdmin.model.user.GroupInfo',
    autoLoad: false,    
    proxy: {type: 'main', url: 'groupinfo/'},      

});