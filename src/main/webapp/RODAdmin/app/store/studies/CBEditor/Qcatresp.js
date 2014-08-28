/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.Qcatresp', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.question.response.Category'
    ],
    model: 'RODAdmin.model.studies.CBEditor.question.response.Category',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});