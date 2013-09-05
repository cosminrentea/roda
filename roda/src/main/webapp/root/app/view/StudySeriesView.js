Ext.define('databrowser.view.StudySeriesView', {
    extend: 'Ext.tab.Panel',
    //    extend: 'Ext.grid.Panel',
    alias: 'widget.studyseriesview',
    autoRender: true,
    id: 'studyseriesview',
    itemId: 'studyseriesview',
    width: '100%',
    header: true,
    hideHeaders: false,
    store: 'StudyStore',
    loaddata: function(id){

    	//asta e o varianta idioata determinata oarecum de incapacitatea autorilor extjs de a explica cum se fac rahaturile simple    	
    	
    	var dtab = Ext.getCmp('ssdetails');
    	var variablesgrid = Ext.getCmp('studyseriesvariables');    
    	var filestab = Ext.getCmp('studyseriesdocuments');
//    	var seriestab = Ext.getCmp('studyseriesseries');    	
    	var sStore = Ext.StoreManager.get(this.store);    	
    	sStore.load({
    		  id: id, //set the id here
    		  scope:this,
    		  callback: function(records, operation, success){
    		    if(success){
    		    	var rec = sStore.first();
    		    	dtab.update(records[0].data);
    		    	variablesgrid.getView().bindStore(rec.variablesStore);
    		    	filestab.bindStore(rec.filesStore);
    		    	//aici trebuie sa incarcam storeul de serie
    		    	var seriesStore = Ext.StoreManager.get('SeriesStore');
    		    	seriesStore.load({
    		    		  id: id, //set the id here,
    		    		  callback: function(records, operation, success) {
    		    			  var sdetails = Ext.getCmp('studyseriesdetails');  
    		    			  var studyseriesstudies = Ext.getCmp('studyseriesstudies');
    		    			  sdetails.update(records[0].data);	
    		    			  studyseriesstudies.getView().bindStore(records[0].studiesStore);
    		    			  
    		    		  }
    		    	})
    		    }
    		  }
    		});
    },
    title: 'Study View',
    items: [
            {
                autoScroll: true,
                layout:'fit',
                title: 'Detalii',
            	id:'ssdetails',
                tpl: new Ext.XTemplate(
                		'<div style="padding:10px;">',
                    	'<H2>{name}</H2>',		
                    	'<H3>{author}</H3>',
                    	'<p>{description}</p>',
                    	'</div>'
                    	),
            },{
                autoScroll: true,
                layout:'fit',
                title: 'Variabile',
            	id:'svariables',
                	items: [{
                            	xtype: 'gridpanel',
                            	id: 'studyseriesvariables',
                            	width:'100%',
                            	autoScroll: true,
                            	remoteSort: false,                 	
                            	listeners : {
                            		cellclick : function(gridView,htmlElement,columnIndex,dataRecord,htmlRow, rowIndex, e, eOpts) {
                            			if (columnIndex == 0) {
                            				var varwin = Ext.create('databrowser.view.VariableView');
                            				var previndice;
                            				var nextindice;
                            				if (gridView.getRecord(rowIndex-1)) {
                            					previndice = gridView.getRecord(rowIndex-1).data.indice;
                            				}
                            				if (gridView.getRecord(rowIndex+1)) {
                            					nextindice = gridView.getRecord(rowIndex+1).data.indice;
                            				}
                            				varwin.setTitle(dataRecord.data.name + ' - ' + dataRecord.data.label);
                            				varwin.loaddata(dataRecord.data.indice, previndice, nextindice);
                            				varwin.show();
                            			}
                            		}
                            	},
                            	columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'name',
                                    hideable: false,
                                    text: 'name',
                                    width:150,
                                    fixed: true,
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'label',
                                    hideable: false,
                                    text: 'label',
                                    flex:1
                                },
                            ]
                	}
                	]
            },{
                autoScroll: true,
                layout:'fit',
                title: 'Date',
            	id:'ssdata'
            }, {
            	autoScroll: true,
              layout:'fit',
              title: 'Documente',
          	id:'ssfiles',
            items : [{
            			id: 'studyseriesdocuments',
            			xtype:'dataview',
                      tpl: new Ext.XTemplate(
                  		'<div class="studyfiles">',                   		  
                  		  '<tpl for=".">',  
                  		  '<div class="studyfiledl">',
                    	'<H2 class="file-{filetype}">{filename}</H2>',		
                    	'<p>{filedescription}</p>',
                    	'<a href="{fileurl}">Descarca fisierul</a>',
                    	'</div>',
                    	'</tpl>',
                    	'</div>'	
                      ),
            	   }
            		]
            }, {
                autoScroll: true,
                layout:'accordion',
                title: 'Series',
                icon: 'img/series.png',
            	id:'sseries',
            	items : [
            	         { 
            	        	  title: 'Series details',
            	        	  id: 'studyseriesdetails',
            	              autoScroll: true,
            	              layout:'fit', 
            	               tpl: new Ext.XTemplate(
            	                		'<div style="padding:10px;">',
            	                    	'<H2>Name: {name}</H2>',		
            	                    	'<H3>Author: {author}</H3>',
            	                    	'<p>{description}</p>',
            	                    	'</div>'
            	                    	),
            	         }, {
            	         	 title: 'Other studies',
            	         	xtype: 'seriesmembersview', 	
            	         	 id: 'studyseriesstudies',
            	         }
    	 ]
            			
            }
            ],    
//    store: 'CatalogDetailStore',
initComponent: function() {
    var me = this;
    me.callParent(arguments);  
}

});