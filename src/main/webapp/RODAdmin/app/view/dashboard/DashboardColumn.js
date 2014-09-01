Ext.define('RODAdmin.view.dashboard.DashboardColumn', {
    extend: 'Ext.container.Container',
    alias: 'widget.dashboardcolumn',

    requires: ['RODAdmin.view.dashboard.Portlet'],

    layout: 'anchor',
    defaultType: 'portlet',
    cls: 'x-dashboard-column'

    // This is a class so that it could be easily extended
    // if necessary to provide additional behavior.
});
