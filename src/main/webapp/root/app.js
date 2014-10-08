//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true,
    paths: {
        'Ext.ux': 'ux/',
     	 'Ext.ux.plugins': '/roda/resources/root/ux/plugins',
     	'databrowser.util' : 'app/util'
    }
});

Ext.application({

    requires: [
        'Ext.grid.*',
        'Ext.data.*',
        'Ext.util.*',
        'Ext.toolbar.Paging',
        'Ext.ModelManager',
        'Ext.ux.plugins.FitToParent',
    ],
    models: [
        'CatalogsTreeModel',
        'CatalogsModel',
        'YearsTreeModel',
        'StudyModel',
        'SeriesModel',
        'VariableModel',
        'StudyFileModel',
        'YearModel',
        'FrequencyModel',
        'TopicTreeModel'
     ],
    stores: [
        'StudyStore',
        'SeriesStore',
        'CatalogStore',
        'CatalogTreeStore',
        'YearTreeStore',
        'YearStore',
        'VariableStore',
        'TopicTreeStore',
        'MemoryVariableStore',
        'SrMemoryVariableStore'
    ],
    views: [
        'DataBrowserPanel',
        'CatalogView',
        'YearView',
        'DBCard',
        'Browser',
        'DetailsPanel',
        'StudyView',
        'SeriesView',
        'YearView',
        'StudySeriesView',
        'SeriesMembersView',
        'VariableView',
        'FrequencyChart',
        'Histogram',
        'VariableDetails',
        'StackedBarChart',
        'Alert',
        'ScatterChart'
    ],
	controllers : ['VariableView', 'DataBrowser', 'Browser', 'CatalogView', 'YearView'],
    autoCreateViewport: false,
    name: 'databrowser',
    appFolder:'/roda/root/app',
    init : function() {  	
    	console.log('init');
    	Ext.Ajax.timeout = 200000; // 200 seconds 
	    Ext.override(Ext.form.Basic, {     timeout: Ext.Ajax.timeout / 1000 });
	    Ext.override(Ext.data.proxy.Server, {     timeout: Ext.Ajax.timeout });
	    Ext.override(Ext.data.Connection, {     timeout: Ext.Ajax.timeout });
	    Ext.create('databrowser.view.DataBrowserPanel',{
	         renderTo: 'dbcontainer',
	         plugins : ['fittoparent'],
	     })
  },
    
    
    
    
    
});
