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
	        {
	        	ref: 'freqChart',
	        	selector: 'studyview freqchart#ilfreqchart'
	        	
	        },
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
	        	selector: 'studyseriesview panel#vardetails'
	        },
	        
	        {
	            ref: 'SrstudyVariables',
	            selector: 'studyseriesview gridpanel#studyseriesvariables'
	        },
	        {
	            ref: 'SranalysisVar',
	            selector: 'studyseriesview gridpanel#analysisvar'
	        }, 
	        {
	        	ref: 'SrfreqChart',
	        	selector: 'studyseriesview freqchart#ilfreqchart'
	        	
	        },
	        {
	        	ref: 'SranPanel',
	        	selector: 'studyseriesview panel#analResults'
	        }
	        
	        
	        
	        ],
	
	init : function(application) {
		this.control({
			"studyview gridpanel#studyvariables" : {
				 itemcontextmenu : this.onVariablesGridContextMenu,
//				 cellclick : this.onVarCellClick
				 cellclick : this.onMenuGetRDetails
				 
			},

			"studyseriesview gridpanel#studyseriesvariables" : {
				 itemcontextmenu : this.onSTVariablesGridContextMenu,
//				 cellclick : this.onVarCellClick
				 cellclick : this.onSTMenuGetRDetails
				 
			},
			
			
			'variablecontextmenu menuitem#vardetails' : {
				 click: this.onMenuGetRDetails	
			},
			'variablecontextmenu menuitem#addanal' : {
				 click: this.onMenuAddAnal	
			},
			
			'stvariablecontextmenu menuitem#vardetails' : {
				 click: this.onSTMenuGetRDetails	
			},
			'stvariablecontextmenu menuitem#addanal' : {
				 click: this.onSTMenuAddAnal	
			},
			
			
			
			"studyview button#sendToAnalysis" :{
				click: this.onSendToAnalysis
			},
			'studyview gridpanel#analysisvar' : {
				deleteRecord : this.onGridDeleteAction
			},

			"studyseriesview button#sendToAnalysis" :{
				click: this.onSTSendToAnalysis
			},
			'studyseriesview gridpanel#analysisvar' : {
				deleteRecord : this.onGridDeleteAction
			},
			
			
			
		});

	},

	onGridDeleteAction : function(grid, record, rowIndex, row) {
		grid.getStore().removeAt(rowIndex);
	},
	
