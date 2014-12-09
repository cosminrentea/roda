/**
 * 
 */
Ext.define('RODAdmin.view.user.GroupContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.groupcontextmenu',
hideMode: 'display',
itemId: 'groupcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deletegroup',
                text: 'Delete group',
                tooltip: 'Deletes the current group'
            },
            {
                xtype: 'menuitem',
                itemId: 'editgroup',
                text: 'Edit Group',
                tooltip: 'Allows modifications of the group'
            },
            {
                xtype: 'menuitem',
                itemId: 'adduser',
                text: 'Add user to group',
                tooltip: 'Add a new user to selected group'
            },
        ]
    });
    me.callParent(arguments);
}
});