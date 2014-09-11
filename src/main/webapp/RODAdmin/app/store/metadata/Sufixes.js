/**
 * 
 */
Ext.define('RODAdmin.store.metadata.Sufixes', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.metadata.Sufixes'
    ],

    model: 'RODAdmin.model.metadata.Sufixes',
    autoload: true,   
    proxy: {type: 'main', url: '/roda/j/ddieditorsuffixlist'},    
 
});