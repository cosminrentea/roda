/**
 * 
 */
Ext.define('RODAdmin.view.MainPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.mainpanel',

    requires: ['RODAdmin.view.dashboard.Dashboard'],
    
    activeTab: 0,

    items: [
        {
        	xtype: 'dashboard',
            closable: false,
            iconCls: 'home',
            title: 'Home',
            layout: 'fit'
        }
    ]
});