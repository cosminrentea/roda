/**
 * 
 */
Ext.define('RODAdmin.view.UserViewport', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.userviewport',

    requires: [
        'RODAdmin.view.Header',
        'RODAdmin.view.menu.Accordion',
        'RODAdmin.view.MainPanel'
    ],

    layout: {
        type: 'border'
    },

    items: [
        {
            xtype: 'mainmenu',
            width: 185,
            collapsible: true,
            region: 'west'//,
            //style: 'background-color: #8FB488;'
        },
        {
            xtype: 'appheader',
            region: 'north'
        },
        {
            xtype: 'mainuserpanel',
            region: 'center'
        },
        {
            xtype: 'container',
            region: 'south',
            height: 30,
            style: 'border-top: 1px solid #4c72a4;',
            html: '<div id="titleHeader"><center><span style="font-size:10px;">RODA Admin</span></center></div>'
        }
    ]
});