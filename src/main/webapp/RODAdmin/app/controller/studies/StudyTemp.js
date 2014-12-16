Ext.define('RODAdmin.controller.studies.StudyTemp', {
    extend : 'Ext.app.Controller',
    stores : [
              'studies.TempStudy', 
	          'studies.CBEditor.PrincipalInv', 
			  'studies.CBEditor.Funder',
			  'studies.CBEditor.Person',
			  'studies.CBEditor.Org',
			  'studies.CBEditor.Qdesign',
			  'studies.CBEditor.Questions',
			  'studies.CBEditor.Qtranslation',
			  'studies.CBEditor.Qsamplingresp',
			  'studies.CBEditor.DataCollectionResp',
		      'studies.CBEditor.CollectionMode',
		      'studies.CBEditor.DPResp',
		      'studies.CBEditor.Qcoderesp',
		      'studies.CBEditor.Qcatresp',
		      'studies.CBEditor.Lang',
		      'studies.CBEditor.Variables',
		      'studies.CBEditor.TransQuestions',
		      'studies.CBEditor.TransQcatresp',
		      'studies.CBEditor.TransQcoderesp',
		      'studies.CBEditor.ConceptResp',
		      'studies.CBEditor.ConceptsDisp',
		      'studies.CBEditor.Qmissing',
		      'studies.CBEditor.Files',
		      'studies.CBEditor.FileTypes',
		      'studies.CBEditor.SRights',              
              ],
    
    views : [
            'RODAdmin.view.studies.StudyItemstempview',
            'RODAdmin.view.studies.StudiesTemp',
            'RODAdmin.view.studies.tempdetails.sTempDataCollection',
            'RODAdmin.view.studies.tempdetails.sTempConcepts',
            'RODAdmin.view.studies.tempdetails.sTempDataProd',
            'RODAdmin.view.studies.tempdetails.sTempFunding',
            'RODAdmin.view.studies.tempdetails.sTempProposal',
            'RODAdmin.view.studies.tempdetails.sTempQuestions',
            'RODAdmin.view.studies.tempdetails.sTempProcessInfo',
    ],
    /**
	 * @cfg
	 */
    refs : [
            {
                ref : 'iconview',
                selector : 'studyitemstempview grid#sticonview'
            },
            {
            	ref: 'prinvdisplay',
            	selector: 'sproposal grid#prinvdisplay'
            },
            {
            	ref: 'cnrespdisplay',
            	selector:'sconcepts grid#cnrespdisplay'
            },
            {
            	ref: 'qsamplingrespdisplay',
            	selector:'sdatacollection grid#qsamplingrespdisplay'
            },        	
            {
            	ref: 'dcrespdisplay',
            	selector:'sdatacollection grid#dcrespdisplay'
            },       	
            {
            	ref: 'dprespdisplay',
            	selector:'sdataprod grid#dprespdisplay'
            },        	
            {
            	ref: 'variabledisplay',
            	selector:'sdataprod grid#variabledisplay'
            },       	
            {
            	ref: 'filesdisplay',
            	selector:'sdataprod grid#filesdisplay'
            },        	
            {
            	ref: 'fundvdisplay',
            	selector:'sfunding grid#fundvdisplay'
            },        	
            {
            	ref: 'qdesigndisplay',
            	selector:'squestions grid#qdesigndisplay' 
            },        	
            {
            	ref: 'questionsdisplay',
            	selector:'squestions grid#questionsdisplay'     
            },        	
            {
            	ref: 'qtranslationdisplay',
            	selector:'squestions grid#qtranslationdisplay'    
            },        
            {
            	ref: 'translationsdisplay',
            	selector:'squestions grid#translationsdisplay'	            
            },            
            {
            	ref : 'tempsdetails',
            	selector : 'studiestemp panel#stdetailstempcontainer'
            	
            },
            {
            	ref: 'tproposalpreview',
            	selector : 'stempproposal panel#stempproposalpreview'
            },
            {
            	ref: 'tfundingpreview',
            	selector : 'stempfunding panel#stempfundingpreview'
            },
            {
            	ref: 'tconceptspreview',
            	selector : 'stempconcepts panel#stempconceptspreview'
            },
           {
            	ref: 'tquestionspreview',
            	selector : 'stempquestions panel#stempquestionspreview'
           },

            {
            	ref: 'tdatacollectionpreview',
            	selector : 'stempdatacollection panel#stempdatacollectionpreview'
           },

            {
            	ref: 'tdataproductionpreview',
            	selector : 'stempdataprod panel#stempdataprodpreview'
           },
           {
        	   ref: 'tprocessinfo',
        	   selector: 'stprocessinfo panel#tprocessinfopreview'
        	   
           }
           
           ],
            
    init : function(application) {
       	    this.control({
    	        "studyitemstempview grid#sticonview" : {
    	            selectionchange : this.onSelectionChange,
    	        },
    	        'studiestemp toolbar#studyedittoolbar button#edit-tstudy' : {
    	        	click : this.onEditTempStudy
    	        }, 
    	        "studiestemp toolbar button#add-study" : {
    	            /**
    				 * @listener studies-toolbar-button-tree-view-click triggered-by:
    				 *           {@link RODAdmin.view.studies.Studies Studies}
    				 *           toolbar button#tree-view
    				 *           {@link #onTreeViewClick}
    				 */	
    		        click : this.onAddStudyClick
    	        },
    	        'studiestemp toolbar#studyedittoolbar button#convert-tstudy' : {
    	      //  	click : this.onConvertTempStudy
    	        	click : this.convertStudyTry
    	        }, 
    	        'studiestemp toolbar#studyedittoolbar button#delete-tstudy' : {
    	    	        	click : this.deleteStudy
      	        },     	        
    	        'convertconfirm button#cancel' : {
    	        	click : this.onButtonCancel
    	        },
    	        'convertconfirm button#convert' : {
    	        	click : this.onButtonConvert
    	        }
    	        
    	        
       	    });
        	this.listen({
                controller: {
                    '*': {
                        controllerStudiestempInitView: this.initView
                    }
                }
       		});
    },

    initView : function() {
    	console.log('InitView temp');	
    	this.getIconview().getStore().load();
    	
    	Ext.History.add('menu-studiestemp');
    },
    
    onButtonCancel : function(button, e, options) {
    	 button.up('window').close();
    }, 

    onButtonConvert : function(button, e, options) {
    	this.onConvertTempStudy();
    	var srec = this.getIconview().getSelectionModel().getLastSelected();
    	var myid = srec.data.indice;
    	
    	console.log(myid);
    	
    	button.up('window').close();
    }, 
    
    deleteStudy : function (button, e, options) {
    	console.log('delete study');
    	var srec = this.getIconview().getSelectionModel().getLastSelected();
    	var myid = srec.data.indice;
	    var me = this;
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the temporary study ' + srec.data.id
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    Ext.Ajax.request({
			        url : RODAdmin.util.Globals.baseurl + '/userjson/cmsfiledrop',
			        method : "POST",
			        params : {
				        fileid : myid
			        },
			        success : function() {
			        	me.getIconview().store.load();
			        	RODAdmin.util.Alert.msg('Success!', 'News item deleted.');

			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
	    e.stopEvent();
	}, 
	
	
    convertStudyTry : function(button, e, options) {
    	console.log('convertStudyTry');
    	var srec = this.getIconview().getSelectionModel().getLastSelected();
    	var myid = srec.data.indice;
		Ext.Ajax.request({
			url: '/roda/cmsfilecontent/'+myid,
			waitTitle: 'Connecting',
			waitMsg: 'Receveing data...',                                     
			method : 'GET',
			scope:this,
			success : function(response, opts) {
				var studyFile = Ext.JSON.decode(response.responseText);
				console.log (studyFile);
				Ext.data.Connection.prototype.useDefaultXhrHeader = false;
				Ext.Ajax.request({
					url: 'http://localhost/ddi/checkjson',
					waitTitle: 'Connecting',
					waitMsg: 'Sending data...',                                     
					params: {
						"data" : Ext.JSON.encode(studyFile),
						"username" : RODAdmin.util.Globals.username
					},
					scope:this,
					method: 'POST',  
					success : function(response, opts) {
						console.log ('success');
						console.log (response.responseText);					

						var resp = Ext.JSON.decode(response.responseText);
						console.log (resp);
						if (resp.success) {
							console.log ('response success');
							// acu sa vedem daca avem erori
	

							var win = Ext.WindowMgr.get('convertconfirm');
							if (!win) {
								win = Ext.create('RODAdmin.view.studies.CBEditor.ConvertConfirm');
							}
							resp.result.data = studyFile;
							win.down('panel#convertstatus').update(resp.result);
							for (i = 0; i < resp.result.errors.length; i++) {
								console.log(resp.result.errors[i]);
								if (resp.result.errors[i].type == 'fatal') {
									win.down('button#convert').disable();
								}
							}
							win.show();

												
						} else {
							console.log ('error');
							RODAdmin.util.Util.showErrorMsg(response.message);
						}
					},
					failure : function(response, action) {
						console.log ('failure');
						console.log(response);
						switch (action.failureType) {
							case Ext.form.action.Action.CLIENT_INVALID:
								Ext.Msg.alert('Failure', 'Form fields may must be submitted with invalid values');
								break;
							case Ext.form.action.Action.CONNECT_FAILURE:
								Ext.Msg.alert('Failure', 'doesn\'t work');
								break;
							case Ext.form.action.Action.SERVER_INVALID:
								Ext.Msg.alert('Failure', action.result.msg);
								break;
						}
					}
				});
			}
		});

    },
    
    
    onConvertTempStudy : function(button, e, options) {
    	var srec = this.getIconview().getSelectionModel().getLastSelected();
    	var myid = srec.data.indice;
    	
		Ext.Ajax.request({
			url: '/roda/cmsfilecontent/'+myid,
			waitTitle: 'Connecting',
			waitMsg: 'Receveing data...',                                     
			method : 'GET',
			scope:this,
			success : function(response, opts) {
				console.log ('success');
//				console.log (response.responseText);					
				var resp = Ext.JSON.decode(response.responseText);
				console.log (resp);
						//aici incarcam jsonul
				Ext.data.Connection.prototype.useDefaultXhrHeader = false;
				Ext.Ajax.request({
					url: 'http://localhost/ddi/getjson',
					waitTitle: 'Connecting',
					waitMsg: 'Sending data...',                                     
					params: {
						"data" : Ext.JSON.encode(resp),
						"username" : RODAdmin.util.Globals.username
					},
					scope:this,
					//				withCredentials: false,
					//				jsonData: allmydata,
					method: 'POST',  
					success : function(response, opts) {
						console.log ('success');
						console.log (response.responseText);					
						var resp = Ext.JSON.decode(response.responseText);
						console.log (resp);
						if (resp.success) {
							console.log ('response success');
							this.getStudyaddmain().setMode('edit');
							this.getStudyaddmain().setEditindex(resp.id);
							this.getTempstudygrid().store.load();
							console.log (resp.id);					
						} else {
							console.log ('error');
							RODAdmin.util.Util.showErrorMsg(response.message);
						}
					},
					failure : function(response, action) {
						console.log ('failure');
						console.log(response);
						switch (action.failureType) {
							case Ext.form.action.Action.CLIENT_INVALID:
								Ext.Msg.alert('Failure', 'Form fields may must be submitted with invalid values');
								break;
							case Ext.form.action.Action.CONNECT_FAILURE:
								Ext.Msg.alert('Failure', 'doesn\'t work');
								break;
							case Ext.form.action.Action.SERVER_INVALID:
								Ext.Msg.alert('Failure', action.result.msg);
								break;
						}
					}
				});
			}
		});
    
    
    },
    
    onEditTempStudy : function(button, e, options) {

    	var srec = this.getIconview().getSelectionModel().getLastSelected();
    	var myid = srec.data.indice;
    	
		Ext.Ajax.request({
			url: '/roda/cmsfilecontent/'+myid,
			waitTitle: 'Connecting',
			waitMsg: 'Sending data...',                                     
			method : 'GET',
			scope:this,
			success : function(response, opts) {
				console.log ('success');
				var resp = Ext.JSON.decode(response.responseText);
				console.log (resp);
						//aici incarcam jsonul

		    			var win = Ext.WindowMgr.get('studyadd');
		    			if (!win) {
		    				win = Ext.create('RODAdmin.view.studies.CBEditor.AddStudyWindow');
		    			}	    
		    			win.setTitle('Edit temporary study');
		    			win.setMode('edit');
                    	win.setEditindex(myid);
		    			
		    			//load record, let's see

                    	//study Proposal
                    	if (resp.studyProposal.startdate) {
                    		win.down('form#sproposalform datefield#startdate').setValue(new Date(resp.studyProposal.startdate));
                    	}
                    	if (resp.studyProposal.enddate) {
                    		win.down('form#sproposalform datefield#enddate').setValue(new Date(resp.studyProposal.enddate));
                    	}

                       	Ext.StoreManager.get('common.Language').load();
                    	
                    	
                    	win.down('form#sproposalform combo#genlanguage').setValue(resp.studyProposal.genlanguage);   
                    	win.down('form#sproposalform textareafield#studytitle').setValue(resp.studyProposal.studytitle);
		    			win.down('form#sproposalform textareafield#stabstract').setValue(resp.studyProposal.stabstract);
		    			win.down('form#sproposalform textareafield#altitle').setValue(resp.studyProposal.altitle);
		    			win.down('form#sproposalform textareafield#geocoverage').setValue(resp.studyProposal.geocoverage);
		    			win.down('form#sproposalform textareafield#geounit').setValue(resp.studyProposal.geounit);
		    			win.down('form#sproposalform textareafield#resinstrument').setValue(resp.studyProposal.resinstrument);
		    			win.down('form#sproposalform textareafield#universe').setValue(resp.studyProposal.universe);
		    			win.down('form#sproposalform textareafield#weighting').setValue(resp.studyProposal.weighting);
		    			win.down('form#sproposalform textareafield#analysisunit').setValue(resp.studyProposal.analysisunit);
		    			
		    			
		    			
		    			var porgstore = win.down('sproposal grid#prinvdisplay').store;
		    			console.log('porgstore');
		    			console.log(resp.studyProposal.principalinvestigator);
		    			
		    			porgstore.removeAll();
		    			this.loadPOrg(porgstore,resp.studyProposal.principalinvestigator);
		    			//study funding
		    			if (resp.studyFunding.sfunding) {
		    			win.down('form#sfundingform datefield#startfunding').setValue(new Date(resp.studyFunding.sfunding));
		    			}
		    			if (resp.studyFunding.endfunding) {
		    			win.down('form#sfundingform datefield#endfunding').setValue(new Date(resp.studyFunding.endfunding));
		    			}
		    			win.down('form#sfundingform textareafield#grantnr').setValue(resp.studyFunding.grantnr);		    			
		    			var fagstore = win.down('sfunding grid#fundvdisplay').store;
		    			fagstore.removeAll();
		    			this.loadPOrg(fagstore,resp.studyFunding.fundingAgency);
		    			//concepts

		    			if (resp.studyConcepts.stconcepts) {
                    		win.down('form#conceptsform datefield#stconcepts').setValue(new Date(resp.studyConcepts.stconcepts));
                    	}
                    	if (resp.studyConcepts.endconcepts) {
                    		win.down('form#conceptsform datefield#endconcepts').setValue(new Date(resp.studyConcepts.endconcepts));
                    	}
		    			var concstore = win.down('sconcepts grid#cnrespdisplay').store;
		    			concstore.removeAll();
		    			this.loadPOrg(concstore,resp.studyConcepts.cdesignresp);

//		    			var conceptstore = win.down('sconcepts treepanel#conceptsdisplay').store;
//		    			conceptstore.removeAll();
		    	//		var rootNode = conceptstore.setRootNode(resp.studyConcepts.concepts);
		    			
		    			
		    			
		    			//questions
		    			if (resp.studyQuestions.startqdesign) {
                    		win.down('form#questionsform datefield#startqdesign').setValue(new Date(resp.studyQuestions.startqdesign));
                    	}
                    	if (resp.studyQuestions.endqdesign) {
                    		win.down('form#questionsform datefield#endqdesign').setValue(new Date(resp.studyQuestions.endqdesign));
                    	}
                    	var qdstore = win.down('squestions grid#qdesigndisplay').store;
                    	qdstore.removeAll();
		    			this.loadPOrg(qdstore,resp.studyQuestions.qdesignresp);
                    	
                    	
                    	//qstore
                    	
                    	var questionstore = win.down('squestions grid#questionsdisplay').store;
                    	questionstore.removeAll();
		    			this.loadQuestions(questionstore,resp.studyQuestions.questions);

                    	
                    	if (resp.studyQuestions.startqtranslation) {
                    		win.down('form#questionsform datefield#qtranslationstart').setValue(new Date(resp.studyQuestions.startqtranslation));
                    	}
                    	if (resp.studyQuestions.endqtranslation) {
                    		win.down('form#questionsform datefield#qtranslationend').setValue(new Date(resp.studyQuestions.endqtranslation));
                    	}
		    			//Data Collection
		    			if (resp.dataCollection.startsampling) {
                    		win.down('form#datacollectionform datefield#startsampling').setValue(new Date(resp.dataCollection.startsampling));
                    	}
                    	if (resp.dataCollection.endsampling) {
                    		win.down('form#datacollectionform datefield#endsampling').setValue(new Date(resp.dataCollection.endsampling));
                    	}

                    	
                    	
                    	
		    			if (resp.dataCollection.startdatacollection) {
                    		win.down('form#datacollectionform datefield#startdatacollection').setValue(new Date(resp.dataCollection.startdatacollection));
                    	}
                    	if (resp.dataCollection.enddatacollection) {
                    		win.down('form#datacollectionform datefield#enddatacollection').setValue(new Date(resp.dataCollection.enddatacollection));
                    	}
                   	
                    	
                    	
                    	
                    	
                    	//Data Production
		    			
		    			
		    			if (resp.dataProduction.stdataprod) {
                    		win.down('form#dataprodform datefield#stdataprod').setValue(new Date(resp.dataProduction.stdataprod));
                    	}
                    	if (resp.dataProduction.enddataprod) {
                    		win.down('form#dataprodform datefield#enddataprod').setValue(new Date(resp.dataProduction.enddataprod));
                    	}
                   	
		    			var dprstore = win.down('sdataprod grid#dprespdisplay').store;
		    			dprstore.removeAll();
		    			this.loadPOrg(dprstore,resp.dataProduction.dataprodresp);
                    	
                    	var variables = win.down('sdataprod grid#variabledisplay').store;
                    	variables.removeAll();
		    			this.loadVariables(variables,resp.dataProduction.variables);
		    			
		    			//Process Info
		    			win.down('form#processinfoform checkbox#ccorrect').setValue(resp.dataProcessInfo.ccorrect);
		    			win.down('form#processinfoform checkbox#qextract').setValue(resp.dataProcessInfo.qextract);
		    			win.down('form#processinfoform checkbox#dataextract').setValue(resp.dataProcessInfo.dataextract);
		    			win.down('form#processinfoform combo#datarights').setValue(resp.dataProcessInfo.datarights);
		    			
		    			win.show();
			},
			failure : function(response, opts) {
				switch (action.failureType) {
				case Ext.form.action.Action.CLIENT_INVALID:
                    	Ext.Msg.alert('Failure', 'Form fields may must be submitted with invalid values');
                    	break;
                    	
				case Ext.form.action.Action.CONNECT_FAILURE:
                    	Ext.Msg.alert('Failure', 'doesn\'t work');
                    	break;
				case Ext.form.action.Action.SERVER.INVALID:
                    	Ext.Msg.alert('Failure', action.result.msg);
                    	break;
				}
			}
		});
    	
    	
    	

    	
    	
    },
    
    
    
    onSelectionChange : function(component, selected, event) {
	    console.log('folderviewselectionchange');
	    var record = selected[0];
	    
	    if (record != null) {
	    	console.log(record);
	    	//	    	console.log(record.filepropertiesset());
	    	//need to do shit with details
	    	this.getTempsdetails().layout.setActiveItem('studytempdetails');
	    	//acu trebuie sa incarcam studiul
	    	var myid = record.data.indice;
	    	
	    	
			Ext.Ajax.request({
				url: '/roda/cmsfilecontent/'+myid,
				waitTitle: 'Connecting',
				waitMsg: 'Sending data...',                                     
				method : 'GET',
				scope:this,
				success : function(response, opts) {
					console.log ('success');
//					console.log (response.responseText);					
					var resp = Ext.JSON.decode(response.responseText);
					console.log (resp);
							//aici incarcam jsonul
					
							this.loadStudyPreview(resp);
							this.loadStudyFunding(resp);
							this.loadStudyConcepts(resp);
							this.loadStudyQuestions(resp);
							this.loadDataCollection(resp);
							this.loadDataProduction(resp);
							this.loadProcessInfo(resp);
							
							
							//	RODAdmin.util.Alert.msg('Success!', response.message);
        					//console.log (resp.id);					
				},
				failure : function(response, opts) {
					console.log ('failure');
					switch (action.failureType) {
					case Ext.form.action.Action.CLIENT_INVALID:
                        	Ext.Msg.alert('Failure', 'Form fields may must be submitted with invalid values');
                        	break;
                        	
					case Ext.form.action.Action.CONNECT_FAILURE:
                        	Ext.Msg.alert('Failure', 'doesn\'t work');
                        	break;
					case Ext.form.action.Action.SERVER.INVALID:
                        	Ext.Msg.alert('Failure', action.result.msg);
                        	break;
					}
				}
			});

	    	
	    	
	    	
	    	//load data
	    }
    },
    
    
    loadStudyPreview : function (resp) {
    	//
    	
    	console.log(resp.studyProposal.principalinvestigator);
    	var spropporgs = this.printPOrgs(resp.studyProposal.principalinvestigator);
    	resp.studyProposal.stringporg = spropporgs;
    	console.log(spropporgs);
		var t = new Ext.Template(
		            		     '<div class="stpreviewcontainer">',
		            		     '<table width="100%">',
		            		     '<tr><th>Title: </th><td>{studytitle}</td></tr>',
		            		     '<tr><th>Start proposal: </th><td>{startdate}</td></tr>',
		            		     '<tr><th>End proposal: </th><td>{enddate}</td></tr>',
		            		     '<tr><th>Principal investigator: </th><td>{stringporg}</td></tr>',
		            		     '<tr><th>General language: </th><td>{genlanguagename} ({genlanguage})</td></tr>',
		            		     '<tr><th>Alternative title: </th><td>{altitle}</td></tr>',
		            		     '<tr><th>Study abstract: </th><td>{stabstract}</td></tr>',
		            		     '<tr><th>Geographic coverage: </th><td>{geocoverage}</td></tr>',
		            		     '<tr><th>Geographic unit: </th><td>{geounit}</td></tr>',
		            		     '<tr><th>Research instrument: </th><td>{resinstrument}</td></tr>',
		            		     '<tr><th>Analysis unit: </th><td>{analysisunit}</td></tr>',
		            		     '<tr><th>Universe: </th><td>{universe}</td></tr>',
		            		     '<tr><th>Weighting: </th><td>{weighting}</td></tr>',
		            		     '</table>',
		            		     '</div>',
		            		     {
		            			compiled: true
		            		});

   		var tfinal =  t.apply(resp.studyProposal);

    	this.getTproposalpreview().update(tfinal);
    	
    },
    
    loadStudyFunding : function (resp) {
    	//
    	var spropporgs = this.printPOrgs(resp.studyFunding.fundingAgency);
    	resp.studyFunding.stringporg = spropporgs;
		var t = new Ext.Template(
		            		     '<div class="stpreviewcontainer">',
		            		     '<table width="100%">',
		            		     '<tr><th>Start funding: </th><td>{sfunding}</td></tr>',
		            		     '<tr><th>End funding: </th><td>{endfunding}</td></tr>',
		            		     '<tr><th>Funding agency: </th><td>{stringporg}</td></tr>',
		            		     '<tr><th>Grant number: </th><td>{grantnr}</td></tr>',
		            		     '</table>',
		            		     '</div>',
		            		     {
		            			compiled: true
		            		});
   		var tfinal =  t.apply(resp.studyFunding);
    	this.getTfundingpreview().update(tfinal);
    	
    },
 
    loadStudyConcepts : function (resp) {
    	//
    	var spropporgs = this.printPOrgs(resp.studyConcepts.cdesignresp);
    	resp.studyConcepts.stringporg = spropporgs;
		var t = new Ext.Template(
		            		     '<div class="stpreviewcontainer">',
		            		     '<table width="100%">',
		            		     '<tr><th>Start concepts design: </th><td>{stconcepts}</td></tr>',
		            		     '<tr><th>End concepts design: </th><td>{endconcepts}</td></tr>',
		            		     '<tr><th>Concept design responsibility: </th><td>{stringporg}</td></tr>',
		            		     '</table>',
		            		     '</div>',
		            		     {
		            			compiled: true
		            		});
   		var tfinal =  t.apply(resp.studyConcepts);
    	this.getTconceptspreview().update(tfinal);
    	
    },
 
    loadStudyQuestions : function (resp) {
    	//
    	
    	console.log('------------------------------------');
    	console.log(resp.studyQuestions);
    	console.log(resp.studyQuestions.qdesignresp);
    	var spropporgs = this.printPOrgs(resp.studyQuestions.qdesignresp);
    	resp.studyQuestions.stringporg = spropporgs;

    	var spropporgs2 = this.printPOrgs(resp.studyQuestions.transresponsability);
    	resp.studyQuestions.stringporg2 = spropporgs2;

    	resp.studyQuestions.questionsstr = this.printQuestions(resp.studyQuestions);
    	resp.studyQuestions.qtranslationsstr = this.printTranslations(resp.studyQuestions);
    	
    
    	
		var t = new Ext.Template(
		            		     '<div class="stpreviewcontainer">',
		            		     '<table width="100%">',
		            		     '<tr><th>Start questionaire design: </th><td>{startqdesign}</td></tr>',
		            		     '<tr><th>End questionaire design: </th><td>{endqdesign}</td></tr>',
		            		     '<tr><th>Questionaire design responsibility: </th><td>{stringporg}</td></tr>',
		            		     '<tr><th>Questions: </th><td>{questionsstr}</td></tr>',		            		     
		            		     '<tr><th>Start questionaire translation: </th><td>{startqtranslation}</td></tr>',
		            		     '<tr><th>End questionaire translation: </th><td>{endqtranslation}</td></tr>',
		            		     '<tr><th>Questionaire translation responsibility: </th><td>{stringporg2}</td></tr>',
		            		     '<tr><th>Translated questions: </th><td>{qtranslationsstr}</td></tr>',
		            		     '</table>',
		            		     '</div>',
		            		     {
		            			compiled: true
		            		});
   		var tfinal =  t.apply(resp.studyQuestions);
    	this.getTquestionspreview().update(tfinal);
    	
    },
 
    
    loadDataCollection : function (resp) {
    	//
    	var spropporgs = this.printPOrgs(resp.dataCollection.samplingresp);
    	resp.dataCollection.stringporg = spropporgs;
    	resp.dataCollection.dcporg = this.printPOrgs(resp.dataCollection.datacolresp);
    	
    	
		var t = new Ext.Template(
		            		     '<div class="stpreviewcontainer">',
		            		     '<table width="100%">',
		            		     '<tr><th>Start sampling: </th><td>{startsampling}</td></tr>',
		            		     '<tr><th>End sampling: </th><td>{endsampling}</td></tr>',
		            		     '<tr><th>Sampling responsibility: </th><td>{stringporg}</td></tr>',
		            		     '<tr><th>Sample description: </th><td>{sampledescr}</td></tr>',
		            		     '<tr><th>Start data collection: </th><td>{startsampling}</td></tr>',
		            		     '<tr><th>End data collection: </th><td>{endsampling}</td></tr>',
		            		     '<tr><th>Data collection responsibility: </th><td>{dcporg}</td></tr>',
		            		     '<tr><th>Data collection mode: </th><td>{dcolmode}</td></tr>',		 
		            		     '<tr><th>Data collection situation: </th><td>{dcolsituation}</td></tr>',				            		     
		            		     '</table>',
		            		     '</div>',
		            		     {
		            			compiled: true
		            		});
   		var tfinal =  t.apply(resp.dataCollection);
    	this.getTdatacollectionpreview().update(tfinal);
    	
    },
    
    loadDataProduction : function (resp) {
    	var spropporgs = this.printPOrgs(resp.dataProduction.dataprodresp);
    	resp.dataProduction.stringporg = spropporgs;
    	resp.dataProduction.varstring = this.printVariables(resp.dataProduction.variables);
    	var t = new Ext.Template(
		            		     '<div class="stpreviewcontainer">',
		            		     '<table width="100%">',
		            		     '<tr><th>Start data production: </th><td>{stdataprod}</td></tr>',
		            		     '<tr><th>End data production: </th><td>{enddataprod}</td></tr>',
		            		     '<tr><th>Data production responsibility: </th><td>{stringporg}</td></tr>',
		            		     '<tr><th>Variables: </th><td>{varstring}</td></tr>',
		            		     '<tr><th>Files: </th><td>{files}</td></tr>',
		            		     '</table>',
		            		     '</div>',
		            		     {
		            			compiled: true
		            		});
   		var tfinal =  t.apply(resp.dataProduction);
    	this.getTdataproductionpreview().update(tfinal);
    	
    },
    
    loadProcessInfo : function (resp) {
    		var t = new Ext.Template(
    		            		     '<div class="stpreviewcontainer">',
    		            		     '<table width="100%">',
    		            		     '<tr><th>Process information: </th><td>',
    		            		     		'<table width="100%" border="0">',
    		            		     		'<tr><th>RODA can correct minor errors</th><td>{ccorrect}</td></tr>',	
    		            		     		'<tr><th>RODA can extract questions and variables</th><td>{qextract}</td></tr>',	
    		            		     		'<tr><th>RODA can extract data</th><td>{dataextract}</td></tr>',	
    		            		     		'</table>',
    		            		     '</td></tr>',
    		            		     '<tr><th>Data rights: </th><td>{datarights}</td></tr>',
    		            		     '</table>',
    		            		     '</div>',
    		            		     {
    		            			compiled: true
    		            		});
       		var tfinal =  t.apply(resp.dataProcessInfo);
        	this.getTprocessinfo().update(tfinal);
    },
    
    
    loadPOrg : function(store, data) {
//    	console.log(porg);
    	for (var index in data) {
    	    var npr = data[index];

			var response = new RODAdmin.model.studies.CBEditor.PersOrg (
			                                                            {
			                                        								id : npr.id,
			                                        								status: npr.status,
			                                        								type: npr.type ,
			                                        								selectedname: npr.selectedname , 
			                                        									persprefix : npr.persprefix ,
			                                        									persfname : npr.persfname ,
			                                        									perslname : npr.perslname ,
			                                        									perssuffix : npr.perssuffix ,
			                                        									persemail : npr.persemail ,
			                                        									persphone : npr.persphone ,
			                                        									persaddr1 : npr.persaddr1 ,
			                                        									persaddr2 : npr.persaddr2 ,
			                                        									perszip : npr.perszip ,
			                                        									perscity : npr.perscity ,
			                                        									orgprefix : npr.orgprefix ,
			                                        									orgsname : npr.orgsname ,
			                                        									orglname : npr.orglnanme ,
			                                        									orgsuffix : npr.orgsuffix ,
			                                        									orgemail : npr.orgemail ,
			                                        									orgphone : npr.orgphone ,
			                                        									orgaddr1 : npr.argaddr ,
			                                        									orgaddr2 : npr.orgaddr2 ,
			                                        									orgzip : npr.orgzip ,
			                                        									orgcity : npr.orgcity ,
			                                        							});
				store.add(response);
				store.commitChanges();    	    
    	}
    },
    
  
    loadQuestions : function(store, data) {
//asta trebuie facut mult mai complicat, sa vedem 
    	for (var index in data) {
    	    var npr = data[index];
    	    console.log(npr);
    	    var response = new RODAdmin.model.studies.CBEditor.StudyQuestion (
			                                                            {
                                    									id : npr.id ,
                                    									text: npr.text,
                                    									lang: npr.lang,
                                    									respdomain: npr.respdomain ,
                                    									concept_id: npr.concept_id,
                                    									concept_name: npr.concept_name ,	
			                                                            });
			
//missing
    	    
    		for (var miss in npr.missing) {
    			var missing = new RODAdmin.model.studies.CBEditor.question.Missing(
    			                                       							{
    			                                       								value : npr.missing[miss].value,
    			                                       								label : npr.missing[miss].label
    			                                       							});
    			response.missing().add(missing);
    			response.missing().commitChanges();
    		}
    	    
    	    if (npr.respdomain == 'Category') {
    	    	console.log('-----------------------------');
    	    	console.log(npr.catsponses);
    	    	for (var ncat in npr.catsponses) {
				var catr = new RODAdmin.model.studies.CBEditor.question.response.Category(
				                                                  							{
				                                                  								id : npr.catsponses[ncat].id,
				                                                  								label : npr.catsponses[ncat].label,
				                                                  								lang : npr.catsponses[ncat].lang,
				                                                  								value : npr.catsponses[ncat].value
				                                                  							});
				 
				response.catresponses().add(catr );
				response.catresponses().commitChanges();
    	    	}
    	    	
    	    	
    	    } else if (npr.respdomain == 'Numeric') {
    	    		for (var nind in npr.numericresponse) {
    	    			var num = npr.numericresponse[nind];
    	    			var numericresponse = new RODAdmin.model.studies.CBEditor.question.response.Numeric(
    	    	                                                            						{
    	    	                                                            							type : num.numresponsetype,
    	    	                                                            							low : num.numresponselow,
    	    	                                                            							high : num.numresponsehigh
    	    	                                                            						});
    	    	
    	    			response.numericresponse().add(numericresponse );
    					response.numericresponse().commitChanges();
    	    		}
    	    }
     	    
    	    store.add(response);
			store.commitChanges();    	    
    	}
    },
		                                                            
   
    
    
    
    
    loadVariables : function(store, data) {
    	//asta trebuie facut mult mai complicat, sa vedem 
    	    	for (var index in data) {
    	    	    var npr = data[index];
    	    	    console.log(npr);
    	    	    var response = new RODAdmin.model.studies.CBEditor.StudyVariable (
    				                                                            {
    	                                    									id : npr.id ,
    	                                    									name: npr.name,
    	                                    									label: npr.label,
    	                                    									respdomain: npr.respdomain ,
    	                                    									oqid: npr.oqid,
    	                                    									oqtext: npr.oqtext ,	
    				                                                            });
    				
//    	//missing
//    	    	    
//    	    		for (var miss in npr.missing) {
//    	    			var missing = new RODAdmin.model.studies.CBEditor.question.Missing(
//    	    			                                       							{
//    	    			                                       								value : npr.missing[miss].value,
//    	    			                                       								label : npr.missing[miss].label
//    	    			                                       							});
//    	    			response.missing().add(missing);
//    	    			response.missing().commitChanges();
//    	    		}
//    	    	    
//    	    	    if (npr.respdomain == 'Category') {
//    	    	    	console.log('-----------------------------');
//    	    	    	console.log(npr.catsponses);
//    	    	    	for (var ncat in npr.catsponses) {
//    					var catr = new RODAdmin.model.studies.CBEditor.question.response.Category(
//    					                                                  							{
//    					                                                  								id : npr.catsponses[ncat].id,
//    					                                                  								label : npr.catsponses[ncat].label,
//    					                                                  								lang : npr.catsponses[ncat].lang,
//    					                                                  								value : npr.catsponses[ncat].value
//    					                                                  							});
//    					 
//    					response.catresponses().add(catr );
//    					response.catresponses().commitChanges();
//    	    	    	}
//    	    	    	
//    	    	    	
//    	    	    } else if (npr.respdomain == 'Numeric') {
//    	    	    		for (var nind in npr.numericresponse) {
//    	    	    			var num = npr.numericresponse[nind];
//    	    	    			var numericresponse = new RODAdmin.model.studies.CBEditor.question.response.Numeric(
//    	    	    	                                                            						{
//    	    	    	                                                            							type : num.numresponsetype,
//    	    	    	                                                            							low : num.numresponselow,
//    	    	    	                                                            							high : num.numresponsehigh
//    	    	    	                                                            						});
//    	    	    	
//    	    	    			response.numericresponse().add(numericresponse );
//    	    					response.numericresponse().commitChanges();
//    	    	    		}
//    	    	    }
//    	     	    
    	    	    store.add(response);
    				store.commitChanges();    	    
    	    	}
    	    },

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    printPOrgs : function(porg) {
    	console.log(porg);
    	var templ =  new Ext.Template('<b>Status: </b> {status} <b>Type: </b> {type} <b>Name: </b> {selectedname}',  {compiled: true});
    	var response = '';
    	for (var index in porg) {
    	    var npr = porg[index];
    	    var element = templ.apply(npr);
    	    console.log(element);
    	    response += templ.apply(npr) + '<br>';
    	}
    	return response;
    },

    printQuestions : function(data) {
//    	console.log(porg);
    	var templ =  new Ext.Template('<b>Question text: </b> {text} <b>Response domain: </b> {respdomain}',  {compiled: true});
    	var response = '';
    	for (var index in data.questions) {
    	    var npr = data.questions[index];
    	    var element = templ.apply(npr);
    	    console.log(element);
    	    response += templ.apply(npr) + '<br>';
    	}
    	return response;
    },

    printVariables : function(data) {
    	var templ =  new Ext.Template('<b>Question text: </b> {oqtext} <b>Variable name: </b> {name} <b>Variable label: </b> {label}',  {compiled: true});
    	var response = '';
    	for (var index in data) {
    	    var npr = data[index];
    	    var element = templ.apply(npr);
    	    console.log(element);
    	    response += templ.apply(npr) + '<br>';
    	}
    	return response;
    },
    
    
    printTranslations : function(data) {
//    	console.log(porg);
    	var templ =  new Ext.Template('<b>Question text: </b> {text} <b>Response domain: </b> {respdomain}',  {compiled: true});
    	var response = '';
    	for (var index in data.translations) {
    	    var npr = data.translations[index];
    	    var element = templ.apply(npr);
    	    console.log(element);
    	    response += templ.apply(npr) + '<br>';
    	}
    	return response;
    },

    
    
    onAddStudyClick : function(button, e, options) {
	    win = Ext.WindowMgr.get('studyadd');
	    this.emptyAllStores();
	    if (!win) {
    	 win = Ext.create('RODAdmin.view.studies.CBEditor.AddStudyWindow');
			var prinvdisplaystore = win.down('sproposal grid#prinvdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('sconcepts grid#cnrespdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('sdatacollection grid#qsamplingrespdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('sdatacollection grid#dcrespdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('sdataprod grid#dprespdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('sdataprod grid#variabledisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('sdataprod grid#filesdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('sfunding grid#fundvdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('squestions grid#qdesigndisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('squestions grid#questionsdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('squestions grid#qtranslationdisplay').getStore().removeAll();
			var prinvdisplaystore = win.down('squestions grid#translationsdisplay').getStore().removeAll();			
	    }
	    win.setTitle('Add a new Study');
	    win.show();
    },

    
    emptyAllStores : function() {
    
    	Ext.StoreManager.get('studies.CBEditor.PrincipalInv').removeAll;
    	Ext.StoreManager.get('studies.CBEditor.Funder').removeAll;
    	Ext.StoreManager.get('studies.CBEditor.Person').removeAll;
    	Ext.StoreManager.get('studies.CBEditor.Org').removeAll;
    	Ext.StoreManager.get('studies.CBEditor.Qdesign').removeAll;
    	Ext.StoreManager.get('studies.CBEditor.Questions').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Qtranslation').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Qsamplingresp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.DataCollectionResp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.CollectionMode').removeAll;
        Ext.StoreManager.get('studies.CBEditor.DPResp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Qcoderesp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Qcatresp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Lang').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Variables').removeAll;
        Ext.StoreManager.get('studies.CBEditor.TransQuestions').removeAll;
        Ext.StoreManager.get('studies.CBEditor.TransQcatresp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.TransQcoderesp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.ConceptResp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.ConceptsDisp').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Qmissing').removeAll;
        Ext.StoreManager.get('studies.CBEditor.Files').removeAll;
        Ext.StoreManager.get('studies.CBEditor.FileTypes').removeAll;
        Ext.StoreManager.get('studies.CBEditor.SRights').removeAll; 
    }
    
    
    

});    