/**
 * 
 */
Ext.define('RODAdmin.store.security.Users', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.security.User'
    ],

    model: 'RODAdmin.model.security.User',

    proxy: {
        type: 'ajax',
        url: '',
        
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});