Ext.define('RODAdmin.view.cms.layout.LayoutItemviewContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.layoutitemviewcontextmenu',
hideMode: 'display',
itemId: 'layoutitemviewcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'icdeletelayout',
                text: 'Delete Layout',
                tooltip: 'Deletes the layout from the database switch all pages using it to base layout'
            },
            {
                xtype: 'menuitem',
                id: 'iceditlayout',
                text: 'Edit Layout',
                tooltip: 'ic Allows modifications of the layout'
            }
        ]
    });
    me.callParent(arguments);
}
});