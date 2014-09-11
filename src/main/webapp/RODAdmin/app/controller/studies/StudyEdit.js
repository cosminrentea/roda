/**
 * 
 */
Ext.define('RODAdmin.controller.studies.StudyEdit', {
    extend : 'Ext.app.Controller',
    /**
	 * @cfg
	 */
    views : [
	    "RODAdmin.view.studies.EditStudyWindow",
	    "RODAdmin.view.studies.AddStudyToGroupWindow",
	    "RODAdmin.view.studies.GroupWindow",
	    'RODAdmin.view.studies.StudyEdit.SEConcepts',
	    'RODAdmin.view.studies.StudyEdit.SEDataCollection',
	    'RODAdmin.view.studies.StudyEdit.SEDataProd',
	    'RODAdmin.view.studies.StudyEdit.SEFunding',
	    'RODAdmin.view.studies.StudyEdit.SEProposal',
	    'RODAdmin.view.studies.StudyEdit.SEQuestions'
    ],
    /**
	 * @cfg
	 */
    refs : [
            {
                ref : 'itemsview',
                selector : 'studyitemsview'
            }, {
                ref : "folderview",
                selector : "studyitemsview treepanel#stfolderview"
            }, {
                ref : 'folderselect',
                selector : 'studyedit treepanel#groupselect'
            }, {
                ref : 'gfolderselect',
                selector : 'catalogedit treepanel#groupselect'
            }, {
                ref : 'groupedit',
                selector : 'catalogedit'
            }, {
            	ref: 'questionsgrid',
            	selector : 'sesquestions grid#questionsdisplay'	
            },{
            	ref: 'questionsgrid',
            	selector : 'sesquestions grid#questionsdisplay'	
            },{
				ref : 'variablegrid',
				selector : 'sesdataprod grid#variabledisplay'
			},{
				ref: 'sesfunding',
				selector: 'sesfunding',
			}, {
				ref: 'sesconcepts',
				selector: 'sesconcepts'
			}, {
				ref: 'sesquestions',
				selector : 'sesquestions'
			},{
				ref: 'sesdatacollection',
				selector: 'sesdatacollection'
			}, {
				ref: 'sesdataprod',
				selector : 'sesdataprod'
			}, {
				ref : 'rdomaincard',
				selector : 'seaddquestion form#questionform fieldset#qrinformation'
				// selector: 'addquestion form#questionform'
			} 
			
    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
	        "studyedit treepanel#groupselect" : {
	        	 /**
				 * @listener groupselect-load triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
				 *           executes {@link #folderLoad}  
				 */
	            load : this.folderLoad, // this is the only event fired
	            // after loading the store in a
	            // tree view, apparently. This
	            // is REALLY stupid because it
	            // is probabily fired multiple
	            // times.
	        	 /**
				 * @listener groupselect-cellclick triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
				 *           executes {@link #onGroupselectCellClick}  
				 */
	            cellclick : this.onGroupselectCellClick
	        },
	        "studyedit button#save" : {
	        	 /**
				 * @listener button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} button#save    
				 *           executes {@link #onStudyEditSaveClick}  
				 */	        	
		        click : this.onStudyEditSaveClick
	        },
	        
	        
	        "catalogedit treepanel#groupselect" : {
	        	 /**
				 * @listener groupselect-load triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
				 *           executes {@link #folderLoad}  
				 */
	            load : this.GfolderLoad, // this is the only event fired
	            // after loading the store in a
	            // tree view, apparently. This
	            // is REALLY stupid because it
	            // is probabily fired multiple
	            // times.
	        	 /**
				 * @listener groupselect-cellclick triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
				 *           executes {@link #onGroupselectCellClick}  
				 */
	            cellclick : this.onGGroupselectCellClick
	        },
	        "catalogedit button#save" : {
	        	 /**
				 * @listener button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} button#save    
				 *           executes {@link #onStudyEditSaveClick}  
				 */	        	
		        click : this.onCatalogEditSaveClick
	        },
	        "studygadd button#save" : {
	        	 /**
				 * @listener studygadd-button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.AddStudyToGroupWindow AddStudyToGroupWindow} button#save    
				 *           executes {@link #onStudyAddGroupSaveClick}  
				 */	
	        	click : this.onStudyAddGroupSaveClick
	        },
	        "stgroupadd button#save" : {
	        	 /**
				 * @listener stgroupadd-button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.GroupWindow GroupWindow} button#save    
				 *           executes {@link #onGroupSaveClick}  
				 */	
	        	click : this.onGroupSaveClick

	        },
	        "studyedit" : {
	        	startStydyLoad : this.loadStudy
	        },
			'sesproposal button#addpinv' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addPrincipalInvestigator
			},
			'sesfunding button#addfund' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addFundingAgency
			},
			'sesconcepts button#conceptresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addConceptresp
			},
			'sesquestions button#addqdesign' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addQuestionaireDesign
			},
			'sesquestions button#addqtranslationresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addQtranslation
			},
			'sesdatacollection button#addsamplingresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addSamplingresp
			},
			'sesdatacollection button#adddcresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addDCollectionresp
			},
			'sesdataprod button#dpresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addDCollectionresp
			},
			'sesquestions button#addquestion' : {
				click : this.addEmptyQuestion
			},
			'sesquestions grid#questionsdisplay' : {
				editRecord : this.editQuestion
			},
			'seaddquestion form#questionform fieldset#questioninfo combo#qrdomain' : {
				change : this.changeResponseDomain
			},
			
			
	    });
    },
 
	changeResponseDomain : function(el, newval, oldval, eOpts) {
		console.log('new value: ' + newval);
		console.log('old value: ' + oldval);

		console.log(this.getRdomaincard());

		if (newval == 'Category') {
			this.getRdomaincard().getLayout().setActiveItem('catresp');
		} else if (newval == 'String') {
			this.getRdomaincard().getLayout().setActiveItem('stringresp');
		} else if (newval == 'Numeric') {
			this.getRdomaincard().getLayout().setActiveItem('numericresp');
		}
	},
    
    
    
    editQuestion : function(grid, record, rowIndex, row) {
		console.log('edit shit');
		console.log(grid);
		console.log(record);
		var myrecord = grid.getStore().getAt(rowIndex);
		// intai incercam manual, apoi vedem daca putem lega cu storeul
		var win = Ext.WindowMgr.get('seaddquestion');
		if (!win) {
			win = Ext.create('RODAdmin.view.studies.StudyEdit.AddQuestion');
		}
		win.setTitle('Edit Question' + myrecord.data.text);

		win.setMode('edit');
		win.setEditId(rowIndex);
		// sa vedem ce se intampla cu storurile. In principiu trebuie sa il
		// umplem pe cel pe care trebuie sapl umplem si sa le golim pe
		// celelalte.
		// referinte utile
		var cards = win.down('form#questionform').down('fieldset#qrinformation');
		var codegrid = cards.down('grid#qcoderesp');
//		var catgrid = cards.down('grid#qcatresp');
		var missinggrid = win.down('grid#missing');
		var numerictype = cards.down('combo#qnrtype');
		
		var nlow = cards.down('textfield[name=qnrlow]');
		var nhigh = cards.down('textfield[name=qnrhigh]');
		// golim toate storeurile
		var codestore = codegrid.getStore();
		codestore.getProxy().clear();
		codestore.data.clear();
		codestore.sync();
//		var catstore = catgrid.getStore();
//		catstore.getProxy().clear();
//		catstore.data.clear();
//		catstore.sync();
		var missingstore = missinggrid.getStore();
		missingstore.getProxy().clear();
		missingstore.data.clear();
		missingstore.sync();
		
		
		win.down('form#questionform').getForm().loadRecord(myrecord);
		//of course the fucking tree doesn't work
		//try manually
//		var combo = win.down('form#questionform').down('fieldset#qrinformation').down('treecombo[name=concept]');
//		combo.setValue(myrecord.concept_id);
		
//missing
//		var newMStore = myrecord.missing();
//		missingstore.loadRecords(newMStore.getRange(0, newMStore.getCount()), {
//						addRecords : false
//		});
		
		
		if (myrecord.data.respdomain == 'Code') {
			console.log('code response domain');
			var newStore = myrecord.coderesponses();
			console.log(newStore);
			codestore.loadRecords(newStore.getRange(0, newStore.getCount()), {
						addRecords : false
					});
		} else if (myrecord.data.respdomain == 'Category') {
			console.log('category response domain');
			console.log('code response domain');
			var newStore = myrecord.catresponses();
			catstore.loadRecords(newStore.getRange(0, newStore.getCount()), {
						addRecords : false
					});
		} else if (myrecord.data.respdomain == 'Numeric') {
			console.log('numeric response domain');
			cards.getLayout().setActiveItem('numericresp');
			var numericshit = myrecord.numericresponse().getAt(0); // this is
																	// actually
																	// one to
																	// one
			console.log(numericshit);
			numerictype.setValue(numericshit.data.type);
			nlow.setValue(numericshit.data.low);
			nhigh.setValue(numericshit.data.high);
		}
		win.show();

    	
    },
    
	addEmptyQuestion : function(button, e, options) {
			var win = Ext.WindowMgr.get('addquestion');
			if (!win) {
				win = Ext.create('RODAdmin.view.studies.StudyEdit.AddQuestion');
			}

			// set general language value
//			var genlang = this.getMainlang().getValue();
//			var langcombo = win.down('form#questionform').down('fieldset#questioninfo').down('combo[name=lang]');
//			if (genlang) {
//				langcombo.setValue(genlang);
//			}
			var cards = win.down('form#questionform').down('fieldset#qrinformation');
			var codegrid = cards.down('grid#qcoderesp');
			var catgrid = cards.down('grid#qcatresp');
			var missinggrid = win.down('form#questionform').down('fieldset#missinginfo').down('grid#missing');
			var numerictype = cards.down('combo#qnrtype');
			var nlow = cards.down('textfield[name=qnrlow]');
			var nhigh = cards.down('textfield[name=qnrhigh]');
			// golim toate storeurile
			var codestore = codegrid.getStore();
			codestore.getProxy().clear();
			codestore.data.clear();
			codestore.sync();
//			var catstore = catgrid.getStore();
//			catstore.getProxy().clear();
//			catstore.data.clear();
//			catstore.sync();
			var missingstore = missinggrid.getStore();
			missingstore.getProxy().clear();
			missingstore.data.clear();
			missingstore.sync();
			win.setTitle('Add Question');
			win.show();
		},
    
	addPrincipalInvestigator : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesproposal().down('fieldset#prinvfs').down('grid#prinvdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Principal Investigator');
		win.show();
	},

	addFundingAgency : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesfunding().down('fieldset#funding').down('grid#fundvdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Funding Agency');
		win.show();
	},
  
	addConceptresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesconcepts().down('fieldset#cnresponsibility').down('grid#cnrespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Concepts Responsibility Entity');
		win.show();
	},
	addQuestionaireDesign : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesquestions().down('fieldset#qdesign').down('grid#qdesigndisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Questionaire design Responsibility Entity');
		win.show();
	},    
	addQtranslation : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesquestions().down('fieldset#qtransation').down('grid#qtranslationdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Questionaire design Responsibility Entity');
		win.show();
	},	
	addSamplingresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesdatacollection().down('fieldset#dcfsamplingresp').down('grid#qsamplingrespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Sampling Responsibility Entity');
		win.show();
	},
	addDCollectionresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesdatacollection().down('fieldset#dcfdcresp').down('grid#dcrespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Sampling Responsibility Entity');
		win.show();
	},
	addDProdresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.StudyEdit.AddPersonOrg');
		var ourstore = this.getSesdataprod().down('fieldset#dpresponsibility').down('grid#dprespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Data Production Responsibility Entity');
		win.show();
	},

	
	
	
    loadStudy : function (editor, item) {
    	console.log('firedevent works');
    	console.log(editor);
    	console.log(item);
    	//sa incarcam studiul
    	var stitemstore = Ext.StoreManager.get('studies.StudyItem');
    	var me = this;
    	editor.setLoading('Loading...');
    	stitemstore.load({
 	        id : item.data.indice, // set the id here
 	        scope : this,
 	        callback : function(records, operation, success) {
 				   if (success) {
 					  var stitem = stitemstore.first();
 					   me.loadStudyEditPanel(editor, stitem);
 					   editor.setLoading(false);
 				   }
 	        }
 	    });    	
    },
    
    loadStudyEditPanel : function(editor, item) {
    	editor.down('sesproposal textareafield#studytitle').setValue(item.data.name);
    	editor.down('sesproposal textareafield#stabstract').setValue(item.data.description);
    	editor.down('sesproposal textareafield#geocoverage').setValue(item.data.geo_coverage);
    	editor.down('sesproposal textareafield#geounit').setValue(item.data.geo_unit);    	
    	editor.down('sesproposal textareafield#resinstrument').setValue(item.data.research_instrument);
    	this.getQuestionsgrid().bindStore(item.variables());
    	this.getVariablegrid().bindStore(item.variables());
    	
    	
    	//variables now
    	
    	
    	
    	
    },
    
    /**
	 * @method
	 * Se ocupa de scrierea unui grup modificat.  
	 */
    onGroupSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
