Ext.define('databrowser.view.DetailsPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.detailspanel',
    autoRender: true,
    id: 'DetailsPanel',
    itemId: 'DetailsPanel',
    width: '100%',
    header: false,
    hideHeaders: true,
    store: 'CatalogDetailStore',

    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            viewConfig: {
                getRowClass: function(record, rowIndex, rowParams, store) {
                    return 'x-hide-display';
                },
                frame: true,
                id: 'DetailsGridView',
                trackOver: false,
                stripeRows: false
                
             
            },
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    id: 'DataBrowserToolbar',
                    width:300,
                    items: [
                        {
                            xtype: 'textfield',
                            id: 'LocalSearchTextField',
                            width: 190,
                            emptyText: 'Cautare locala'
                        },
                        {
                            xtype: 'button',
                            id: 'AdvancedSearchButton',
                            width: 156,
                            text: 'Cautare avansata'
                        },
                        {
                            xtype: 'tbseparator',
                            id: 'DataBrowserToolbarSeparator1'
                        },
                        {
                            xtype: 'tbfill',
                        },
                        {
                            xtype: 'buttongroup',
                            autoRender: false,
                            id: 'SMCButtonGroup',
                            //width: 72,
                            header: false,
                            frame:false,
                            title: 'Buttons',
                            columns: 3,
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'SButton',
                                    enableToggle: true,
//                                    text: 'S',
                                    icon: 'img/simple.png',
                                    toggleGroup: 'SMCButtonGroup',
                                    listeners: {
                                        click: {
                                            fn: me.onSButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    id: 'MButton',
                                    enableToggle: true,
                                    icon: 'img/mediu.png',
                                    toggleGroup: 'SMCButtonGroup',
                                    listeners: {
                                        click: {
                                            fn: me.onMButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    id: 'CButton',
                                    enableToggle: true,
                                    icon: 'img/complex.png',
                                    toggleGroup: 'SMCButtonGroup',
                                    listeners: {
                                        click: {
                                            fn: me.onCButtonClick,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                },
            ],
        });
//        me.callParent(arguments);
    },
});