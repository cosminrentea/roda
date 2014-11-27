Ext.define('databrowser.controller.CatalogView', {
	extend : 'Ext.app.Controller',
	views : [ 'CatalogView' ],
	
	 refs: [
	        {
	            ref: 'catalogview',
	            selector: 'catalogview'
	        },
	        ],
	
	init : function(application) {
		this.control({
			"catalogview button#SButton" : {
				 click : this.onSButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"catalogview button#MButton" : {
				 click : this.onMButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"catalogview button#CButton" : {
				 click : this.onCButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"catalogview textfield#searchbox" : {
				specialkey : this.onSearchKey
  	 	  	 },
		});

	},

	onSearchKey : function(field, event) {
		console.log('ia hai');
		if (event.getKey() == event.ENTER) {
			 Ext.data.JsonP.request({
		            url: 'http://localhost:8983/solr/collection1/select/?q=language:'+translations.language+' AND description:'+field.value+'&version=2.2&hl=true&hl.fl=description&indent=on&wt=json',
		            method: 'GET',
		            callbackKey : 'json.wrf',
		            params: {
		            	start: 0,
			 			rows: 100,
		            },
		            failure: function () {
		                console.log('solr search failed');
		            },
		            success: function (data) {
		            	var header = data.responseHeader;
		            	var response = data.response;
		            	var highlighting = data.highlighting;
		            	var win = Ext.WindowMgr.get('searchres');
		        	    if (!win) {
		        		    win = Ext.create('databrowser.view.SearchResults');
		        	    }	    
		        	    var srcelement = Ext.get('catalogSearch');
		        	    console.log(srcelement);
		        	    win.setTitle('Search results');
		        	    var container = win.down('container#srcres');
		        	    response.results = translations.results;	
		        	    for (var i = 0; i < response.docs.length; i++) {
		        	    	response.docs[i].highlight = highlighting[response.docs[i].id].description;
		        	    }
		        	    container.update(response);
		        	    win.show();
		            }
		        });			 
		}
	},
	
	onSButtonClick : function(button, e, eOpts) {
		console.log('sbutton controller click');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().setCurrentview('simple');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().getView().refresh();
	},

	onCButtonClick : function(button, e, eOpts) {
		console.log('cbutton controller click');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().setCurrentview('complex');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().getView().refresh();

	},

	onMButtonClick : function(button, e, eOpts) {
		console.log('mbutton controller click');
		//sa vedem ce facem. Intai sa vedem daca putem sa luam configul
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().setCurrentview('medium');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().getView().refresh();

	},


});