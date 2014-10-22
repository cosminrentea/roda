/**
 * 
 */
Ext.define('RODAdmin.store.metadata.Orgs', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.metadata.Orgs'
    ],

    model: 'RODAdmin.model.metadata.Orgs',
    autoload: true,   
    proxy: {type: 'main', url: 'ddieditororglist'},    
 
});