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

    autoload: true,
    proxy: {type: 'main', url: 'cmslayoutinfo'},    
});