Ext.define('RODAdmin.view.cms.files.IconviewContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.iconviewcontextmenu',
hideMode: 'display',
itemId: 'iconviewcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                id: 'icdeletefile',
                text: 'Delete File',
                tooltip: 'ic Deletes the file from the database and from the filesystem'
            },
            {
                xtype: 'menuitem',
                id: 'icEditFile',
                text: 'Edit File',
                tooltip: 'ic Allows modifications of the file properties and of the file itself'
            }
        ]
    });
    me.callParent(arguments);
}
});