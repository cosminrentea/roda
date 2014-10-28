/**
 * 
 */
Ext.define('RODAdmin.store.audit.RevisedObjects', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.audit.RevisedObjects'
    ],

    model: 'RODAdmin.model.audit.RevisedObjects',
    autoLoad: false,    
    proxy: {type: 'main', url: 'adminjson/revised-objects'},  
});