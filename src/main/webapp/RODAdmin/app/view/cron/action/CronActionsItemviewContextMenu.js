/**
 * 
 */
Ext.define('RODAdmin.view.cron.action.CronActionsItemviewContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.cronactionsitemviewcontextmenu',
hideMode: 'display',
itemId: 'cronactionsitemviewcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deletecronaction',
                text: 'Delete Action',
                tooltip: 'Deletes the action and all executions from the database'
            },
            {
                xtype: 'menuitem',
                id: 'editcronaction',
                text: 'Edit Action',
                tooltip: 'Allows modifications of the action'
            }
        ]
    });
    me.callParent(arguments);
}
});