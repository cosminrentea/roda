/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.LayoutContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.layoutcontextmenu',
hideMode: 'display',
itemId: 'layoutcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deletelayout',
                text: 'Delete Layout',
                tooltip: 'Deletes the layout from the database and switches all the pages using it to base layout'
            },
            {
                xtype: 'menuitem',
                itemId: 'editlayout',
                text: 'Edit Layout',
                tooltip: 'Allows modifications of the layout'
            }
        ]
    });
    me.callParent(arguments);
}
});