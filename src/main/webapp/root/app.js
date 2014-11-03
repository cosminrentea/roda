//@require @packageOverrides
//Ext.Loader.setConfig({
//    enabled: true,
//    paths: {
//        'Ext.ux': 'ux/',
//     	 'Ext.ux.plugins': '/resources/root/ux/plugins',
//     	'databrowser.util' : 'app/util'
//    }
//});

//Ext.application({
//
//    requires: [
//        'Ext.grid.*',
//        'Ext.data.*',
//        'Ext.util.*',
//        'Ext.toolbar.Paging',
//        'Ext.ModelManager',
//        'Ext.ux.plugins.FitToParent',
//    ],
//    models: [
//        'CatalogsTreeModel',
//        'CatalogsModel',
//        'YearsTreeModel',
//        'StudyModel',
//        'SeriesModel',
//        'VariableModel',
//        'StudyFileModel',
//        'YearModel',
//        'FrequencyModel',
//        'TopicTreeModel'
//     ],
//    stores: [
//        'StudyStore',
//        'SeriesStore',
//        'CatalogStore',
//        'CatalogTreeStore',
//        'YearTreeStore',
//        'YearStore',
//        'VariableStore',
//        'TopicTreeStore',
//        'MemoryVariableStore',
//        'SrMemoryVariableStore'
//    ],
//    views: [
//        'DataBrowserPanel',
//        'CatalogView',
//        'YearView',
//        'DBCard',
//        'Browser',
//        'DetailsPanel',
//        'StudyView',
//        'SeriesView',
//        'YearView',
//        'StudySeriesView',
//        'SeriesMembersView',
//        'VariableView',
//        'FrequencyChart',
//        'Histogram',
//        'VariableDetails',
//        'StackedBarChart',
//        'Alert',
//        'ScatterChart'
//    ],
//	controllers : ['VariableView', 'DataBrowser', 'Browser', 'CatalogView', 'YearView', 'History'],
//    autoCreateViewport: false,
//    name: 'databrowser',
//    appFolder:'/root/app',
//    init : function() {  	
////    	console.log('init');
//    	Ext.Ajax.timeout = 200000; // 200 seconds 
//	    Ext.override(Ext.form.Basic, {     timeout: Ext.Ajax.timeout / 1000 });
//	    Ext.override(Ext.data.proxy.Server, {     timeout: Ext.Ajax.timeout });
//	    Ext.override(Ext.data.Connection, {     timeout: Ext.Ajax.timeout });
//	    Ext.create('databrowser.view.DataBrowserPanel',{
//	         renderTo: 'dbcontainer',
//	         plugins : ['fittoparent'],
//	     })
//    },
//    
//    launch : function() {
//    	console.log('launch application');    	
//	    Ext.tip.QuickTipManager.init();
//	    
//	    var me = this;
//      // init Ext.util.History on app launch; if there is a hash in the url,
////      // our controller will load the appropriate content
////      Ext.Ajax.timeout = 300000; 
////	    Ext.override(Ext.form.Basic, {     timeout: Ext.Ajax.timeout / 1000 });
////	    Ext.override(Ext.data.proxy.Server, {     timeout: Ext.Ajax.timeout });
////	    Ext.override(Ext.data.Connection, {     timeout: Ext.Ajax.timeout });
//      
//      
//      
//      Ext.util.History.init(function(){
//      	console.log('history init firing event');
//      	var hash = document.location.hash;
//         // me.getMainController().fireEvent( 'tokenchange', hash.replace( '#', '' ) );
////            me.getHistoryController().fireEvent( 'tokenchange', hash.replace( '#', '' ) );
//          	me.getHistoryController().fireEvent( 'tokenchangeinit', hash.replace( '#', '' ) );
//            
//      	
//      })
//      // add change handler for Ext.util.History; when a change in the token
//      // occurs, this will fire our controller's event to load the appropriate content
//
//      Ext.util.History.on( 'change', function( token ){
//      	console.log('history change firing event');
//         me.getHistoryController().fireEvent( 'tokenchange', token );
//      });
//
//  }    
//    
//    
//    
//});
