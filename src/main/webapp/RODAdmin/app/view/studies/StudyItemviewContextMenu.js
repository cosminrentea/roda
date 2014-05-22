/**
 * Meniu contextual pentru {@link RODAdmin.view.studies.StudyItemsview StudyItemsviewgrid#sticonview} lista de studii 
 */
Ext.define('RODAdmin.view.studies.StudyItemviewContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.studyitemviewcontextmenu',
hideMode: 'display',
itemId: 'studyitemviewcontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'icdeletestudy',
                text: 'Delete Study',
                tooltip: 'Deletes the study from the database'
            },
            {
                xtype: 'menuitem',
                id: 'iceditstudy',
                text: 'Edit Study',
                tooltip: 'Allows modifications of the study'
            }
        ]
    });
    me.callParent(arguments);
}
});