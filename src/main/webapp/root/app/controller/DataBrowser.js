Ext.define('databrowser.controller.DataBrowser', {
	extend : 'Ext.app.Controller',
//	views : [ 'VariableView' ],
	
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
	        }
	    ],
	
	init : function(application) {
		this.control({
			"studyview gridpanel#studyvariables" : {
				 itemcontextmenu : this.onVariablesGridContextMenu,
//				 cellclick : this.onVarCellClick
				 cellclick : this.onMenuGetVdetails
				 
			},
			'variablecontextmenu menuitem#vardetails' : {
				 click: this.onMenuGetVdetails	
			},
			'variablecontextmenu menuitem#addanal' : {
				 click: this.onMenuAddAnal	
			},
			
			"studyview button#sendToAnalysis" :{
				click: this.onSendToAnalysis
			},
			'studyview gridpanel#analysisvar' : {
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
        url : 'http://roda.apiary-mock.com/statistics',
        method : "POST",
        params : {
                variable1 : firstv,
                variable2 : secv
        },
        success : function(response) {
        var responseJson = Ext.decode(response.responseText);
        
            if (responseJson.success === true) {
                // whatever stuff needs to happen on success
            	var rpanel = me.getAnPanel();
            	Ext.each(responseJson.data, function(value) {
            		console.log(value); 
            		if (value.itemtype == 'chart') {
            			
           		    var nstore = new Ext.data.JsonStore({
           		    	fields:['name', 'value'],
            		        data: value.data
           		    });
            			
               		 var panel = Ext.create('Ext.Panel', {
          		       title: value.title, 
          		       collapsible: true,
          		       width: 600,
          		       height: 600,
          		       bodyPadding: 5,
          		       layout: 'fit',
          		       items: {
          		    	 xtype: 'freqchart',
          		    	 store: nstore,
          		       }
          		       
          		       
               		 });

            		 rpanel.add(panel);            			

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
                RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);

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
	}

});