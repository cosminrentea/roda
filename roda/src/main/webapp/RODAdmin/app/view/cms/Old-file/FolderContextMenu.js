Ext.define('RODAdmin.view.cms.file.FolderContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
hideMode: 'display',
alias : 'widget.foldercontextmenu',
itemId: 'foldercontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                id: 'NewFolder',
                text: 'New Folder',
                tooltip: 'Adds a new folder underneath this folder'
            },
            {
                xtype: 'menuitem',
                id: 'AddFile',
                text: 'Add File',
                tooltip: 'Add a new file in this folder'
            },
            {
                xtype: 'menuitem',
                id: 'EmptyFolder',
                text: 'Empty Folder',
                tooltip: 'Deletes all the files from this folder'
            },
            {
                xtype: 'menuitem',
                id: 'DeleteFolder',
                text: 'Delete Folder',
                tooltip: 'Deletes the folder and all it\'s content'
           	}
        ]
    });
    me.callParent(arguments);
}
});