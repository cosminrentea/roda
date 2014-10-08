/**
 * 
 */
Ext.define('RODAdmin.store.studies.StudyTree', {
    extend: 'RODAdmin.store.BaseTree',

    requires: [
        'RODAdmin.model.studies.Study'
    ],
    autoLoad: false,
    model: 'RODAdmin.model.studies.Study',
    proxy: {type: 'mainajax', url: '/roda/studiestree'},    

   folderSort: true,    

});