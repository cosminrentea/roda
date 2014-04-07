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
                text: 'Delete Tree',
                tooltip: 'Deletes the current page'
            },
            {
                xtype: 'menuitem',
                itemId: 'editpage',
                text: 'Edit Page',
                tooltip: 'Allows modifications of the page'
            },
            {
                xtype: 'menuitem',
                itemId: 'addpage',
                text: 'Add Page',
                tooltip: 'Adds a page'
            }

        ]
    });
    me.callParent(arguments);
}
});