/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.PageContextMenuDisabled', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.pagecontextmenudisabled',
hideMode: 'display',
itemId: 'pagecontextmenudisabled',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deletepage',
                disabled: true,
                text: 'Delete Tree',
                tooltip: 'Deletes the current page'
            },
            {
                xtype: 'menuitem',
                itemId: 'editpage',
                text: 'Edit Page',
                tooltip: 'Allows modifications of the page'
            }
        ]
    });
    me.callParent(arguments);
}
});