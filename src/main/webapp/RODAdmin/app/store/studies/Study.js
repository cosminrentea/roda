/**
 * 
 */
Ext.define('RODAdmin.store.studies.Study', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.studies.Study'
    ],

    model: 'RODAdmin.model.studies.Study',
    autoload: true,    
    proxy: {type: 'main', url: 'studylist'},
});