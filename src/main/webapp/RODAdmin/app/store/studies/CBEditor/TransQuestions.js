/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.TransQuestions', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.TranslatedQuestion'
    ],
    model: 'RODAdmin.model.studies.CBEditor.TranslatedQuestion',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});