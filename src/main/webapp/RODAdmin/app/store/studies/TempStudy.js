/**
 * 
 */
Ext.define('RODAdmin.store.studies.TempStudy', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.studies.TempStudy'
    ],

    model: 'RODAdmin.model.studies.TempStudy',
    autoload: true,    
    proxy: {type: 'main', url: '/roda/adminjson/cmsfilelist/json'},
});