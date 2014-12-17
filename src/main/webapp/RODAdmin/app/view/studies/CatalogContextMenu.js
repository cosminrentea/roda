/**
 * 
 */
Ext.define('RODAdmin.view.studies.CatalogContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
hideMode: 'display',
alias : 'widget.catalogcontextmenu',
itemId: 'catalogcontextmenu',
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
                itemId: 'addstudy',
                text: 'Add Study',
                tooltip: 'Add a new study in this group'
            },
            {
                xtype: 'menuitem',
                itemId: 'importstudy',
                text: 'Import Study',
                tooltip: 'Import a new study in this group'
            },            
            {
                xtype: 'menuitem',
                itemId: 'emptygroup',
                text: 'Empty Group',
                tooltip: 'Deletes all the studies from this group'
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