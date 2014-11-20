Ext.define('databrowser.controller.YearView', {
	extend : 'Ext.app.Controller',
	views : [ 'YearView' ],
	
	 refs: [
	        {
	            ref: 'yearview',
	            selector: 'yearview'
	        },
	        ],
	
	init : function(application) {
		this.control({
			"yearview button#YSButton" : {
				 click : this.onYSButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"yearview button#YMButton" : {
				 click : this.onYMButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"yearview button#YCButton" : {
				 click : this.onYCButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"yearview textfield#searchbox" : {
				specialkey : this.onSearchKey
  	 	  	 }			
		});

	},

	
	onSearchKey : function(field, event) {
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
	
	
	onYSButtonClick : function(button, e, eOpts) {
		console.log('sbutton controller click');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().setCurrentview('simple');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().getView().refresh();
	},

	onYCButtonClick : function(button, e, eOpts) {
		console.log('cbutton controller click');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().setCurrentview('complex');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().getView().refresh();

	},

	onYMButtonClick : function(button, e, eOpts) {
		console.log('mbutton controller click');
		//sa vedem ce facem. Intai sa vedem daca putem sa luam configul
		console.log(this.getYearview().getCurrentview());
		this.getYearview().setCurrentview('medium');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().getView().refresh();

	},


});