/**
 * 
 */
Ext.define('RODAdmin.store.studies.CBEditor.Qsamplingresp', {
    extend: 'Ext.data.Store',

    requires: [
        'RODAdmin.model.studies.CBEditor.PersOrg'
    ],
    model: 'RODAdmin.model.studies.CBEditor.PersOrg',
    autoSync: true,
    
    proxy: {
        type: 'memory'
    }
});