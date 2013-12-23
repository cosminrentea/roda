/**
 * 
 */
Ext.define('RODAdmin.store.cron.ExecutionList', {
    extend: 'RODAdmin.store.Base',

    requires: [
        'RODAdmin.model.cron.action.Execution',
    ],

    model: 'RODAdmin.model.cron.action.Execution',
    proxy: {type: 'main', url: 'http://roda.apiary.io/admin/scheduler/executionsbytask/'},    
    autoLoad: false,

});