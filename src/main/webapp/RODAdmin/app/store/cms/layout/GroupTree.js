/**
 * 
 */
Ext.define('RODAdmin.store.cms.layout.GroupTree', {
    extend: 'RODAdmin.store.BaseTree',
    
    requires: [
        'RODAdmin.model.cms.layout.Layout'
    ],

    model: 'RODAdmin.model.cms.layout.Layout',
    proxy: {type: 'mainajax', url: 'cmslayoutgrouptree'},   

});