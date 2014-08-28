/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.TransQcoderesp', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.question.response.TransCode'
    ],
    model: 'RODAdmin.model.studies.CBEditor.question.response.TransCode',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});