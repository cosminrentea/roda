Ext.define('RODAdmin.store.security.Permissions', {
    extend: 'Ext.data.TreeStore',

    clearOnLoad: true,

    proxy: {
        type: 'ajax',
        url: ''
    }
});