/**
 * 
 */
Ext.define('RODAdmin.store.cms.layout.Layout', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.layout.Layout'
    ],

    model: 'RODAdmin.model.cms.layout.Layout',
    autoload: true,    
    proxy: {type: 'main', url: 'adminjson/cmslayoutlist'},
});