//	onSendToAnalysis : function (button) {
//		var astore = this.getAnalysisVar().getStore();
//		var mycount = 0;
//		var firstv = astore.getAt(0).data.indice;
//		var secv = astore.getAt(1).data.indice;
//		var me = this;
//		Ext.Ajax.request({
//            url : 'http://roda.apiary-mock.com/statistics',
//            method : "POST",
//            params : {
//                    variable1 : firstv,
//                    variable2 : secv
//            },
//            success : function(response) {
//            var responseJson = Ext.decode(response.responseText);
//                if (responseJson.success === true) {
//                    // whatever stuff needs to happen on success
//                	Ext.each(responseJson.data, function(value) {
//                		 var myhtml = me.generateAnOb(value);
//                		 var rpanel = me.getAnPanel();
//                		 var oldhtml = rpanel.html; 
//                		 console.log(oldhtml);
//                		 
//                		 if (oldhtml) {	
//                			 rpanel.update(oldhtml + myhtml);
//                		 } else {
//                			 rpanel.update(myhtml);
//                		 }
//                		 rpanel.doLayout();
//                	});
//                	
//                
//                } else {
//                    RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);
//
//                }
//         },
//            failure : function(response, opts) {
//                    Ext.Msg.alert('Failure', response);
//
//            }
//        });	
//			
//	},
	

	onSendToAnalysis : function (button) {
	var astore = this.getAnalysisVar().getStore();
	var mycount = 0;
	var firstv = astore.getAt(0).data.indice;
	var secv = astore.getAt(1).data.indice;
	var me = this;
	Ext.Ajax.request({
//        url : 'http://roda.apiary-mock.com/statistics',
		url: '../../j/statistics',
		method : "GET",
        params : {
                variable1 : firstv,
                variable2 : secv
        },
        success : function(response) {
        var responseJson = Ext.decode(response.responseText);
        
            if (responseJson.success === true) {
                // whatever stuff needs to happen on success
            	var rpanel = me.getAnPanel();
            	rpanel.removeAll();
            	Ext.each(responseJson.data, function(value) {
            		console.log(value); 
            		if (value.itemtype == 'chart') {
            			if (value.charttype == 'stackedbar') {
            				console.log('stacked bar');	
            				var catfield = value.catfield;
            				var datafield1 = value.datafields;
            				
            				var nstore = new Ext.data.JsonStore({
                		    	fields:[value.catfield, value.datafield1, value.datafield2],
                 		        data: value.data
                		    });
            				var height = 500;
            				if (value.height > 100) {
            					height = value.height + 50;
            				}
            				var panel = Ext.create('Ext.Panel', {
                		       title: value.title, 
                		       collapsible: true,
                		       width: 600,
                		       height: height,
                		       bodyPadding: 5,
                		       layout: 'fit',
                		       items: {
                		    	 xtype: 'sbchart',
                		    	 store: nstore,
                		    	 catfield : value.catfield,
                		    	 datafields : [value.datafield1, value.datafield2],
                		       	}
                     		 	});
                     		 rpanel.add(panel);          
            			} else if (value.charttype == 'bar') {	
            				console.log('bar');
            				var nstore = new Ext.data.JsonStore({
            					fields:['name', 'value'],
            					data: value.data
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
            						xtype: 'freqchart',
            						store: nstore,
            					}
            				});
            				rpanel.add(panel);            			
            			}
            		} else if   (value.itemtype == 'paragraph') {
               		 	var panel = Ext.create('Ext.Panel', {
               		 		collapsible: true,
               		 		title: value.title, 
               		 		width: 600,
               		 		bodyPadding: 5,
               		 		html : value.content
               		 	});
            			
               		 	rpanel.add(panel);
            		} else {
            		var myhtml = me.generateAnOb(value);
            		 //incercam sa facem un nou panou
            		 var panel = Ext.create('Ext.Panel', {
            			   collapsible: true,
            		       title: value.title,
            		       width: 600,
            		       bodyPadding: 5,
            		       html : myhtml
            		  });
            		
            		 rpanel.add(panel);
            		}
            		
            		
            		 rpanel.doLayout();
            	});
            	
            
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
	var astore = this.getSranalysisVar().getStore();
	var mycount = 0;
	var firstv = astore.getAt(0).data.indice;
	var secv = astore.getAt(1).data.indice;
	var me = this;
	Ext.Ajax.request({
        url : '../../j/statistics',
        method : "POST",
        params : {
                variable1 : firstv,
                variable2 : secv
        },
        success : function(response) {
        var responseJson = Ext.decode(response.responseText);
        
            if (responseJson.success === true) {
                // whatever stuff needs to happen on success
            	var rpanel = me.getSranPanel();
            	rpanle.removeAll();
            	Ext.each(responseJson.data, function(value) {
            		console.log(value); 
            		if (value.itemtype == 'chart') {
            			console.log('chart starting------');
            			if (value.charttype == 'stackedbar') {
            				console.log('stacked bar');	
            				
            				var nstore = new Ext.data.JsonStore({
                		    	fields:[value.catfield, value.datafield1, value.datafield2],
                 		        data: value.data
                		    });
            				var height = 500;
            				if (value.height > 100) {
            					height = value.height + 50;
            				}
            				var panel = Ext.create('Ext.Panel', {
                		       title: value.title, 
                		       collapsible: true,
                		       width: 600,
                		       height: height,
                		       bodyPadding: 5,
                		       layout: 'fit',
                		       items: {
                		    	 xtype: 'sbchart',
                		    	 store: nstore,
                		    	 catfield : value.catfield,
                		    	 datafields : [value.datafield1, value.datafield2],
                		       	}
                     		 	});
                     		 rpanel.add(panel);             				
            			} else if (value.charttype == 'bar') {	
            				console.log('bar');
            				var nstore = new Ext.data.JsonStore({
            					fields:['name', 'value'],
            					data: value.data
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
            						xtype: 'freqchart',
            						store: nstore,
            					}
            				});
            				rpanel.add(panel);            			
            			}
            		} else if   (value.itemtype == 'paragraph') {
               		 	var panel = Ext.create('Ext.Panel', {
               		 		collapsible: true,
               		 		title: value.title, 
               		 		width: 600,
               		 		bodyPadding: 5,
               		 		html : value.content
               		 	});
            			
               		 	rpanel.add(panel);
            		} else {
            		var myhtml = me.generateAnOb(value);
            		 //incercam sa facem un nou panou
            		 var panel = Ext.create('Ext.Panel', {
            			   collapsible: true,
            		       title: value.title,
            		       width: 600,
            		       bodyPadding: 5,
            		       html : myhtml
            		  });
            		
            		 rpanel.add(panel);
            		}
            		
            		
            		 rpanel.doLayout();
            	});
            	
            
            } else {
            	Ext.Msg.alert('Failure!', responseJson.message);

            }
     },
        failure : function(response, opts) {
                Ext.Msg.alert('Failure', response);

        }
    });	
		
},
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	generateAnOb : function (props) {
		console.log('we are where we are supposed to be');
		console.log(props);
		if (props.itemtype == 'table') {
			//hai sa vedem cum facem tabelul
			var mytable = '<table>';
			//console.log(mytable);
			for (var i in props.data) {
				mytable += '<tr>';
				for (var j in props.data[i]) {
					if (props.headerRow == 1 && i == 0) {
						mytable += '<th>';
						mytable += props.data[i][j];
						mytable += '</th>';
					} else {
						if (props.headerCol == 1 && j == 0) {
							mytable += '<th>';
							mytable += props.data[i][j];
							mytable += '</th>';
						} else {
							mytable += '<td>';
							mytable += props.data[i][j];
							mytable += '</td>';
						}

					}

				}
				mytable += '</tr>';
			}
			mytable += '</table>';
			props.renderedtable = mytable;
			var t = new Ext.Template(
			     '<div class="tablecontainer">{renderedtable}</div>',
			     
			{
				compiled: true
			});

			var tfinal =  t.apply(props);
			return tfinal;
		} else if (props.itemtype == 'paragraph') {
			var t = new Ext.Template(
			        			     '<div class="partitle">{title}</div>',
			        			     '<div class="parcontent">{content}</div>',
			        			{
			        				compiled: true
			        			});
			        			return t.apply(props);
			
		} else if (props.itemtype == 'chart') {	
			var t = new Ext.Template(
			        			     '<div class="charttitle">{title}</div>',                    
			        			{
			        				compiled: true
			        			});
			        			return t.apply(props);
			
		}
		
	},

	onMenuGetRDetails : function (component, event) {
		var currentNode = this.getStudyVariables().getSelectionModel().getLastSelected();
		console.log(currentNode);
		var ilfreqchart = this.getFreqChart();
		console.log(ilfreqchart);
		var me = this;
		Ext.Ajax.request({
	        url : '../../j/statistics',
//	        url : 'http://roda.apiary-mock.com/statistics',
	        method : "GET",
	        params : {
	                variable1 : currentNode.data.indice,
	        },
	        success : function(response) {
	        var responseJson = Ext.decode(response.responseText);
	        
	            if (responseJson.success === true) {
	                // whatever stuff needs to happen on success
	            	var rpanel = me.getSingledetails();
	            	rpanel.removeAll();
	            	Ext.each(responseJson.data, function(value) {
	            		console.log(value); 
	            		if (value.itemtype == 'chart') {
	            			console.log('chart starting------');
	            			if (value.charttype == 'stackedbar') {
	            				console.log('stacked bar');	
	            				var nstore = new Ext.data.JsonStore({
	                		    	fields:[value.catfield, value.datafield1, value.datafield2],
	                 		        data: value.data
	                		    });
	            				var height = 500;
	            				if (value.height > 100) {
	            					height = value.height + 50;
	            				}
	            				var panel = Ext.create('Ext.Panel', {
	                		       title: value.title, 
	                		       collapsible: true,
	                		       width: 600,
	                		       height: height,
	                		       bodyPadding: 5,
	                		       layout: 'fit',
	                		       items: {
	                		    	 xtype: 'sbchart',
	                		    	 store: nstore,
	                		    	 catfield : value.catfield,
	                		    	 datafields : [value.datafield1, value.datafield2],
	                		       	}
	                     		 	});
	                     		 rpanel.add(panel); 	            				
	            			} else if (value.charttype == 'bar') {	
	            				console.log('bar');
	            				var nstore = new Ext.data.JsonStore({
	            					fields:['name', 'value'],
	            					data: value.data
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
	            						xtype: 'freqchart',
	            						store: nstore,
	            					}
	            				});
	            				rpanel.add(panel);            			
	            			}
	            		} else if   (value.itemtype == 'paragraph') {
	               		 	var panel = Ext.create('Ext.Panel', {
	               		 		collapsible: true,
	               		 		title: value.title, 
	               		 		width: 600,
	               		 		bodyPadding: 5,
	               		 		html : value.content
	               		 	});
	            			
	               		 	rpanel.add(panel);
	            		} else {
	            		var myhtml = me.generateAnOb(value);
	            		 //incercam sa facem un nou panou
	            		 var panel = Ext.create('Ext.Panel', {
	            			   collapsible: true,
	            		       title: value.title,
	            		       width: 600,
	            		       bodyPadding: 5,
	            		       html : myhtml
	            		  });
	            		
	            		 rpanel.add(panel);
	            		}
	            		 rpanel.doLayout();
	            	});
	            	
	            
	            } else {
	            	Ext.Msg.alert('Failure!', responseJson.message);

	            }
	     },
	        failure : function(response, opts) {
	                Ext.Msg.alert('Failure', response);

	        }
	    });	
	},
	
	onMenuGetVdetails : function (component, event) {
				var currentNode = this.getStudyVariables().getSelectionModel().getLastSelected();
				console.log(currentNode);
				var ilfreqchart = this.getFreqChart();
				console.log(ilfreqchart);
				var vStore = Ext.StoreManager.get('VariableStore');
				vStore.load({
					id : currentNode.data.indice, // set the id here
					scope : this,
					callback : function(records, operation, success) {
						console.log('callback success');
						if (success) {
							var rec = vStore.first();
							console.log(rec);
							if (rec.get('nrfreq') == 0) {
								ilfreqchart.getEl().hide();
							} else {
								console.log('inelse');
							ilfreqchart.bindStore(rec.otherStatisticsStore);
							ilfreqchart.surface.add(
							{
							    type: 'text',
							    text: rec.name + ' ' +rec.label,
							    font: '12px Arial',
							    x: 50,
							    y: 0,
							    width: 100,
							    height: 100 
							});
							
							
							console.log(rec.otherStatisticsStore);
							}
						}
					}
				});
	},

	
	
	onSTMenuGetRDetails : function (component, event) {
		var currentNode = this.getSrstudyVariables().getSelectionModel().getLastSelected();
		console.log(currentNode);
		var ilfreqchart = this.getFreqChart();
		console.log(ilfreqchart);
		var me = this;
		Ext.Ajax.request({
	        url : '../../j/statistics',
//	        url : 'http://roda.apiary-mock.com/statistics',
	        method : "GET",
	        params : {
	                variable1 : currentNode.data.indice,
	        },
	        success : function(response) {
	        var responseJson = Ext.decode(response.responseText);
	        
	            if (responseJson.success === true) {
	                // whatever stuff needs to happen on success
	            	var rpanel = me.getStstsingledetails();
	            	rpanel.removeAll();
	            	Ext.each(responseJson.data, function(value) {
	            		console.log(value); 
	            		if (value.itemtype == 'chart') {
	            			console.log('chart starting------');
	            			if (value.charttype == 'stackedbar') {
	            				console.log('stacked bar');
	            				
	            				var nstore = new Ext.data.JsonStore({
	                		    	fields:[value.catfield, value.datafield1, value.datafield2],
	                 		        data: value.data
	                		    });
	            				var height = 500;
	            				if (value.height > 100) {
	            					height = value.height + 50;
	            				}
	            				var panel = Ext.create('Ext.Panel', {
	                		       title: value.title, 
	                		       collapsible: true,
	                		       width: 600,
	                		       height: height,
	                		       bodyPadding: 5,
	                		       layout: 'fit',
	                		       items: {
	                		    	 xtype: 'sbchart',
	                		    	 store: nstore,
	                		    	 catfield : value.catfield,
	                		    	 datafields : [value.datafield1, value.datafield2],
	                		       	}
	                     		 	});
	                     		 rpanel.add(panel); 	            				
	            			} else if (value.charttype == 'bar') {	
	            				console.log('bar');
	            				var nstore = new Ext.data.JsonStore({
	            					fields:['name', 'value'],
	            					data: value.data
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
	            						xtype: 'freqchart',
	            						store: nstore,
	            					}
	            				});
	            				rpanel.add(panel);            			
	            			}
	            		} else if   (value.itemtype == 'paragraph') {
	               		 	var panel = Ext.create('Ext.Panel', {
	               		 		collapsible: true,
	               		 		title: value.title, 
	               		 		width: 600,
	               		 		bodyPadding: 5,
	               		 		html : value.content
	               		 	});
	            			
	               		 	rpanel.add(panel);
	            		} else {
	            		var myhtml = me.generateAnOb(value);
	            		 //incercam sa facem un nou panou
	            		 var panel = Ext.create('Ext.Panel', {
	            			   collapsible: true,
	            		       title: value.title,
	            		       width: 600,
	            		       bodyPadding: 5,
	            		       html : myhtml
	            		  });
	            		
	            		 rpanel.add(panel);
	            		}
	            		 rpanel.doLayout();
	            	});
	            	
	            
	            } else {
	            	Ext.Msg.alert('Failure!', responseJson.message);

	            }
	     },
	        failure : function(response, opts) {
	                Ext.Msg.alert('Failure', response);

	        }
	    });	
	},
	
	
	
	onSTMenuGetVdetails : function (component, event) {
		var currentNode = this.getSrstudyVariables().getSelectionModel().getLastSelected();
		console.log(currentNode);
		var ilfreqchart = this.getSrfreqChart();
		var vStore = Ext.StoreManager.get('VariableStore');
		vStore.load({
			id : currentNode.data.indice, // set the id here
			scope : this,
			callback : function(records, operation, success) {
				console.log('callback success');
				if (success) {
					var rec = vStore.first();
					console.log(rec);
					if (rec.get('nrfreq') == 0) {
						ilfreqchart.getEl().hide();
					} else {
						console.log('inelse');
					ilfreqchart.bindStore(rec.otherStatisticsStore);
					ilfreqchart.surface.add(
					{
					    type: 'text',
					    text: rec.name + ' ' +rec.label,
					    font: '12px Arial',
					    x: 50,
					    y: 0,
					    width: 100,
					    height: 100 
					});
					
					
					console.log(rec.otherStatisticsStore);
					}
				}
			}
		});
},
	
	
onSTMenuAddAnal : function (component, event) {
	var currentNode = this.getSrstudyVariables().getSelectionModel().getLastSelected();
	console.log(currentNode);

	var mygrid = this.getSranalysisVar();
	console.log(mygrid);
	var vStore = this.getSranalysisVar().getStore();
	vStore.add(currentNode);
	vStore.commitChanges();


},
	
	
	
	
	onMenuAddAnal : function (component, event) {
		var currentNode = this.getStudyVariables().getSelectionModel().getLastSelected();
		console.log(currentNode);

		var mygrid = this.getAnalysisVar();
		console.log(mygrid);
		var vStore = this.getAnalysisVar().getStore();
		vStore.add(currentNode);
		vStore.commitChanges();


	},
	
	
	
	
	onVariablesGridContextMenu : function(component, record, item, index, e) {
		 e.stopEvent();
		 console.log(record.data.type);
		   if (!this.itemmenu) {
			   this.itemmenu = Ext.create('databrowser.view.VariableContextMenu');
		   }
		   this.itemmenu.showAt(e.getXY());
	},
	
	onSTVariablesGridContextMenu : function(component, record, item, index, e) {
		 e.stopEvent();
		 console.log(record.data.type);
		   if (!this.itemmenu) {
			   this.itemmenu = Ext.create('databrowser.view.STVariableContextMenu');
		   }
		   this.itemmenu.showAt(e.getXY());
	}	

});
