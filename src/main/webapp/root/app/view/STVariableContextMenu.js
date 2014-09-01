/**
 * 
 */
Ext.define('databrowser.view.STVariableContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.stvariablecontextmenu',
hideMode: 'display',
itemId: 'stvariablecontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'addanal',
                text: 'Analyze',
                tooltip: 'Adds current variable to analysts'
            },
            {
                xtype: 'menuitem',
                itemId: 'vardetails',
                text: 'Get details',
                tooltip: 'Gets variable details'
            },
        ]
    });
    me.callParent(arguments);
}
});