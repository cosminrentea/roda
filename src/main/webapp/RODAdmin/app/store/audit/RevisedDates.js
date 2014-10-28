/**
 * 
 */
Ext.define('RODAdmin.store.audit.RevisedDates', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.audit.RevisedDates'
    ],

    model: 'RODAdmin.model.audit.RevisedDates',
    autoLoad: false,    
    proxy: {type: 'main', url: 'adminjson/revised-dates'},  
});