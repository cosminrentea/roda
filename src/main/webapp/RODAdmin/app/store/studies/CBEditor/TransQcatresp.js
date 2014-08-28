/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.TransQcatresp', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.question.response.TransCategory'
    ],
    model: 'RODAdmin.model.studies.CBEditor.question.response.TransCategory',
    autoSync: true,
   
    proxy: {
        type: 'memory'
    }
});