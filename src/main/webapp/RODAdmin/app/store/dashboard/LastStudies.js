/**
 * 
 */
Ext.define('RODAdmin.store.dashboard.LastStudies', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.dashboard.LastStudies',
    ],

    model: 'RODAdmin.model.dashboard.LastStudies',

    autoLoad: true,
    proxy: {type: 'main', url: '/studyinfo'},    
});