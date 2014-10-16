Ext.define('databrowser.controller.DataBrowser', {
	extend : 'Ext.app.Controller',
	views : [ 'Alert' ],
	
	 refs: [
	        {
	            ref: 'studyVariables',
	            selector: 'studyview gridpanel#studyvariables'
	        },
	        {
	            ref: 'analysisVar',
	            selector: 'studyview gridpanel#analysisvar'
	        }, 
//	        {
//	        	ref: 'freqChart',
//	        	selector: 'studyview freqchart#ilfreqchart'
//	        	
//	        },
	        {
	        	ref: 'anPanel',
	        	selector: 'studyview panel#analResults'
	        },
	        {
	        	ref: 'singledetails',
	        	selector: 'studyview panel#vardetails'
	        },
	        {
	        	ref: 'stsingledetails',
	        	selector: 'studyseriesview panel#srvardetails'
	        },
	        
	        {
	            ref: 'SrstudyVariables',
	            selector: 'studyseriesview gridpanel#studyseriesvariables'
	        },
	        {
	            ref: 'SranalysisVar',
	            selector: 'studyseriesview gridpanel#sranalysisvar'
	        }, 
//	        {
//	        	ref: 'SrfreqChart',
//	        	selector: 'studyseriesview freqchart#ilfreqchart'
//	        	
//	        },
	        {
	        	ref: 'SranPanel',
	        	selector: 'studyseriesview panel#analResults'
	        },
	        {
	        	ref: 'SrvarPanel',
	        	selector: 'studyseriesview panel#srvardetails'
	        },	        
	        {
	        	ref: 'vardet',
	        	selector: 'studyview tabpanel#vardet'	
	        },
	        {
	        	ref: 'servardet',
	        	selector: 'studyseriesview tabpanel#srvardet'	
	        }
	        ],
	
	init : function(application) {
		this.control({
			"studyview gridpanel#studyvariables" : {
				 itemcontextmenu : this.onVariablesGridContextMenu,
				 cellclick : this.onMenuGetRDetails
				// selectionchange: this.onMenuGetRDetails
			},

			"studyseriesview gridpanel#studyseriesvariables" : {
				 itemcontextmenu : this.onSTVariablesGridContextMenu,
				 cellclick : this.onSTMenuGetRDetails
				 //selectionchange: this.onSTMenuGetRDetails
				 
			},
			'variablecontextmenu menuitem#vardetails' : {
				 //click: this.onMenuGetRDetails	
				 selectionchange: this.onMenuGetRDetails
			},
			'variablecontextmenu menuitem#addanal' : {
				 click: this.onMenuAddAnal	
			},
			
			'stvariablecontextmenu menuitem#stvardetails' : {
				 click: this.onSTMenuGetRDetails	
			},
			'stvariablecontextmenu menuitem#staddanal' : {
				 click: this.onSTMenuAddAnal
			},
			"studyview button#sendToAnalysis" :{
				click: this.onSendToAnalysis
			},
			'studyview gridpanel#analysisvar' : {
				deleteRecord : this.onGridDeleteAction,
			},
			'studyview gridpanel#analysisvar > gridview' : {
				beforedrop : this.onStudyVarGridDrop,
			},
			"studyseriesview button#sendToAnalysis" :{
				click: this.onSTSendToAnalysis
			},
			'studyseriesview gridpanel#sranalysisvar' : {
				deleteRecord : this.onGridDeleteAction
			},
			'studyseriesview gridpanel#sranalysisvar  > gridview' : {
				deleteRecord : this.onSerStudyVarGridDrop
			},
			
		});

	},

	onGridDeleteAction : function(grid, record, rowIndex, row) {
		grid.getStore().removeAt(rowIndex);
	},

	onStudyVarGridDrop : function(node, data, overModel, dropPosition, dropHandlers) {
		 dropHandlers.wait = true;
		var currentNode = data.records[0];
		var mygrid = this.getAnalysisVar();
		var vStore = this.getAnalysisVar().getStore();
		var isit = vStore.findExact('name',currentNode.data.name);
		if (isit > -1) {
			 Ext.Msg.alert('Eroare', 'Variabila e deja acolo');
			 dropHandlers.cancelDrop();
		} else {
	
		if (vStore.count() < 2) {
			 dropHandlers.processDrop();
		} else {
			 Ext.Msg.alert('Eroare', 'Numarul maxim de variabile a fost atins');
			 dropHandlers.cancelDrop();
		}	
		}
	},

	onSerStudyVarGridDrop : function(node, data, overModel, dropPosition, dropHandlers) {
		 dropHandlers.wait = true;
		var currentNode = data.records[0];
		var mygrid = this.getSranalysisVar();
		var vStore = this.getSranalysisVar().getStore();
		var isit = vStore.findExact('name',currentNode.data.name);
		if (isit > -1) {
			 Ext.Msg.alert('Eroare', 'Variabila e deja acolo');
			 dropHandlers.cancelDrop();
		} else {
	
		if (vStore.count() < 2) {
			 dropHandlers.processDrop();
		} else {
			 Ext.Msg.alert('Eroare', 'Numarul maxim de variabile a fost atins');
			 dropHandlers.cancelDrop();
		}	
		}
	},

	
	onSendToAnalysis : function (button) {
	console.log('sendtoanalysis');	
	var astore = this.getAnalysisVar().getStore();
	var mycount = 0;
	var firstv = astore.getAt(0).data.indice;
	var secv = astore.getAt(1).data.indice;
	this.getAnPanel().setLoading('Loading...');	
	var me = this;
	Ext.Ajax.request({
//        url : 'http://roda.apiary-mock.com/statistics',
		url: '../statistics',
		method : "GET",
        params : {
                variable1 : firstv,
                variable2 : secv,
                lang : translations.language
        },
        success : function(response) {
        var responseJson = Ext.decode(response.responseText);
        
            if (responseJson.success === true) {
                // whatever stuff needs to happen on success
            	var rpanel = me.getAnPanel();
            	rpanel.removeAll();
            	
            	var newpanels = me.getRResult(responseJson.data);
            	Ext.each(newpanels, function(value) {
            		rpanel.add(value);
            		rpanel.doLayout();
            	});
            		rpanel.setLoading(false);
            } else {
            	Ext.Msg.alert('Failure!', responseJson.message);

            }
     },
        failure : function(response, opts) {
                Ext.Msg.alert('Failure', response);

        }
    });	
		
},
	
	
	
onSTSendToAnalysis : function (button) {
	console.log('stsendtoanalysis');
	var astore = this.getSranalysisVar().getStore();
	var mycount = 0;
	this.getSranPanel().setLoading('Loading...');
	var firstv = astore.getAt(0).data.indice;
	var secv = astore.getAt(1).data.indice;
	var me = this;
	Ext.Ajax.request({
        url : '../statistics',
        method : "POST",
        params : {
                variable1 : firstv,
                variable2 : secv,
                lang : translations.language                
        },
        success : function(response) {
        var responseJson = Ext.decode(response.responseText);
        
            if (responseJson.success === true) {
            	var rpanel = me.getSranPanel();
            	rpanel.removeAll();
            	
            	var newpanels = me.getRResult(responseJson.data);
            	Ext.each(newpanels, function(value) {
            		rpanel.add(value);
            		rpanel.doLayout();
            	});
            	rpanel.setLoading(false);
            } else {
            	Ext.Msg.alert('Failure!', responseJson.message);
            }
     },
        failure : function(response, opts) {
                Ext.Msg.alert('Failure', response);

        }
    });	
		
},
	

	onMenuGetRDetails : function (component, event) {
		console.log('onMenuGetRDetails');
		var currentNode = this.getStudyVariables().getSelectionModel().getLastSelected();
		this.getSingledetails().setLoading('Loading...');
		var me = this;
		Ext.Ajax.request({
	        url : '../statistics',
//	        url : 'http://roda.apiary-mock.com/statistics',
	        method : "GET",
	        params : {
	                variable1 : currentNode.data.indice,
	                lang : translations.language
	        },
	        success : function(response) {
	        var responseJson = Ext.decode(response.responseText);
	        
	            if (responseJson.success === true) {
	                // whatever stuff needs to happen on success
	            	var rpanel = me.getSingledetails();
	            	rpanel.removeAll();
	            	var newpanels = me.getRResult(responseJson.data);
	            	Ext.each(newpanels, function(value) {
	            		rpanel.add(value);
	            		rpanel.doLayout();
	            	});
	            	rpanel.setLoading(false);
	            	me.getVardet().layout.setActiveItem('vardetails');
	            } else {
	            	Ext.Msg.alert('Failure!', responseJson.message);

	            }
	     },
	        failure : function(response, opts) {
	                Ext.Msg.alert('Failure', response);

	        }
	    });	
	},

	
	
	getRResult : function (json) {
		console.log('getttesult');
		var me = this;
		var panelarray = []
		Ext.each(json, function(value) {
			if (value.itemtype == 'chart') {
				if (value.charttype == 'stackedbar') {
					var panel = me.getChartStackedBar(value);
					panelarray.push(panel);
				} else if (value.charttype == 'bar') {					
					var panel = me.getChartBar(value);
					panelarray.push(panel);
				} else if (value.charttype == 'scatter') {					
					var panel = me.getChartScatter(value);
					panelarray.push(panel);
				} else if (value.charttype == 'histogram') {					
					var panel = me.getChartHistogram(value);
					panelarray.push(panel);
				}
			} else if (value.itemtype == 'paragraph') {
				var panel = me.getParagraph(value);
				panelarray.push(panel);
			} else if (value.itemtype == 'table') {
				var panel = me.getTable(value);
				panelarray.push(panel);
			}
		});
		return panelarray;
	},
	
	
	getChartStackedBar : function (value) {
		console.log('stackedbar');
		console.log(value);
		var ifields =[value.catfield];
		storefields = ifields.concat(value.datafields);
		console.log(storefields);
		console.log(value.datafields);
		var nstore = new Ext.data.JsonStore({
			fields : storefields,
			data: value.data
		});
		
		var height = 500;
		if (value.height > 100) {
			height = value.height + 50;
		}

		var panel = Ext.create('Ext.Panel', {
			title: value.title, 
			collapsible: true,
			width: 1000,
			height: height,
			bodyPadding: 5,
			layout: 'fit',
			items: {
				xtype: 'sbchart',
				store: nstore,
				catfield : value.catfield,
				datafields : value.datafields,
			}
		 });
		return panel;
	},

	getChartBar : function (value) {
		var nstore = new Ext.data.JsonStore({
			fields:['name', 'value'],
			data: value.data
		});
		var height = 500;
		if (value.height > 20) {
			height = value.height + 180;
		}
		console.log(value.height);
		console.log(height);
		var panel = Ext.create('Ext.Panel', {
			title: value.title, 
			collapsible: true,
			width: 800,
			height: height,
			bodyPadding: 5,
			layout: 'fit',
			items: {
				xtype: 'freqchart',
				store: nstore,
			}
		});
		return panel;
	},

	getChartScatter : function (value) {
		console.log('scatter');
		console.log(value.xaxistitle);
		console.log(value.yaxistitle);
		console.log(value.xfield);
		console.log(value.yfield);
		
		
		var nstore = new Ext.data.JsonStore({
			fields:[value.xfield, value.yfield],
			data: value.data
		});
		
		console.log(nstore);
		
		var height = 500;
		if (value.height > 100) {
			height = value.height + 100;
		}
		var panel = Ext.create('Ext.Panel', {
			title: value.title, 
			collapsible: true,
			width: 600,
			height: height,
			bodyPadding: 5,
			layout: 'fit',
			items: {
				xtype: 'scatter',
				store: nstore,
				xaxistitle : value.xaxistitle,
				yaxistitle : value.yaxistitle,
				xfields: value.xfield,
				yfields: value.yfield,
			}
		});
		return panel;		
	},

	getChartHistogram : function (value) {
		console.log(value);
		console.log(value.data[0]);
		var nstore = new Ext.data.JsonStore({
			fields:['breaks', 'counts'],
			data: value.data[0]
		});
		var height = 500;
		if (value.height > 100) {
			height = value.height + 100;
		}
		var panel = Ext.create('Ext.Panel', {
			title: value.title, 
			collapsible: true,
			width: 600,
			height: height,
			bodyPadding: 5,
			layout: 'fit',
			items: {
				xtype: 'histogram',
				store: nstore,
			}
		});
		return panel;		
	},
	
	getParagraph : function (data) {
		console.log(data);
		var panel = Ext.create('Ext.Panel', {
	 		collapsible: true,
	 		title: data.title, 
	 //		width: 600,
	 		bodyPadding: 5,
	 		html: data.content
	 	});
		return panel;
	},

	
	getTable : function (data) {
		var mytable = '<table class="jsdatatable">';
		for (var i in data.data) {
			mytable += '<tr>';
			for (var j in data.data[i]) {
				if (data.headerRow == 1 && i == 0) {
					mytable += '<th>';
					mytable += data.data[i][j];
					mytable += '</th>';
				} else {
					if (data.headerCol == 1 && j == 0) {
						mytable += '<th>';
						mytable += data.data[i][j];
						mytable += '</th>';
					} else {
						mytable += '<td>';
						mytable += data.data[i][j];
						mytable += '</td>';
					}

				}

			}
			mytable += '</tr>';
		}
		mytable += '</table>';
		data.renderedtable = mytable;
		var t = new Ext.Template(
		     '<div class="tablecontainer">{renderedtable}</div>',
		     
		{
			compiled: true
		});

		var tfinal =  t.apply(data);
		
		var panel = Ext.create('Ext.Panel', {
	 		collapsible: true,
	 		title: data.title, 
	 		//width: 600,
	 		bodyPadding: 5,
	 		html : tfinal,
	 	});
		return panel;
	},
	
	
	onSTMenuGetRDetails : function (component, event) {
		console.log('stmenugetrdetails');
		var currentNode = this.getSrstudyVariables().getSelectionModel().getLastSelected();
		this.getSrvarPanel().setLoading('Loading...');	
		console.log(currentNode);
		var me = this;
		Ext.Ajax.request({
	        url : '../statistics',
//	        url : 'http://roda.apiary-mock.com/statistics',
	        method : "GET",
	        params : {
	                variable1 : currentNode.data.indice,
	                lang : translations.language
	        },
	        success : function(response) {
	        var responseJson = Ext.decode(response.responseText);
	        
	            if (responseJson.success === true) {
	            	var rpanel = me.getStsingledetails();
	            	rpanel.removeAll();

	            	var newpanels = me.getRResult(responseJson.data);
	            	Ext.each(newpanels, function(value) {
	            		rpanel.add(value);
	            		rpanel.doLayout();
	            	});
	            	rpanel.setLoading(false);
	            	me.getServardet().layout.setActiveItem('srvardetails');
	            } else {
	            	Ext.Msg.alert('Failure!', responseJson.message);

	            }
	     },
	        failure : function(response, opts) {
	                Ext.Msg.alert('Failure', response);

	        }
	    });	
	},
	
	
	
	
onSTMenuAddAnal : function (component, event) {
	console.log('stmenuaddanal');
	var currentNode = this.getStudyVariables().getSelectionModel().getLastSelected();
	var mygrid = this.getSranalysisVar();

	
	
	var vStore = this.getSranalysisVar().getStore();
	var isit = vStore.findExact('name',currentNode.data.name);
	console.log(isit);
	if (isit > -1) {
		 Ext.Msg.alert('Eroare', 'Variabila e deja acolo');
	} else {
		if (vStore.count() < 2) {
			vStore.add(currentNode);
			vStore.commitChanges();
			this.getServardet().layout.setActiveItem('varanalyze');
		} else {
			 Ext.Msg.alert('Eroare', 'Numarul maxim de variabile a fost atins');
		}	
	}			

},
	
	
	
	
	onMenuAddAnal : function (component, event) {
		console.log('menuaddanal');		
		var currentNode = this.getStudyVariables().getSelectionModel().getLastSelected();
		console.log(currentNode);
		var mygrid = this.getAnalysisVar();
		console.log(mygrid);
		var vStore = this.getAnalysisVar().getStore();
		//avem store, avem variabila. Sa vedem daca nu e deja acolo
		var isit = vStore.findExact('name',currentNode.data.name);
		console.log(isit);
		if (isit > -1) {
			 Ext.Msg.alert('Eroare', 'Variabila e deja acolo');
		} else {
		//hai sa vedem cate elemente avem in store		
		if (vStore.count() < 2) {
			vStore.add(currentNode);
			vStore.commitChanges();
			//daca adaugam la store, mutam tabul
			this.getVardet().layout.setActiveItem('varanalyze');
		} else {
			 Ext.Msg.alert('Eroare', 'Numarul maxim de variabile a fost atins');
		}	
		}

	},
	
	
	
	
	onVariablesGridContextMenu : function(component, record, item, index, e) {
		console.log('variable menu');
		 e.stopEvent();
		 console.log(record.data.type);
		   if (!this.itemmenu) {
			   this.itemmenu = Ext.create('databrowser.view.VariableContextMenu');
		   }
		   this.itemmenu.showAt(e.getXY());
	},
	
	onSTVariablesGridContextMenu : function(component, record, item, index, e) {
		console.log('stvariable menu');
		e.stopEvent();
		 console.log(record.data.type);
		   if (!this.itemmenu) {
			   this.itemmenu = Ext.create('databrowser.view.STVariableContextMenu');
		   }
		   this.itemmenu.showAt(e.getXY());
	}	

});
