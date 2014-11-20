/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.PageContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.pagecontextmenu',
hideMode: 'display',
itemId: 'pagecontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deletepage',
                text: translations.pg_deletetree,
                tooltip: 'Deletes the current page'
            },
            {
                xtype: 'menuitem',
                itemId: 'editpage',
                text: translations.pg_editpage,
                tooltip: 'Allows modifications of the page'
            },
            {
                xtype: 'menuitem',
                itemId: 'addpage',
                text: translations.pg_addpage,
                tooltip: 'Adds a page'
            },
            {
                xtype: 'menuitem',
                itemId: 'clearcache',
                text: translations.pg_clearcache,
                tooltip: 'Clears page cache'
            }


        ]
    });
    me.callParent(arguments);
}
});