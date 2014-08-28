/**
 * 
 */
Ext.define('databrowser.view.VariableContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.variablecontextmenu',
hideMode: 'display',
itemId: 'variablecontextmenu',
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