/**
 * 
 */
Ext.define('RODAdmin.view.toolbar.CloselTb', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.closetb',

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
            text: 'Close',
            itemId: 'closewin',
            iconCls: 'close'
        }
    ]
});