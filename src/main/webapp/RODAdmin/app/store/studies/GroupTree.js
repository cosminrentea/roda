/**
 * 
 */
Ext.define('RODAdmin.store.studies.GroupTree', {
    extend: 'RODAdmin.store.BaseTree',
    
    requires: [
        'RODAdmin.model.studies.Study'
    ],

    model: 'RODAdmin.model.studies.Study',
    proxy: {type: 'mainajax', url: 'catalogtree'},   

});