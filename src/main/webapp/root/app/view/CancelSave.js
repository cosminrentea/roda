/**
 * 
 */
Ext.define('databrowser.view.CancelSave', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.cancelsave',
    flex: 1,
    dock: 'bottom',
    ui: 'footer',
    layout: {
        pack: 'end',
        type: 'hbox'
    },
    items: [
        {
            xtype: 'button',
            text: translations.cancel,
            itemId: 'cancel',
            iconCls: 'cancel'
        },
        {
            xtype: 'button',
            text: translations.ok,
            itemId: 'ok',
            iconCls: 'save'
        }
    ]
});