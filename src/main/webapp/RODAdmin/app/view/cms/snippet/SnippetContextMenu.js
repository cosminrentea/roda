Ext.define('RODAdmin.view.cms.snippet.SnippetContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.snippetcontextmenu',
hideMode: 'display',
itemId: 'snippetcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deletesnippet',
                text: 'Delete Snippet',
                tooltip: 'Deletes the snippet from the database'
            },
            {
                xtype: 'menuitem',
                itemId: 'editsnippet',
                text: 'Edit Snippet',
                tooltip: 'Allows modifications of the snippet'
            }
        ]
    });
    me.callParent(arguments);
}
});