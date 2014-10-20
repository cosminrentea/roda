/**
 * 
 */
Ext.define('RODAdmin.store.metadata.Persons', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.metadata.Persons'
    ],

    model: 'RODAdmin.model.metadata.Persons',
    autoload: true,   
    proxy: {type: 'main', url: '/ddieditorpersonlist'},    
 
});