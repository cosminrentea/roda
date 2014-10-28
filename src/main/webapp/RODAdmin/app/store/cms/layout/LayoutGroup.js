/**
 * 
 */
Ext.define('RODAdmin.store.cms.layout.LayoutGroup', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.layout.LayoutGroup',
    ],

    model: 'RODAdmin.model.cms.layout.LayoutGroup',
    
    autoload: true,
    proxy: {type: 'main', url: 'adminjson/cmslayoutgroupinfo'},    
});