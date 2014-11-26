Ext.define('databrowser.controller.History', {
    extend : 'Ext.app.Controller',

    // stores: [
    // 'CatalogStore',
    // 'CatalogTreeStore',
    // ],

    refs : [

            {
                ref : 'browser',
                selector : 'browser tabpanel#TreesPanel'
            }, {
                ref : 'catalogview',
                selector : 'castalogview'
            }, {
                ref : 'catalogtree',
                selector : 'browser treepanel#CatalogsTreePanel'
            }, {
                ref : 'yearstree',
                selector : 'browser treepanel#YearsTreePanel'
            },
            {
                ref : 'topicstree',
                selector : 'browser treepanel#TopicTreePanel'
            },

    ],

    init : function(application) {
	    this.control({});

	    this.listen({
		    // We are using Controller event domain here
		    controller : {
			    // This selector matches any originating Controller
			    '*' : {
			        tokenchange : 'tokenChange',
			        tokenchangeinit : 'tokenInit'
			    }
		    }
	    });

    },

    tokenChange : function(token) {
    	if (token) {
    		console.log('token here' + token);
    		
		    var tokenparts = token.split(":");
		    if (tokenparts[0] == 'catalogid') {
			    console.log('have catalog' + tokenparts[1]);
			    var catalogid = tokenparts[1];
			    if (catalogid) {
				    this.loadCatalogInTree(catalogid);
			    }
		    }
		    else if (tokenparts[0] == 'year') {
			    console.log('have year' + tokenparts[1]);
			    var year = tokenparts[1];
			    if (year) {
				    this.loadYearInTree(year);
			    }
		    }
		    else if (tokenparts[0] == 'topicid') {
			    console.log('have topic' + tokenparts[1]);
			    var topic = tokenparts[1];
			    if (topic) {
				    this.loadTopicInTree(topic);
			    }
		    }
		    else if (tokenparts[0] == 'catalogstudyid') {
//		    else if (tokenparts[0] == 'study') {
			    console.log('have study' + tokenparts[1]);
			    var studyid = tokenparts[1];
			    if (studyid) {
				    this.loadStudyIdinCatalog(studyid);
			    }
		    } 
//		    else if (tokenparts[0] == 'yearstudyid') {
			    else if (tokenparts[0] == 'study') {		    	
			    console.log('have study' + tokenparts[1]);
			    var studyid = tokenparts[1];
			    if (studyid) {
				    this.loadStudyInYearTree(studyid);
			    }
		    
		    } else {
		    	this.noToken();
		    }
	    }
    },
    
    
    
    tokenInit : function(token) {
    	if (token) {
    		console.log('token here' + token);
    		var tokenparts = token.split(":");
		    if (tokenparts[0] == 'catalogid') {
			    console.log('have catalog' + tokenparts[1]);
			    var catalogid = tokenparts[1];
			    if (catalogid) {
				    this.loadCatalogInTree(catalogid);
			    }
		    }
		    else if (tokenparts[0] == 'year') {
			    console.log('have year' + tokenparts[1]);
			    var year = tokenparts[1];
			    if (year) {
				    this.loadYearInTree(year);
			    }
		    }
		    else if (tokenparts[0] == 'topicid') {
			    console.log('have topic' + tokenparts[1]);
			    var topic = tokenparts[1];
			    if (topic) {
				    this.loadTopicInTree(topic);
			    }
		    }
//		    else if (tokenparts[0] == 'studyid') {
//			    console.log('have study' + tokenparts[1]);
//			    var studyid = tokenparts[1];
//			    if (studyid) {
//				    this.loadStudyId(studyid);
//			    }

//		    else if (tokenparts[0] == 'study') {
		    else if (tokenparts[0] == 'catalogstudyid') {		    	
				    console.log('have study' + tokenparts[1]);
				    var studyid = tokenparts[1];
				    if (studyid) {
					    this.loadStudyIdinCatalog(studyid);
				    }
			    } 
//		    else if (tokenparts[0] == 'yearstudyid') {
		    else if (tokenparts[0] == 'study') {
		    		console.log('have study' + tokenparts[1]);
				    var studyid = tokenparts[1];
				    if (studyid) {
					    this.loadStudyInYearTree(studyid);

				    }
			    
			    
			    
		    } else {
		    	this.noToken();
		    }
	    } else {
	    	this.loadEmpty();
	    }
    },

    
    noToken : function() {
    	console.log('no token');
    },
    

    loadCatalogs : function() {
    	this.getCatalogtree().setLoading(translations.loading);
    	this.getCatalogtree().getStore().load({
	        scope : this,
	        callback : function(records, operation, success) {
	        		console.log('load catalog store');
	        		this.getCatalogtree().setLoading(false);
	        }
	    });
    },
    
    loadYears : function() {
    	this.getYearstree().setLoading(translations.loading);    	
    	this.getYearstree().getStore().load({
	        scope : this,
	        callback : function(records, operation, success) {
	        		console.log('load years store');
	        		this.getYearstree().setLoading(false);
	        }
	    });
    },

    loadTopics : function() {
    	this.getTopicstree().setLoading(translations.loading);    	
    	this.getTopicstree().getStore().load({
	        scope : this,
	        callback : function(records, operation, success) {
	        		console.log('load topics store');
	        		this.getTopicstree().setLoading(false);
	        }
	    });
    },
    
    
    
    loadEmpty : function() {
    	console.log('loadEmpty');
    	this.loadCatalogs();
    	this.loadYears();
    	this.loadTopics();
    },
    
    loadStudyIdinCatalog : function(studyid) {
	    console.log('loading study ' + studyid);
	    var dbcard = Ext.getCmp('dbcard');
	    dbcard.layout.setActiveItem('studyview');
	    var studyviewob = Ext.getCmp('studyview');
	    studyviewob.setLoading(translations.loading);
	    
	    var sStore = Ext.StoreManager.get('StudyStore');
	    sStore.load({
	        id : studyid, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
	        	studyviewob.setLoading(false);
	        	if (success) {
	        		//TODO: make it prettier
	        		var dtab = Ext.getCmp('sdetails');
        		    var variablesgrid = Ext.getCmp('studyvariables');
        		    var filestab = Ext.getCmp('studydocuments');
	        		var rec = sStore.first();
			        console.log(rec.filesStore);
			        console.log('study store loaded');
			        dtab.update(records[0].data);
			        variablesgrid.getView().bindStore(rec.variablesStore);
			        console.log(variablesgrid.getStore());
			        filestab.bindStore(rec.filesStore);

			        
			        
		        }
	        }
	    });
    },
	
    loadStudyIdinYear : function(studyid) {
		    console.log('loading study ' + studyid);
	//	    this.getBrowser().getLayout().setActiveItem('YearTreePanel');
		    var dbcard = Ext.getCmp('dbcard');
		    dbcard.layout.setActiveItem('studyview');
		    var studyviewob = Ext.getCmp('studyview');
		    studyviewob.setLoading(translations.loading);
		    
		    var sStore = Ext.StoreManager.get('StudyStore');
		    sStore.load({
		        id : studyid, // set the id here
		        scope : this,
		        callback : function(records, operation, success) {
		        	studyviewob.setLoading(false);
		        	if (success) {
		        		var rec = sStore.first();
				        var myyear = rec.get('an');
				        console.log(myyear);
				        this.loadYearInTree(myyear);
				        //aici avem nevoie de catalog
			        }
		        }
		    });
	    
	    
//	    studyviewob.loaddata(studyid);
	    // cvv.loadStudy(studyid);

	    // this.getCatalogview.loadStudy(studyid);
    },
    
    loadTopicInTree : function(topic) {
    	console.log('loading topic');
	    this.getBrowser().getLayout().setActiveItem('TopicTreePanel');
	    var cvv = this.getTopicstree();
	    var mytstore = Ext.StoreManager.get('TopicTreeStore');
	    console.log(mytstore);
	    mytstore.load({
	        scope : this,
	        callback : function(records, operation, success) {
		        var cvv = this.getTopicstree();
		        console.log('something loaded in topic callback');
		        console.log(cvv);
		        Ext.apply(cvv, {
			        store : mytstore
		        });
		        var rn = mytstore.getRootNode();
		        var me = this;
		        rn.cascadeBy(function() {
				        if (this.data.indice == topic) {
					        console.log('fould the cucker');
					        var cvv = me.getTopicstree();
					        //var record = cvv.getStore().getNodeById(this.data.id);
					        cvv.getSelectionModel().select(this);
					        var path = this.getPath();
					        console.log(path);
					        cvv.expandPath(path);
					        // get the catalog view loaded or signal it
				        }
		        });
	        }
	    });
    },
    
    
    loadYearInTree : function(year) {
    	console.log('ani de liceu');
	    this.getBrowser().getLayout().setActiveItem('YearsTreePanel');
	    var cvv = this.getYearstree();
	    var mytstore = Ext.StoreManager.get('YearTreeStore');
	    console.log(mytstore);
	    mytstore.load({
	        scope : this,
	        callback : function(records, operation, success) {
		        var cvv = this.getYearstree();
		        Ext.apply(cvv, {
			        store : mytstore
		        });
		        var rn = mytstore.getRootNode();
		        var me = this;
		        rn.cascadeBy(function() {
			        if (this.data.type == 'Y') {
				        if (this.data.year == year) {
					        console.log('fould the cucker');
					        var cvv = me.getYearstree();
//					        var record = cvv.getStore().getNodeById(this.data.year);
					        cvv.getSelectionModel().select(this);
					        var path = this.getPath();
					        console.log(path);
					        cvv.expandPath(path);
					        // get the catalog view loaded or signal it
				        }
			        }
		        });
	        }
	    });
    },

    
    loadStudyInYearTree : function(studyid) {
    	console.log('study in year tree' + studyid);
	    this.getBrowser().getLayout().setActiveItem('YearsTreePanel');
	    var cvv = this.getYearstree();
	    var mytstore = Ext.StoreManager.get('YearTreeStore');
	    console.log(mytstore);
	    mytstore.load({
	        scope : this,
	        callback : function(records, operation, success) {
		        var cvv = this.getYearstree();
		        Ext.apply(cvv, {
			        store : mytstore
		        });
		        var rn = mytstore.getRootNode();
		        var me = this;
		        rn.cascadeBy(function() {
		        	console.log('ce facem');
			        if (this.data.type == 'Sts') {
			        	console.log(this.data.indice);
				        if (this.data.indice == studyid) {
					        console.log('fould the cucker');
					        var cvv = me.getYearstree();
//					        var record = cvv.getStore().getNodeById(this.data.year);
					        cvv.getSelectionModel().select(this);
					        var path = this.getPath();
					        console.log(path);
					        cvv.expandPath(path);
				        }
			        }
		        });
	        }
	    });
    },
    
    
    
    loadCatalogInTree : function(catalogid) {
    	this.getBrowser().getLayout().setActiveItem('CatalogsTreePanel');
    	var cvv = Ext.getCmp('CatalogsTreePanel');
	    var cvv = this.getCatalogtree();
	    var mytstore = Ext.StoreManager.get('CatalogTreeStore');
	    console.log(mytstore);
	    mytstore.load({
	        scope : this,
	        callback : function(records, operation, success) {
		        var cvv = this.getCatalogtree();
		        Ext.apply(cvv, {
			        store : mytstore
		        });
		        var rn = mytstore.getRootNode();
		        var me = this;
		        rn.cascadeBy(function() {
			        console.log(this);
			        if (this.data.type == 'C') {
				        if (this.data.indice == catalogid) {
				        	var cvv = me.getCatalogtree();
					        cvv.getSelectionModel().select(this);
					        var path = this.getPath();
					        cvv.expandPath(path);
				        }
			        }
		        });
	        }
	    });
    },

    setCatalogInTree : function(catalogid) {
    	this.getBrowser().getLayout().setActiveItem('CatalogsTreePanel');
    	console.log('setting catalog in tree' + catalogid);
	    var cvv = Ext.getCmp('CatalogsTreePanel');
	    var cvv = this.getCatalogtree();
	    var mytstore = Ext.StoreManager.get('CatalogTreeStore');
	    console.log(mytstore);
        var rn = mytstore.getRootNode();
        var me = this;
        rn.cascadeBy(function() {
	        console.log(this);

	        if (this.data.type == 'C') {
		        if (this.data.indice == catalogid) {
		        	var cvv = me.getCatalogtree();
			        cvv.getSelectionModel().select(this);
			        var path = this.getPath();
			        cvv.expandPath(path);
		        }
	        }
        });
	    
	    
//	    mytstore.load({
//	        scope : this,
//	        callback : function(records, operation, success) {
//		        var cvv = this.getCatalogtree();
//		        Ext.apply(cvv, {
//			        store : mytstore
//		        });
//		        var rn = mytstore.getRootNode();
//		        var me = this;
//		        rn.cascadeBy(function() {
//			        console.log(this);
//			        if (this.data.type == 'C') {
//				        if (this.data.indice == catalogid) {
//				        	var cvv = me.getCatalogtree();
//					        cvv.getSelectionModel().select(this);
//					        var path = this.getPath();
//					        cvv.expandPath(path);
//				        }
//			        }
//		        });
//	        }
//	    });
    },

    
    
    tokenFired : function(token) {
	    console.log('tokenfired');
	    console.log(token);
	    if (token) {
		    var tokenparts = token.split("-");
		    if (tokenparts[0] == 'study') {
			    console.log('study token' + tokenparts[1]);
		    }
		    else if (tokenparts[0] == 'studyid') {
			    console.log('study token' + tokenparts[1]);
			    var studyid = tokenparts[1];
			    if (studyid) {
				    console.log('loading study ' + studyid);

				    var cvv = Ext.getCmp('catalogview');
				    // Ext.getCmp('catalogview').getView.getStore.load();
				    // Ext.getCmp('catalogview').getStore().load();
				    // incarcam de mana
				    // intai sa incarcam store-ul de cataloage
				    // cvv.loaddata(studyid);
				    // a de presupus ca aici s-a incarcat catalogul.

				    // ar trebui sa localizam studiul in catalog si sa trimitem
					// pointerul acolo

				    //	        				
				    console.log('-**************-');

				    // var rn = this.getCatalogtree.store.getRootNode();
				    var cst = this.getCatalogtree().getStore();
				    console.log(cst.isLoaded);
				    var me = this;
				    cst.load({
				        scope : this,
				        callback : function(records, operation, success) {
					        // this.setLoading(false);
					        if (success) {
						        var rn = cst.getRootNode();
						        rn.cascadeBy(function() {
							        console.log(this.data);
							        if (this.data.indice == studyid) {
								        console.log('found the cucker');
							        }
						        });
						        console.log('loaded catalog tree store');
						        me.getCatalogtree().update();
					        }
				        }
				    });

				    // var rn = cst.getRootNode();
				    // rn.expand();
				    // rn.expandChildren();
				    // rn.cascadeBy(function(){
				    // console.log(this.data);
				    // if (this.data.indice == studyid) {
				    // console.log('found the cucker');
				    // }
				    // });

				    console.log('-**************-');
				    var dbcard = Ext.getCmp('dbcard');
				    dbcard.layout.setActiveItem('studyview');
				    var studyviewob = Ext.getCmp('studyview');
				    studyviewob.loaddata(studyid);
				    // cvv.loadStudy(studyid);

				    // this.getCatalogview.loadStudy(studyid);
			    }
		    }
		    else if (tokenparts[0] == 'catalog') {
			    console.log('catalog token' + tokenparts[1]);

		    }
		    else if (tokenparts[0] == 'catalogid') {
			    console.log('catalogid token' + tokenparts[1]);
			    var catalogid = tokenparts[1];
			    if (catalogid) {
				    console.log('loading catalog ' + catalogid);
				    var cvv = Ext.getCmp('catalogview');
				    console.log(cvv.getStore());
				    var mytstore = Ext.StoreManager.get('CatalogTreeStore');
				    console.log(mytstore);

				    mytstore.load({
				        scope : this,
				        callback : function(records, operation, success) {
					        // this.setLoading(false);
					        console.log(records);
					        var dstore = this.getCatalogtree().getStore();
					        var rn = dstore.getRootNode();
					        rn.cascadeBy(function() {
						        console.log(this.data);
						        if (this.data.indice == studyid) {
							        console.log('found the cucker');
						        }
					        });
					        console.log('loaded catalog tree store');
					        this.getCatalogtree().update();
				        }

				    });

			    }

		    }
		    else if (tokenparts[0] == 'year') {
			    console.log('year token' + tokenparts[1]);
		    }
	    }
    },

});