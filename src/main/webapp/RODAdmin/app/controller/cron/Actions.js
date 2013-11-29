Ext.define('RODAdmin.controller.cron.Actions', {
    extend: 'Ext.app.Controller',

    views: [
        'cron.actions.Actions',
        'cron.actions.Cronactionsview',
        'cron.actions.CronDetails',
        'cron.actions.details.ActionProperties',
        'cron.actions.details.ActionRuns',
    ]

});