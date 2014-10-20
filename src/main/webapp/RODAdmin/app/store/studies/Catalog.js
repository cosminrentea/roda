/**
 * 
 */
Ext.define('RODAdmin.store.studies.Catalog', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.studies.Catalog',
    ],

    model: 'RODAdmin.model.studies.Catalog',
    
    autoload: true,
    proxy: {type: 'main', url: 'studiesbycatalog'},    
});