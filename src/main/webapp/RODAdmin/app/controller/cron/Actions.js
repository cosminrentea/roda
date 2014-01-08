/**
 * 
 */
Ext.define('RODAdmin.controller.cron.Actions', {
    extend: 'Ext.app.Controller',

    views: [
        'RODAdmin.view.cron.action.Actions',
        'RODAdmin.view.cron.action.Cronactionsview',
        'RODAdmin.view.cron.action.CronDetails',
        'RODAdmin.view.cron.action.details.ActionProperties',
        'RODAdmin.view.cron.action.details.ActionRuns',
    ]

});