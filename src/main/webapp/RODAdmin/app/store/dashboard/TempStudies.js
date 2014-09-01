/**
 * 
 */
Ext.define('RODAdmin.store.dashboard.TempStudies', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.dashboard.TempStudies',
    ],

    model: 'RODAdmin.model.dashboard.TempStudies',

    autoLoad: true,
    proxy: {type: 'main', url: 'http://roda.apiary.io/dashboard/tempstudies'},    
});