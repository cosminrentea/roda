/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.Files', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.File'
    ],
    model: 'RODAdmin.model.studies.CBEditor.File',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});