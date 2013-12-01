Ext.define('RODAdmin.controller.cron.Actions', {
    extend: 'Ext.app.Controller',

    views: [
        'cron.action.Actions',
        'cron.action.Cronactionsview',
        'cron.action.CronDetails',
        'cron.action.details.ActionProperties',
        'cron.action.details.ActionRuns',
    ]

});