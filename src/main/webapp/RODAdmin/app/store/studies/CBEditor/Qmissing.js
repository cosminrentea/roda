/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.Qmissing', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.question.Missing'
    ],
    model: 'RODAdmin.model.studies.CBEditor.question.Missing',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});