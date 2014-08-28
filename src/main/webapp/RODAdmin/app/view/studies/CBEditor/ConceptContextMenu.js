/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.ConceptContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.conceptcontextmenu',
hideMode: 'display',
itemId: 'conceptcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'delete',
                text: 'Delete',
                tooltip: 'Deletes current concept and all children'
            },
            {
                xtype: 'menuitem',
                itemId: 'addconcept',
                text: 'Add Concept',
                tooltip: 'Adds a concept'
            },
        ]
    });
    me.callParent(arguments);
}
});