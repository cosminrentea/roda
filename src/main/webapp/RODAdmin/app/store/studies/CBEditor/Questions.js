/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.Questions', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.StudyQuestion'
    ],
    model: 'RODAdmin.model.studies.CBEditor.StudyQuestion',
    autoSync: true,
   
    proxy: {
        type: 'memory'
    }
});