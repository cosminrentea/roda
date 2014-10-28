/**
 * 
 */
Ext.define('RODAdmin.store.cms.layout.LayoutItem', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cms.layout.LayoutItem',
        'RODAdmin.model.cms.layout.LayoutUsage' 
    ],

    model: 'RODAdmin.model.cms.layout.LayoutItem',

    autoLoad: false,
    proxy: {type: 'main', url: 'adminjson/cmslayoutinfo'},    
});