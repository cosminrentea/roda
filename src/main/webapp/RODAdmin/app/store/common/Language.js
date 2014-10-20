/**
 * 
 */
Ext.define('RODAdmin.store.common.Language', {
    extend: 'RODAdmin.store.Base',	

    requires: [
        'RODAdmin.model.common.Language',
    ],

    model: 'RODAdmin.model.common.Language',
    proxy: {type: 'main', url: 'userjson/languagelist'},      
    autoload: true,
});