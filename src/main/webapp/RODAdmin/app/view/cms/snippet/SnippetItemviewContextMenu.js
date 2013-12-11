Ext.define('RODAdmin.view.cms.snippet.SnippetItemviewContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.snippetitemviewcontextmenu',
hideMode: 'display',
itemId: 'snippetitemviewcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'icdeletesnippet',
                text: 'Delete Snippet',
                tooltip: 'Deletes the snippet from the database'
            },
            {
                xtype: 'menuitem',
                id: 'iceditsnippet',
                text: 'Edit Snippet',
                tooltip: 'Allows modifications of the snippet'
            }
        ]
    });
    me.callParent(arguments);
}
});