/**
 * 
 */
Ext.define('RODAdmin.view.studies.StudyContextMenu', {
extend: 'Ext.menu.Menu',
hidden: true,
alias : 'widget.studycontextmenu',
hideMode: 'display',
itemId: 'studycontextmenu',
width: 138,
frameHeader: false,
initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
        items: [
            {
                xtype: 'menuitem',
                itemId: 'deletestudy',
                text: 'Delete Study',
                tooltip: 'Deletes the study from the database'
            },
            {
                xtype: 'menuitem',
                itemId: 'editstudy',
                text: 'Edit Study',
                tooltip: 'Allows modifications of the study'
            }
        ]
    });
    me.callParent(arguments);
}
});