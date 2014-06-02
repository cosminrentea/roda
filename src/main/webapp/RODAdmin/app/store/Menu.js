/**
 * 
 */
Ext.define('RODAdmin.store.Menu', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.menu.Root',
    ],

    model: 'RODAdmin.model.menu.Root',
//    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/menu',reader:{root:'items'}},
    proxy: {type: 'main', url: '/roda/j/user/menu',reader:{root:'items'}},
    

});