/**
 * @todo Store 
 * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
 */
	    
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/catalogsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Study group saved.');
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadTreeClick();
				        }
			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        console.log(action.failureType);
			        console.log(action);
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
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
	    }
    },
    /**
	 * @method
	 */

    onStudyEditSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var bt = button;
	    var formPanel = win.down('form');
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var folderview = this.getFolderview()
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/studysave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Study saved.');
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadGridClick();
				        }
			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        console.log(action.failureType);
			        console.log(action);
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
	    }

    },
    /**
	 * @method
	 */

    onStudyAddGroupSaveClick : function(button, e, options) {
	    console.log('small step for man...');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/studysave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        console.log('closing');
				        console.log(win);
			        	RODAdmin.util.Alert.msg('Success!', 'Study saved on the server.');
				        // store.load();
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadTreeClick();
				        }

			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        console.log(action.failureType);
			        console.log(action);
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
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
	    }
    },
    
    /**
	 * @method
	 */
    
    onCatalogEditSaveClick : function(button, e, options) {
	    console.log('group save...');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/catalogsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        console.log('closing');
				        console.log(win);
			        	RODAdmin.util.Alert.msg('Success!', 'Study saved on the server.');
				        // store.load();
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadTreeClick();
				        }

			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        console.log(action.failureType);
			        console.log(action);
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
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
	    }
    },
    /**
	 * @method
	 */
    folderLoad : function(component, options) {
	    var active = this.getItemsview().study.getActiveItem();
	    var pnode = active.getSelectionModel().getLastSelected();
	    var rnode = this.getFolderselect().getRootNode();
	    var cnode = rnode.findChild('indice', pnode.data.groupid, true);
	    
	    if (cnode != null) {
		    this.getFolderselect().getSelectionModel().select(cnode);
	    }
    },
    
    /**
	 * @method
	 */

    GfolderLoad : function(a, b, c, d, e, f) {
    	
    	console.log('gfolderload');
//    	var groupid = this.getGroupedit().down('form').query('hiddenfield[name=group]').value;
//
//    	console.log(this.getGroupedit().down('form').query('hiddenfield[name=group]'));
//	    console.log(groupid);
	    var active = this.getItemsview().study.getActiveItem();
	    console.log(active);
	    var pnode = active.getSelectionModel().getLastSelected();
	    var rnode = this.getGfolderselect().getRootNode();
	    console.log(pnode);
	    var cnode = rnode.findChild('indice', pnode.data.groupid, true);
	    console.log(cnode);	    
	    if (cnode != null) {
		    this.getGfolderselect().getSelectionModel().select(cnode);
	    } else {
	    	this.getGfolderselect().getSelectionModel().select(rnode);
	    }
    },
    
    /**
	 * @method
	 */

    onGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    component.up('studyedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name + '('+record.data.indice+')');
	    component.up('studyedit').down('hiddenfield#groupid').setValue(record.data.indice);
    },

    onGGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	
	    component.up('catalogedit').down('form').query('displayfield')[0].setValue(record.data.name + '('+record.data.indice+')');
	    
	    
	    
	    component.up('catalogedit').down('hiddenfield#groupid').setValue(record.data.indice);
    },
    
    
    
});