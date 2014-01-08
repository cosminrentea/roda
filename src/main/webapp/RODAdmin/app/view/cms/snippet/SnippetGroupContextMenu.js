/**
 * 
 */
Ext.define('RODAdmin.view.cms.snippet.SnippetGroupContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
hideMode: 'display',
alias : 'widget.snippetgroupcontextmenu',
itemId: 'snippetgroupcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'newgroup',
                text: 'New Group',
                tooltip: 'Adds a new group underneath this one'
            },
            {
                xtype: 'menuitem',
                itemId: 'addsnippet',
                text: 'Add Snippet',
                tooltip: 'Add a new snippet in this group'
            },
            {
                xtype: 'menuitem',
                itemId: 'emptygroup',
                text: 'Empty Group',
                tooltip: 'Deletes all the snippets from this group'
            },
            {
                xtype: 'menuitem',
                itemId: 'deletegroup',
                text: 'Delete Group',
                tooltip: 'Deletes the group and all it\'s content'
           	}
        ]
    });
    me.callParent(arguments);
}
});