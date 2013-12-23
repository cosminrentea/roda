/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.LayoutGroupContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
hideMode: 'display',
alias : 'widget.layoutgroupcontextmenu',
itemId: 'layoutgroupcontextmenu',
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
                itemId: 'addlayout',
                text: 'Add Layout',
                tooltip: 'Add a new layout in this group'
            },
            {
                xtype: 'menuitem',
                itemId: 'emptygroup',
                text: 'Empty Group',
                tooltip: 'Deletes all the layouts from this group'
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