/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.FileContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.filecontextmenu',
hideMode: 'display',
itemId: 'filecontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                id: 'deletefile',
                text: translations.fl_deletefile,
                tooltip: 'Deletes the file from the database and from the filesystem'
            },
            {
                xtype: 'menuitem',
                id: 'EditFile',
                text: translations.fl_editfile,
                tooltip: 'Allows modifications of the file properties and of the file itself'
            }
        ]
    });
    me.callParent(arguments);
}
});