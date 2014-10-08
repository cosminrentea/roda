Ext.define('databrowser.view.Browser', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.browser',
	id: 'browser',
	itemId: 'browser',
	activeItem: 0,
	width: '100%',
	
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
        	items: [
	        {
	        	xtype: 'tabpanel',
	        	region:'west',
	        	width: '30%',
	        	resizable:true,
	        	activeTab: 0,
	        	items: [
	        	        {
	        	        	xtype: 'panel',
	        	        	id: 'CatalogsPanel',
	        	        	autoScroll: true,
	        	        	layout:'fit',
	        	        	title: translations.catalogs,
	        	        	tabConfig: {
	        	        		xtype: 'tab',
	        	        		id: 'CatalogsTabConfig'
	        	        	},
	        	        	items: [
	        	        	        {
	        	        	        	xtype: 'treepanel',
	        	        	        	itemId: 'CatalogsTreePanel',
	        	        	        	store: 'CatalogTreeStore',
	        	        	        	displayField: 'text',
	        	        	        	viewConfig: {
	        	        	        		id: 'CatalogsTreeView',
	        	        	        		autoScroll: true,
	        	        	        		rootVisible: false
	        	        	        	},
//	        	        	        	listeners: {
//	        	        	        		select: {
//	        	        	        			fn: me.onCatalogsTreePanelSelect,
//	        	        	        			scope: me
//	        	        	        		}
//	        	        	        	}
	        	        	        }
	        	        	        ]
	        	        },
	        	        {
	        	        	xtype: 'panel',
	        	        	id: 'YearsPanel',
	        	        	autoScroll: true,
	        	        	layout:'fit',                            
	        	        	title: translations.years,
	        	        	tabConfig: {
	        	        		xtype: 'tab',
	        	        		id: 'YearsTabConfig'
	        	        	},
	        	        	items: [
	        	        	        {
	        	        	        	xtype: 'treepanel',
	        	        	        	height: 389,
	        	        	        	itemId: 'YearsTreePanel',
	        	        	        	store: 'YearTreeStore',
	        	        	        	displayField: 'name',
	        	        	        	viewConfig: {
	        	        	        		id: 'YearsTreeView',
	        	        	        		autoScroll: true,
	        	        	        		rootVisible: false
	        	        	        	},
//	        	        	        	listeners: {
//	        	        	        		select: {
//	        	        	        			fn: me.onYearsTreePanelSelect,
//	        	        	        			scope: me
//	        	        	        		}
//	        	        	        	}
	        	        	        }
	        	        	        ]
	        	        }
	        	        ,{
	        	        	xtype: 'panel',
	        	        	id: 'TopicPanel',
	        	        	autoScroll: true,
	        	        	layout:'fit',                            
	        	        	title: translations.topics,
	        	        	tabConfig: {
	        	        		xtype: 'tab',
	        	        		id: 'TopicTabConfig'
	        	        	},
	        	        	items: [
	        	        	        {
	        	        	        	xtype: 'treepanel',
	        	        	        	height: 389,
	        	        	        	id: 'TopicTreePanel',
	        	        	        	store: 'TopicTreeStore',
	        	        	        	displayField: 'name',
	        	        	        	viewConfig: {
	        	        	        		id: 'TopicTreeView',
	        	        	        		autoScroll: true,
	        	        	        		rootVisible: false
	        	        	        	},
	        	        	        	listeners: {
	        	        	        		select: {
	        	        	        			fn: me.onTopicTreePanelSelect,
	        	        	        			scope: me
	        	        	        		}
	        	        	        	}
	        	        	        }
	        	        	        ]
	        	        }
	        	        ]
	        }
	        ]
     });
       me.callParent(arguments);
    },
    
    
//    onCatalogsTreePanelSelect: function(rowmodel, record, index, eOpts) {
//    	var dbcard = Ext.getCmp('dbcard');
//    	if (record.get('type') == 'C') {
//        	dbcard.layout.setActiveItem('catalogview');
//        	var catalogview = Ext.getCmp('catalogview');
//        	catalogview.setTitle(record.get('text'));
//        	catalogview.catalogid = record.get('indice');
//        	var cStore = Ext.StoreManager.get('CatalogStore');    
//       	    var sButton = Ext.getCmp('SButton');
//       	    sButton.toggle(true);
//       	    console.log(record.get('indice'));
//        	catalogview.loaddata(record.get('indice'));
//        } else if (record.get('type') == 'S') {
//        	dbcard.layout.setActiveItem('seriesview');	        	
//        	var seriesviewob = Ext.getCmp('seriesview');
//        	seriesviewob.setTitle(record.get('text'));
//       	    seriesviewob.loaddata(record.get('indice'));
//        } else if (record.get('type') == 'St') {
//        	dbcard.layout.setActiveItem('studyview');	
//        	var studyviewob = Ext.getCmp('studyview');
//        	studyviewob.setTitle(record.get('text'));
//        	studyviewob.loaddata(record.get('indice'));
//        } else if (record.get('type') == 'Sts') {
//        	dbcard.layout.setActiveItem('studyseriesview');	
//        	var studyseriesviewob = Ext.getCmp('studyseriesview');
//        	studyseriesviewob.setTitle(record.get('text'));
//        	studyseriesviewob.loaddata(record.get('indice'));
//        } else {
//        	dbcard.layout.setActiveItem('initial');
//        }
//  },

//    onYearsTreePanelSelect: function(rowmodel, record, index, eOpts) {
//        var dbcard = Ext.getCmp('dbcard');
//        if (record.get('type') == 'Y') {
//        	dbcard.layout.setActiveItem('yearview');
//        	var yearview = Ext.getCmp('yearview');
//        	yearview.setTitle(record.get('year'));
//        	var cStore = Ext.StoreManager.get('YearStore');    
//       	    var sButton = Ext.getCmp('SButton');
//       	    sButton.toggle(true);
//        	yearview.loaddata(record.get('year'));
//        } else if (record.get('type') == 'St') {
//        	dbcard.layout.setActiveItem('studyview');	
//        	var studyviewob = Ext.getCmp('studyview');
//        	studyviewob.setTitle(record.get('text'));
//        	studyviewob.loaddata(record.get('indice'));
//        } else if (record.get('type') == 'Sts') {
//        	dbcard.layout.setActiveItem('studyseriesview');	
//        	var studyseriesviewob = Ext.getCmp('studyseriesview');
//        	studyseriesviewob.setTitle(record.get('text'));
//        	studyseriesviewob.loaddata(record.get('indice'));
//        } else {
//        	dbcard.layout.setActiveItem('initial');
//        }
//    },
    
    onTopicTreePanelSelect: function(rowmodel, record, index, eOpts) {

    }
    
    
    
    
});

