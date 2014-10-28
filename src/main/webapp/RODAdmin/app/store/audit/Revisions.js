/**
 * 
 */
Ext.define('RODAdmin.store.audit.Revisions', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.audit.Revisions'
    ],

    model: 'RODAdmin.model.audit.Revisions',
    autoload: true,    
    proxy: {type: 'main', url: 'adminjson/revisionsinfo'},  
});