/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.Variables', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.StudyVariable'
    ],
    model: 'RODAdmin.model.studies.CBEditor.StudyVariable',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});