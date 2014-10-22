/**
 * 
 */
Ext.define('RODAdmin.store.metadata.Country', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.metadata.Country'
    ],

    model: 'RODAdmin.model.metadata.Country',
    autoload: true,   
    proxy: {type: 'main', url: '/ddieditorcountrylist'},    
 
});