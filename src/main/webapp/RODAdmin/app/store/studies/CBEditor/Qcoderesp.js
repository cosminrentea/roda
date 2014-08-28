/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.Qcoderesp', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.question.response.Code'
    ],
    model: 'RODAdmin.model.studies.CBEditor.question.response.Code',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});