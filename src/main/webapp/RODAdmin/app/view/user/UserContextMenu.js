/**
 * 
 */
Ext.define('RODAdmin.view.user.UserContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.usercontextmenu',
hideMode: 'display',
itemId: 'usercontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deleteuser',
                text: 'Delete user',
                tooltip: 'Deletes the current user'
            },
            {
                xtype: 'menuitem',
                itemId: 'edituser',
                text: 'Edit User',
                tooltip: 'Allows modifications of the user'
            },
            {
                xtype: 'menuitem',
                itemId: 'usermessage',
                text: 'Send Message',
                tooltip: 'Sends a message to the user'
            },


        ]
    });
    me.callParent(arguments);
}
});