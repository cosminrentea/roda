/**
 * 
 */
Ext.define('RODAdmin.store.studies.StudyItem', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.studies.StudyItem',
        'RODAdmin.model.studies.StudyVariable', 
        'RODAdmin.model.studies.StudyKeywords' 
    ],

    model: 'RODAdmin.model.studies.StudyItem',

    autoLoad: false,
    proxy: {type: 'main', url: '/roda/j/studyinfo'},    
});