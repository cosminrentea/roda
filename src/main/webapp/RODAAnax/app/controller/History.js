Ext.define('anax.controller.History', {
    extend : 'Ext.app.Controller',

    // stores: [
    // 'CatalogStore',
    // 'CatalogTreeStore',
    // ],

    refs : [



    ],

    init : function(application) {
 //   	this.loadEmpty();
	    this.control({});

	    this.listen({
		    // We are using Controller event domain here
		    controller : {
			    // This selector matches any originating Controller
			    '*' : {
//			        tokenchange : 'tokenChange',
//			        tokenchangeinit : 'tokenInit'
			    }
		    }
	    });

    },

    tokenChange : function(token) {
    	if (token) {
    		console.log('token change here' + token);
		    var tokenparts = token.split("-");
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
			    console.log('change topic' + tokenparts[1]);
			    var topic = tokenparts[1];
			    if (topic) {
				    this.loadTopicInTree(topic);
			    }
		    }
		    else if (tokenparts[0] == 'catalogstudyid') {
			    console.log('have study' + tokenparts[1]);
			    var studyid = tokenparts[1];
			    if (studyid) {
				    this.loadStudyInCatalogTreeD(studyid);
			    }
		    } 
		    else if (tokenparts[0] == 'catalogstudy') {
			    console.log('have study' + tokenparts[1]);
			    var catalogid = tokenparts[1];
			    var studyid = tokenparts[2];
			    if (studyid) {
				    this.loadStudyInCatalogTreeD(studyid);
			    }
		    } 		    
		    else if (tokenparts[0] == 'catalogstudyvariable') {
			    console.log('have variable' + tokenparts[1]);
			    var catalogid = tokenparts[1];
			    var studyid = tokenparts[2];
			    var variableid = tokenparts[3];
			    this.loadVariableinCatalog(studyid, variableid);

		    } 
		    else if (tokenparts[0] == 'yearstudyid') {
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
    		console.log('token init here' + token);
    		var tokenparts = token.split("-");
    		console.log(tokenparts[0]);
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
			    console.log('init topic' + tokenparts[1]);
			    var topic = tokenparts[1];
			    if (topic) {
				    this.loadTopicInTree(topic);
			    }
		    }
		    else if (tokenparts[0] == 'catalogstudy') {
			    console.log('have study' + tokenparts[1]);
			    var catalogid = tokenparts[1];
			    var studyid = tokenparts[2];
			    if (studyid) {
				    this.loadStudyInCatalogTreeD(studyid);
			    }
		    } 		    

		    else if (tokenparts[0] == 'catalogstudyid') {		    	
				    console.log('have study' + tokenparts[1]);
				    var studyid = tokenparts[1];
				    if (studyid) {
					    this.loadStudyInCatalogTreeD(studyid);
				    }
			    } 
		    else if (tokenparts[0] == 'yearstudyid') {
		    		console.log('have study' + tokenparts[1]);
				    var studyid = tokenparts[1];
				    if (studyid) {
					    this.loadStudyInYearTree(studyid);

				    }
		    }
		    else if (tokenparts[0] == 'catalogstudyvariable') {
					    console.log('have variable' + tokenparts[1]);
					    var catalogid = tokenparts[1];
					    var studyid = tokenparts[2];
					    var variableid = tokenparts[3];
					    this.loadVariableinCatalog(studyid, variableid);

		    } 
			    
			    
			    
		     else {
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
    
    
    loadVariableinCatalog : function (studyid, variableid){
    	console.log('loading variable ' + studyid + 'var: '+ variableid);
    	this.loadYears();
    	this.loadTopics();
    	this.getBrowser().getLayout().setActiveItem('CatalogsTreePanel');
    	console.log('=============');
    	var cvv = this.getCatalogtree();
	    var mytstore = Ext.StoreManager.get('CatalogTreeStore');
	    //console.log(mytstore);
    	console.log('=============');	    
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
		        	console.log('ce facem');
			        if (this.data.type == 'St' || this.data.type == 'Sts') {
			        	console.log(this.data.indice);
				        if (this.data.indice == studyid) {
					        console.log('found the cucker');
					        var cvv = me.getCatalogtree();
//					        var record = cvv.getStore().getNodeById(this.data.year);
					        cvv.getSelectionModel().select(this);
					        var path = this.getPath();
					        console.log(path);
					        cvv.expandPath(path);
					        
					        //acu sa vedem ce avem in panoul din dreapta
					        var dbcard = Ext.getCmp('dbcard');
						    var activeitem = dbcard.layout.getActiveItem();
					        console.log(activeitem);
					        if (activeitem.itemId == 'studyseriesview') {
					        	activeitem.layout.setActiveItem('svariables');
					        	var vgrid = activeitem.down('gridpanel#studyseriesvariables');
					        	var rowIndex = vgrid.getStore().find('indice', variableid);  //where 'id': the id field of your model, record.getId() is the method automatically created by Extjs. You can replace 'id' with your unique field.. And 'this' is your store.
					        	console.log(rowIndex);
					        	vgrid.getView().select(rowIndex);
					        	
					        } else if (activeitem.itemId == 'studyview') {
					        	activeitem.layout.setActiveItem('svariables');
					        	var vgrid = activeitem.down('gridpanel#studyvariables');
					        	var rowIndex = vgrid.getStore().find('indice', variableid);  //where 'id': the id field of your model, record.getId() is the method automatically created by Extjs. You can replace 'id' with your unique field.. And 'this' is your store.
					        	console.log(rowIndex);
					        	vgrid.getView().select(rowIndex);
					        }
//					        activeitem.layout.setActiveItem('');
					        
					        
				        }
			        }
		        });
	        }
	    });
	},
    
    loadStudyIinCatalog : function(studyid) {
    	this.loadYears();
    	this.loadTopics();
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
    		this.loadCatalogs();
    		this.loadTopics();
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
    },
    
    loadTopicInTree : function(topic) {
    	this.loadCatalogs();
    	this.loadYears();
	    this.getBrowser().getLayout().setActiveItem('TopicTreePanel');
	    var cvv = this.getTopicstree();
	    var mytstore = Ext.StoreManager.get('TopicTreeStore');
	    console.log(mytstore);
	    var stload = mytstore.load({
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
	    if (stload) {
	    	console.log('loaded');
	    } else {
	    	console.log('not loaded');	    	
	    }
	    
    },
    
    
    loadYearInTree : function(year) {
    	this.loadCatalogs();
    	this.loadYears();
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
    	this.loadCatalogs();
    	this.loadTopics();
    	console.log('study in year tree ' + studyid);
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
			        if (this.data.type == 'Sts' || this.data.type == 'St') {
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
    

    loadStudyInCatalogTreeD : function(studyid) {
    	this.loadTopics();
    	this.loadYears();
    	console.log('study in catalog tree' + studyid);
	    this.getBrowser().getLayout().setActiveItem('CatalogsTreePanel');
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
		        	console.log('ce facem');
			        if (this.data.type == 'St' || this.data.type == 'Sts') {
			        	console.log(this.data.indice);
				        if (this.data.indice == studyid) {
					        console.log('found the cucker');
					        var cvv = me.getCatalogtree();
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
    	this.loadTopics();
    	this.loadYears();
    	console.log('load catalog in tree');
    	this.getBrowser().getLayout().setActiveItem('CatalogsTreePanel');
    	var cvv = Ext.getCmp('CatalogsTreePanel');
	    var cvv = this.getCatalogtree();
	    var mytstore = Ext.StoreManager.get('CatalogTreeStore');
	    console.log(mytstore);
	    var cucu = mytstore.load({
	    	scope : this,
	        callback : function(records, operation, success) {
	        	console.log('catalog tree store loaded');
	        	var cvv = this.getCatalogtree();
		        Ext.apply(cvv, {
			        store : mytstore
		        });
		        var rn = mytstore.getRootNode();
		        var me = this;
		        rn.cascadeBy(function() {
			        console.log(this);
			        if (this.data.type == 'C') {
			        	console.log('found the cucker');	
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
	    if (cucu) {
	    } else {
	    	console.log('else');
	        var rn = mytstore.getRootNode();
	        var me = this;
	        rn.cascadeBy(function() {
		        console.log(this);
		        if (this.data.type == 'C') {
		        	console.log('found the cucker');	
			        if (this.data.indice == catalogid) {
			        	var cvv = me.getCatalogtree();
				        cvv.getSelectionModel().select(this);
				        var path = this.getPath();
				        cvv.expandPath(path);
			        }
		        }
	        });
	    }
    },

    setCatalogInTree : function(catalogid) {
    	this.loadYears();
    	this.loadTopics();
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