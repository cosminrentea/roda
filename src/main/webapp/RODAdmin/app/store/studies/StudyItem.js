/**
 * 
 */
Ext.define('RODAdmin.store.studies.StudyItem', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.studies.StudyItem',
        'RODAdmin.model.studies.StudyVariable' 
    ],

    model: 'RODAdmin.model.studies.StudyItem',

    autoLoad: false,
    proxy: {type: 'main', url: 'studyinfo'},    
});