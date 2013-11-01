Ext.define('RODAdmin.store.security.Groups', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.security.Group'
    ],

    model: 'RODAdmin.model.security.Group',

    storeId: 'groups',

    autoLoad: true,

    proxy: {
        type: 'ajax',
        url: '',
        
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});