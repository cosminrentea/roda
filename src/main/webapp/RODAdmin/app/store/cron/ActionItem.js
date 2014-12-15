/**
 * 
 */
Ext.define('RODAdmin.store.cron.ActionItem', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cron.action.Action',
    ],

    model: 'RODAdmin.model.cron.action.Action',
    
    autoLoad: false,
    proxy: {type: 'main', url: 'adminjson/scheduler/tasks/'},     
//    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/scheduler/tasks/'},     

});