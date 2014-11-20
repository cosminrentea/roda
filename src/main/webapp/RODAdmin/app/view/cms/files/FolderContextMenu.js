/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.FolderContextMenu', {
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
                text: translations.fl_newfolder,
                tooltip: translations.fl_addfoldertooltip
            },
            {
                xtype: 'menuitem',
                id: 'AddFile',
                text: translations.fl_addfile,
                tooltip: translations.fl_addfiletootltip
            },
            {
                xtype: 'menuitem',
                id: 'EmptyFolder',
                text: translations.fl_emptyfolder,
                tooltip: translations.fl_emptyfoldertooltip
            },
            {
                xtype: 'menuitem',
                id: 'DeleteFolder',
                text: translations.fl_deletefolder,
                tooltip: translations.fl_delfoldertooltip
           	}
        ]
    });
    me.callParent(arguments);
}
});