/**
 * 
 */
Ext.define('RODAdmin.store.metadata.City', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.metadata.City'
    ],

    model: 'RODAdmin.model.metadata.City',
    autoload: true,   
    proxy: {type: 'main', url: 'ddieditorcitylist'},    
 
});