/**
 * 
 */
Ext.define('RODAdmin.store.Menu', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.menu.Root',
    ],

    model: 'RODAdmin.model.menu.Root',
    proxy: {type: 'main', url: '/userjson/menu',reader:{root:'items'}},
    

});