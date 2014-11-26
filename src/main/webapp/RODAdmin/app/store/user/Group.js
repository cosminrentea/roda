/**
 * 
 */
Ext.define('RODAdmin.store.user.Group', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.user.Group'
    ],

    model: 'RODAdmin.model.user.Group',
    autoLoad: false,  
    proxy: {type: 'main', url: 'adminjson/grouplist/'},     
});