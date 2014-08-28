//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true,
    paths: {
        'Ext.ux': 'ux/',
     	 'Ext.ux.plugins': '/roda/resources/root/ux/plugins'
    }
});
//Ext.Loader.setPath('Ext.ux.plugins.FitToParent', '.');
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
        'MemoryVariableStore'
    ],
    views: [
        'DataBrowserPanel',
        'CatalogView',
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
        'VariableDetails'
    ],
	controllers : ['VariableView', 'DataBrowser'],
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
