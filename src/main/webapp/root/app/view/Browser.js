Ext.define('databrowser.view.Browser', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.browser',
//	id: 'browser',
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
	        	itemId: 'TreesPanel',
	        	width: '30%',
	        	resizable:true,
	        	activeTab: 0,
	        	items: [
	        	        {
	        	        	xtype: 'treepanel',
	        	        	autoScroll: true,
	        	        	itemId: 'CatalogsTreePanel',
	        	        	layout:'fit',
	        	        	store: 'CatalogTreeStore',
	        	        	displayField: 'text',
	        	        	title: translations.catalogs,
	        	        	viewConfig: {
	        	        		rootVisible: false
	        	        	},
	        	        },
	        	        {
	        	        	xtype: 'treepanel',
	        	        	itemId: 'YearsTreePanel',
	        	        	autoScroll: true,
	        	        	store: 'YearTreeStore',
	        	        	layout:'fit',                            
	        	        	title: translations.years,
	        	        	displayField: 'name',
	        	        	viewConfig: {
	        	        		rootVisible: false
	        	        	},

	        	        }
	        	        ,{
	        	        	xtype: 'treepanel',
	        	        	id: 'TopicTreePanel',
	        	        	autoScroll: true,
	        	        	store: 'TopicTreeStore',
	        	        	layout:'fit',                            
	        	        	title: translations.topics,
	        	        	displayField: 'translation',
	        	        	viewConfig: {
	        	        		rootVisible: true
	        	        	},

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

