Ext.define('databrowser.view.DataBrowserPanel', {
    extend: 'Ext.panel.Panel',
    frame: true,
    height: 671,
    width: 900,
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    closable: false,
    header:false,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'tabpanel',
                    dock: 'left',
                    id: 'NavigatorTabPanel',
                    width: 400,
                    animCollapse: false,
                    collapseDirection: 'left',
                    collapsible: true,
                    resizable:true,
                    activeTab: 0,
                    items: [
                        {
                            xtype: 'panel',
                            id: 'CatalogsPanel',
                            autoScroll: true,
                            layout:'fit',
                            title: 'Cataloage',
                            tabConfig: {
                                xtype: 'tab',
                                id: 'CatalogsTabConfig'
                            },
                            items: [
                                {
                                    xtype: 'treepanel',
                                    id: 'CatalogsTreePanel',
                                    store: 'CatalogTreeStore',
                                    displayField: 'text',
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
                            layout:'fit',                            
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
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'dbcard',
                }
            ]
        });

        me.callParent(arguments);
    },

    onCatalogsTreePanelSelect: function(rowmodel, record, index, eOpts) {
        var dbcard = Ext.getCmp('dbcard');
        if (record.get('type') == 'C') {
        	dbcard.layout.setActiveItem('catalogview');
        	var catalogview = Ext.getCmp('catalogview');
        	catalogview.setTitle(record.get('text'));
        	catalogview.catalogid = record.get('indice');
        	var cStore = Ext.StoreManager.get('CatalogStore');    
       	    var sButton = Ext.getCmp('SButton');
       	    sButton.toggle(true);
       	    console.log(record.get('indice'));
        	catalogview.loaddata(record.get('indice'));
        } else if (record.get('type') == 'S') {
        	dbcard.layout.setActiveItem('seriesview');	        	
        	var seriesviewob = Ext.getCmp('seriesview');
        	seriesviewob.setTitle(record.get('text'));
       	    seriesviewob.loaddata(record.get('indice'));
        } else if (record.get('type') == 'St') {
        	dbcard.layout.setActiveItem('studyview');	
        	var studyviewob = Ext.getCmp('studyview');
        	studyviewob.setTitle(record.get('text'));
        	studyviewob.loaddata(record.get('indice'));
        } else if (record.get('type') == 'Sts') {
        	dbcard.layout.setActiveItem('studyseriesview');	
        	var studyseriesviewob = Ext.getCmp('studyseriesview');
        	studyseriesviewob.setTitle(record.get('text'));
        	studyseriesviewob.loaddata(record.get('indice'));
        } else {
        	dbcard.layout.setActiveItem('initial');
        }
    },

    onYearsTreePanelSelect: function(rowmodel, record, index, eOpts) {
        var dbcard = Ext.getCmp('dbcard');
        if (record.get('type') == 'Y') {
        	dbcard.layout.setActiveItem('yearview');
        	var yearview = Ext.getCmp('yearview');
        	yearview.setTitle(record.get('year'));
        	var cStore = Ext.StoreManager.get('YearStore');    
       	    var sButton = Ext.getCmp('SButton');
       	    sButton.toggle(true);
        	yearview.loaddata(record.get('year'));
        } else if (record.get('type') == 'St') {
        	dbcard.layout.setActiveItem('studyview');	
        	var studyviewob = Ext.getCmp('studyview');
        	studyviewob.setTitle(record.get('text'));
        	studyviewob.loaddata(record.get('indice'));
        } else if (record.get('type') == 'Sts') {
        	dbcard.layout.setActiveItem('studyseriesview');	
        	var studyseriesviewob = Ext.getCmp('studyseriesview');
        	studyseriesviewob.setTitle(record.get('text'));
        	studyseriesviewob.loaddata(record.get('indice'));
        } else {
        	dbcard.layout.setActiveItem('initial');
        }
    }
});