/**
 * 
 */
Ext.define('RODAdmin.store.audit.RevisedUsers', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.audit.RevisedUsers'
    ],

    model: 'RODAdmin.model.audit.RevisedUsers',
    autoLoad: false,    
    proxy: {type: 'main', url: 'adminjson/revised-users'},  
});