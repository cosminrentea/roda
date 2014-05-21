/**
 Meniu contextual pentru {@link RODAdmin.controller.cms.news.NewsList}
 */
Ext.define('RODAdmin.view.cms.news.NewsItemviewContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.newsitemviewcontextmenu',
hideMode: 'display',
itemId: 'newsitemviewcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'icdeletenews',
                text: 'Delete Newsitem',
                tooltip: 'Deletes the news item from the database'
            },
            {
                xtype: 'menuitem',
                id: 'iceditnews',
                text: 'Edit Newsitem',
                tooltip: 'Allows modifications of the news item'
            }
        ]
    });
    me.callParent(arguments);
}
});