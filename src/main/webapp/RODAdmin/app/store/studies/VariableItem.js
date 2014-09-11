/**
 * 
 */
Ext.define('RODAdmin.store.studies.VariableItem', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.studies.StudyVariable', 
        'RODAdmin.model.studies.variable.response.Category',
        'RODAdmin.model.studies.variable.response.Code',
        'RODAdmin.model.studies.variable.response.Numeric',
        'RODAdmin.model.studies.variable.Missing'
    ],

    model: 'RODAdmin.model.studies.StudyVariable',

    autoLoad: true,
    proxy: {type: 'main', url: 'http://roda.apiary-mock.com/admin/varinfo/13'},    
});