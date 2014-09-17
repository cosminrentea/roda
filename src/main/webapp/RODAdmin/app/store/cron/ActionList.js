/**
 * 
 */
Ext.define('RODAdmin.store.cron.ActionList', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cron.action.Action',
    ],

    model: 'RODAdmin.model.cron.action.Action',
    autoLoad: true,
    proxy: {type: 'main', url: 'scheduler/tasks'},      
//    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/scheduler/tasks'},      

});
