/*
 * File: app/view/DataBrowserPanel.js
 *
 * This file was generated by Sencha Architect version 2.2.1.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('databrowser.view.DataBrowserPanel', {
    extend: 'Ext.panel.Panel',

    requires: [
        'databrowser.view.DetailGridPanelCls'
    ],

    frame: true,
    height: 471,
    width: 695,
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    closable: true,
    title: 'RODA - Data Browser',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'tabpanel',
                    dock: 'left',
                    id: 'NavigatorTabPanel',
                    width: 226,
                    animCollapse: false,
                    collapseDirection: 'left',
                    collapsible: true,
                    activeTab: 0,
                    items: [
                        {
                            xtype: 'panel',
                            id: 'CatalogsPanel',
                            autoScroll: true,
                            title: 'Cataloage',
                            tabConfig: {
                                xtype: 'tab',
                                id: 'CatalogsTabConfig'
                            },
                            items: [
                                {
                                    xtype: 'treepanel',
                                    height: 389,
                                    id: 'CatalogsTreePanel',
                                    store: 'CatalogTreeStore',
                                    displayField: 'name',
                                    viewConfig: {
                                        id: 'CatalogsTreeView',
                                        autoScroll: true,
                                        rootVisible: false
                                    },
                                    listeners: {
                                        select: {
                                            fn: me.onCatalogsTreePanelSelect,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            id: 'YearsPanel',
                            autoScroll: true,
                            title: 'Ani',
                            tabConfig: {
                                xtype: 'tab',
                                id: 'YearsTabConfig'
                            },
                            items: [
                                {
                                    xtype: 'treepanel',
                                    height: 389,
                                    id: 'YearsTreePanel',
                                    store: 'YearTreeStore',
                                    displayField: 'name',
                                    viewConfig: {
                                        id: 'YearsTreeView',
                                        autoScroll: true,
                                        rootVisible: false
                                    },
                                    listeners: {
                                        select: {
                                            fn: me.onYearsTreePanelSelect,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            id: 'UsersPanel',
                            width: 226,
                            autoScroll: true,
                            layout: {
                                type: 'fit'
                            },
                            title: 'Utilizatori',
                            tabConfig: {
                                xtype: 'tab',
                                id: 'UsersTabConfig'
                            },
                            items: [
                                {
                                    xtype: 'gridpanel',
                                    height: 373,
                                    id: 'UsersGridPanel',
                                    width: 247,
                                    autoScroll: true,
                                    title: 'users',
                                    store: 'UsersStore',
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'username',
                                            hideable: false,
                                            text: 'User'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'password',
                                            hideable: false,
                                            text: 'Password'
                                        }
                                    ],
                                    viewConfig: {
                                        id: 'UsersGridView'
                                    }
                                }
                            ]
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'detailgridpanelcls1',
                    height: 438,
                    width: 465
                }
            ]
        });

        me.callParent(arguments);
    },

    onCatalogsTreePanelSelect: function(rowmodel, record, index, eOpts) {
        var gridPanel = Ext.getCmp('DetailsGridPanel');

        if (record.get('leaf') !== true && record.get('name') != 'RODA') 
        {
            var studyYear = record.get('an');
            var studyTitle = record.get('name');
            var studyData = [{an: studyYear, name: studyTitle}];	

            gridPanel.store.loadData(studyData);
        }
        else
        {
            gridPanel.store.removeAll();
        }

    },

    onYearsTreePanelSelect: function(rowmodel, record, index, eOpts) {
        var gridPanel = Ext.getCmp('DetailsGridPanel');

        if (record.get('leaf') !== true && record.get('depth') > 1)
        {
            var data = record.childNodes;
            var year;
            var aut = "", descr = "";
            var loadedInfo = [];

            gridPanel.store.removeAll();

            if (data.length > 0)
            {
                for (var i = 0; i < data.length; i++)
                {
                    if (record.get('depth') == 2) 
                    {
                        // daca a fost selectat un an, in panel-ul cu detalii vor fi afisate
                        // cataloagele din anul respectiv	
                        year = record.get('name');
                    }
                    else 
                    {
                        // daca a fost selectat un catalog, in panel-ul cu detalii vor fi afisate
                        // studiile din catalogul respectiv	
                        if (record.get('depth') >= 3) 
                        {
                            year = Ext.data.Model(data[i]).get('an');
                            aut = Ext.data.Model(data[i]).get('author');
                            descr = Ext.data.Model(data[i]).get('description');
                        }
                    }
                    var title = Ext.data.Model(data[i]).get('name');
                    loadedInfo[i] = {an: year, name: title, author: aut, description: descr};
                }

                //gridPanel.store.loadData(loadedInfo);
                //gridPanel.store.add(loadedInfo);

                gridPanel.store.proxy.data = loadedInfo;
                gridPanel.store.load();

            }
        }
        else
        gridPanel.store.removeAll();

    }

});