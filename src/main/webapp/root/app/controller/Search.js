/**
 * Controller de baza pentru diferite ferestre care au butoane. Butonul de OK de la aceste ferestre va face lucruri diferite dar butonul de cancel si cel de close 
 * fac de fiecare data acelasi lucru, inchid fereastra
 */
Ext.define('databrowser.controller.Search', {
    extend: 'Ext.app.Controller',
   
    init: function(application) {
        this.control({
            "advsearch button#ok": {
                click: this.onButtonClickOk
            }, 
            "tool#search": {
                click: this.onToolSearch
            }
        });
    },
   	/**
   	 * @method
   	 */
    
    onToolSearch : function (button, event) {
		console.log('come the fuck on');
		var win = Ext.WindowMgr.get('advsearch');
	    if (!win) {
		    win = Ext.create('databrowser.view.AdvancedSearch');
	    }	  
	    win.setTitle('Advanced search');
	    win.show();
		
	},
    
    onButtonClickOk: function(button, e, options) {
    	var stitle = button.up('window').down('form').down('textfield[name=stitle]').getValue();
    	var stabstract = button.up('window').down('form').down('textfield[name=stabstract]').getValue();
    	var stuniverse = button.up('window').down('form').down('textfield[name=stuniverse]').getValue();
    	var stvariables = button.up('window').down('form').down('textfield[name=stvariables]').getValue();    	
    	var strtype = button.up('window').down('form').down('textfield[name=strtype]').getValue();
    	console.log('stitle' + stitle);
    	console.log('stabstract' + stabstract);
    	console.log('stuniverse' + stuniverse);
    	console.log('stvariables' + stvariables);
    	console.log('strtype' + strtype);

    	var srcwin = button.up('window');
		 Ext.data.JsonP.request({
	            url: 'http://localhost:8983/solr/collection1/select/?q=language:'+translations.language+' AND description:'+stitle+'&version=2.2&hl=true&hl.fl=description&indent=on&wt=json',
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

	        	    var srcelement = Ext.get('catalogSearch');
	        	    console.log(srcelement);
//	        	    win.setTitle('Search results');

	        	    
	        	    var container = srcwin.down('container#srcresults');
	        	    response.results = translations.results;	
	        	    for (var i = 0; i < response.docs.length; i++) {
	        	    	response.docs[i].highlight = highlighting[response.docs[i].id].description;
	        	    }
	        	    container.update(response);
	        	    win.show();
	            }
	        });			 

    	
    	
    	
    	
    	
    	
    	button.up('window').layout.setActiveItem('srcresults');
    
    
    
    
    
    }

    
});
