Ext.define('databrowser.controller.Browser', {
	extend : 'Ext.app.Controller',
	views : [ 'Alert' ],
	
	 refs: [
	        {
	            ref: 'studyVariables',
	            selector: 'studyview gridpanel#studyvariables'
	        },
	        {
	            ref: 'dbcard',
	            selector: 'dbcard'
	        },
	        {
	            ref: 'dbmaincenter',
	            selector: 'dbmainp #dbcardcont'
	        },
	        {
	            ref: 'analysisVar',
	            selector: 'studyview gridpanel#analysisvar'
	        }, 
	        {
	            ref: 'SranalysisVar',
	            selector: 'studyseriesview gridpanel#sranalysisvar'
	        }
           ],

       	init : function(application) {
    		this.control({
    			"browser treepanel#CatalogsTreePanel" : {
    				selectionchange :  this.onCatalogTreeSelectionChange
    			},
    			"browser treepanel#YearsTreePanel" : {
    				selectionchange :  this.onYearsTreeSelectionChange
    			},
    			"browser treepanel#TopicTreePanel" : {
    				selectionchange :  this.onTopicTreeSelectionChange
    			},
    			
    		});

    	},
    	
    	onYearsTreeSelectionChange : function (component, selected, event) {
    		Ext.History.add('');
    		var record = selected[0];
            var dbcard = this.getDbcard();
            this.getDbmaincenter().setLoading('Loading...');
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
            var vStore = this.getAnalysisVar().getStore();
            var srvStore = this.getSranalysisVar().getStore();
            vStore.getProxy().clear();
            vStore.data.clear();
            vStore.sync();
            srvStore.getProxy().clear();    		
            srvStore.data.clear();
            srvStore.sync();
            this.getDbmaincenter().setLoading(false);
    	},
    	
    	onCatalogTreeSelectionChange: function (component, selected, event) {
    		console.log('captured')
    		var record = selected[0];
            var dbcard = this.getDbcard();
//    		dbcard.setLoading('Loading...');
    		this.getDbmaincenter().setLoading('Loading...');
    		if (record.get('type') == 'C') {
            	dbcard.layout.setActiveItem('catalogview');
            	var catalogview = Ext.getCmp('catalogview');
            	catalogview.setTitle(record.get('text'));
            	catalogview.catalogid = record.get('indice');
            	var cStore = Ext.StoreManager.get('CatalogStore');    
           	    var sButton = Ext.getCmp('SButton');
           	    sButton.toggle(true);
           	    console.log(record.get('indice'));
           	    if (databrowser.util.Globals['vselect'] > 0) {
           	    	
           	    } else {
           	    	Ext.History.add('catalogid-'+record.get('indice'));
           	    }
            	catalogview.loaddata(record.get('indice'));
            } else if (record.get('type') == 'S') {
            	dbcard.layout.setActiveItem('seriesview');	        	
            	var seriesviewob = Ext.getCmp('seriesview');
            	seriesviewob.setTitle(record.get('text'));
        		Ext.History.add('catalogseriesid-'+record.get('indice'));
           	    seriesviewob.loaddata(record.get('indice'));
            } else if (record.get('type') == 'St') {
            	dbcard.layout.setActiveItem('studyview');	
            	var studyviewob = Ext.getCmp('studyview');
            	studyviewob.setTitle(record.get('text'));
        		Ext.History.add('catalogstudyid-'+record.get('indice'));
            	studyviewob.loaddata(record.get('indice'));
            } else if (record.get('type') == 'Sts') {
            	dbcard.layout.setActiveItem('studyseriesview');	
            	var studyseriesviewob = Ext.getCmp('studyseriesview');
            	studyseriesviewob.setTitle(record.get('text'));
           	    if (databrowser.util.Globals['vselect'] > 0) {
           	    	
           	    } else {
            		Ext.History.add('seriesstudyid-'+record.get('indice'));
           	    }
            	
            	studyseriesviewob.loaddata(record.get('indice'));
            } else {
            	dbcard.layout.setActiveItem('initial');
            }
            var vStore = this.getAnalysisVar().getStore();
            var srvStore = this.getSranalysisVar().getStore();
            vStore.getProxy().clear();
            vStore.data.clear();
            vStore.sync();
            srvStore.getProxy().clear();    		
            srvStore.data.clear();
            srvStore.sync();
            this.getDbmaincenter().setLoading(false);
    	},
    	
    	onTopicTreeSelectionChange: function (component, selected, event) {
    		console.log('captured topic')
    		var record = selected[0];
            var dbcard = this.getDbcard();
            console.log(record.get('type'));
//    		dbcard.setLoading('Loading...');
    		this.getDbmaincenter().setLoading('Loading...');
    		if (record.get('type') == 'T') {
    			console.log('-----------topic view-------------------');
            	dbcard.layout.setActiveItem('topicview');
            	var topicview = Ext.getCmp('topicview');
            	topicview.setTitle(record.get('text'));
            	topicview.catalogid = record.get('indice');
            	var cStore = Ext.StoreManager.get('TopicStore');    
           	    var sButton = Ext.getCmp('SButton');
           	    sButton.toggle(true);
           	    console.log(record.get('indice'));
            	topicview.loaddata(record.get('indice'));
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
            var vStore = this.getAnalysisVar().getStore();
            var srvStore = this.getSranalysisVar().getStore();
            vStore.getProxy().clear();
            vStore.data.clear();
            vStore.sync();
            srvStore.getProxy().clear();    		
            srvStore.data.clear();
            srvStore.sync();
            this.getDbmaincenter().setLoading(false);
    	}
           
});