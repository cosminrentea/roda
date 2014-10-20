/**
 * 
 */
Ext.define('RODAdmin.store.metadata.Prefixes', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.metadata.Prefixes'
    ],

    model: 'RODAdmin.model.metadata.Prefixes',
    autoload: true,   
    proxy: {type: 'main', url: '/ddieditorprefixlist'},    
 
});