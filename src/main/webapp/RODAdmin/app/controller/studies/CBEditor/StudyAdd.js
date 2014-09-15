/**
 * 
 */
Ext.define('RODAdmin.controller.studies.CBEditor.StudyAdd', {
	extend : 'Ext.app.Controller',

	views : [ 'RODAdmin.view.studies.CBEditor.ConceptContextMenu',
            'RODAdmin.view.studies.CBEditor.studyadd.sProposal',
            'RODAdmin.view.studies.CBEditor.studyadd.sDataCollection',
            'RODAdmin.view.studies.CBEditor.studyadd.sFunding',
            'RODAdmin.view.studies.CBEditor.studyadd.sDataProd',
            'RODAdmin.view.studies.CBEditor.studyadd.sQuestions',
			'RODAdmin.view.studies.CBEditor.studyadd.sConcepts',            
	],
	stores : ['studies.CBEditor.PrincipalInv', 
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
			      'studies.CBEditor.FileTypes'
			      ],
	models : ['studies.CBEditor.PersOrg', 'studies.CBEditor.StudyQuestion',
			'studies.CBEditor.question.response.Numeric'],
	/**
	 * @cfg
	 */
	refs : [
	        {
	        	ref : 'studyaddmain',
	        	selector : 'studyadd'
	        },
	        {
				ref : 'studyadd',
				selector : 'studyadd panel#studyaddform'
			}, 
			{
				ref : 'sproposal',
				selector : 'sproposal'
			}, {
				ref : 'sfunding',
				selector : 'sfunding'
			}, {
				ref : 'sconcepts',
				selector : 'sconcepts'
			},	{
				ref : 'squestions',
				selector : 'squestions'
			}, {
				ref : 'sdatacollection',
				selector : 'sdatacollection'
			}, {
				ref : 'sdataprod',
				selector : 'sdataprod'
			}, {
				ref : 'rdomaincard',
				selector : 'addquestion form#questionform fieldset#qrinformation'
				// selector: 'addquestion form#questionform'
			}, 
			 {
				ref : 'transdomaincard',
				selector : 'addquestiontrans form#trquestionform fieldset#qrinformation'
				// selector: 'addquestion form#questionform'
			},
			 {
				ref : 'transmissinggrid',
				selector : 'addquestiontrans form#trquestionform fieldset#qrmissing grid#missing'
				// selector: 'addquestion form#questionform'
			},
			 {
				ref : 'transcodegrid',
				selector : 'addquestiontrans form#trquestionform fieldset#qrinformation grid#qcoderesp'
				// selector: 'addquestion form#questionform'
			},
			 {
				ref : 'transcatgrid',
				selector : 'addquestiontrans form#trquestionform fieldset#qrinformation grid#qcatresp'
				// selector: 'addquestion form#questionform'
			},
			
			
			 {
				ref : 'vardomaincard',
				selector : 'addvariable form#variableform fieldset#vrinformation'
				// selector: 'addquestion form#questionform'
			},
			
			 {
				ref : 'transfsgrid',
				selector : 'squestions form#questionsform fieldset#translatedqfs'
				// selector: 'addquestion form#questionform'
			},
			{
				ref : 'responsecodegrid',
				selector : 'addquestion form#questionform fieldset#qrinformation grid#qcoderesp'
			}, {
				ref : 'responsecatgrid',
				selector : 'addquestion form#questionform fieldset#qrinformation grid#qcatresp'
			}, {
				ref : 'questionsgrid',
				selector : 'squestions form#questionsform fieldset#questions grid#questionsdisplay'
			},
			 {
				ref : 'questionstransgrid',
				selector : 'squestions form#questionsform fieldset#translatedqfs grid#translationsdisplay'
			},
			 {
				ref : 'variablegrid',
				selector : 'sdataprod form#dataprodform fieldset#dpvariables grid#variabledisplay'
			},
			 {
				ref : 'filesgrid',
				selector : 'sdataprod form#dataprodform fieldset#dpdatafiles grid#filesdisplay'
			},
			
			{
				ref : 'missinggrid',
				selector : 'addquestion form#questionform fieldset#missinginfo grid#missing'
			},	
			{
				ref: 'mainlang',
				selector: 'sproposal form#elayoutform fieldset#genprops combo#genlanguage'
			},
			{
				ref: 'tempstudygrid',
				selector: 'studyitemstempview grid#sticonview'
			}
			],

	init : function(application) {
		this.control({
			'studyadd button#sproposalbutton' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.sProposalClick
			},
			'studyadd button#sfundingbutton' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.sFoundingClick
			},
			'studyadd button#sconceptsbutton' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.sConceptsClick
			},
			'studyadd button#squestionsbutton' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.sQuestionsClick
			},
			'studyadd button#sdatacollectionbutton' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.sDataCollectionClick
			},
			'studyadd button#sdataprodbutton' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.sDataProductionClick
			},
			'studyadd button#addpinv' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addPrincipalInvestigator
			},
			'studyadd button#addfund' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addFoundingAgency
			},
			'studyadd button#addqdesign' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addQuestionaireDesign
			},
			'studyadd button#addqtranslationresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addQtranslation
			},
			'studyadd button#addsamplingresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addSamplingresp
			},

			'studyadd button#adddcresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addDCollectionresp
			},
			'studyadd button#dpresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addDProdresp
			},
			'studyadd button#conceptresp' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addConceptresp
			},

			'sdataprod button#addfile' : {
				/**
				 * @listener studies-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view {@link #onIconViewClick}
				 */
				click : this.addFile
			},
			
			
			'addpersonorg button#save' : {
				click : this.savePersonOrg
			},
			'addvariable button#save' : {
				click : this.saveVariable
			},
			'addfile button#save' : {
				click : this.saveFile
			},
			
			
			'squestions fieldset#questions grid#questionsdisplay toolbar button#addquestion' : {
				click : this.addEmptyQuestion
			},
			'squestions fieldset#questions grid#questionsdisplay toolbar button#addquestionwin' : {
				click : this.addQuestion
			},
			'squestions fieldset#translatedqfs grid#translationsdisplay toolbar button#addqtranslation' : {
				click : this.addQuestionTranslation
			},
			'addquestion form#questionform fieldset#questioninfo combo#qrdomain' : {
				change : this.changeResponseDomain
			},
			'addquestion form#questionform fieldset#qrinformation grid#qcoderesp toolbar button#addrespcode' : {
				click : this.addEmptyResponseCode
			},
			'addquestion form#questionform fieldset#missinginfo grid#missing toolbar button#addmissing' : {
				click : this.addEmptyMissing
			},
			'addquestion form#questionform fieldset#qrinformation grid#qcatresp toolbar button#addrespcat' : {
				click : this.addEmptyResponseCat
			},
			'addquestion form#questionform fieldset#qrinformation grid#qcoderesp' : {
				deleteRecord : this.onGridDeleteAction
			},
			'addquestion form#questionform fieldset#qrinformation grid#qcatresp' : {
				deleteRecord : this.onGridDeleteAction
			},
			'addquestion form#questionform fieldset#missinginfo grid#missing' : {
				deleteRecord : this.onGridDeleteAction
			},
			'squestions form#questionsform fieldset#questions grid#questionsdisplay' : {
				deleteRecord : this.onGridQuestionDeleteAction,
				editRecord : this.onQuestionEditAction
			},
			'squestions form#questionsform fieldset#translatedqfs grid#translationsdisplay' : {
				deleteRecord : this.onGridDeleteAction,
				editRecord : this.onTranslationGridEditAction
			},
			'sdataprod form#dataprodform fieldset#dpvariables grid#variabledisplay' : {
				deleteRecord : this.onGridDeleteAction,
				editRecord : this.onVariableGridEditAction
			},
			'sproposal form#elayoutform fieldset#prinvfs grid#prinvdisplay' : {
				deleteRecord : this.onGridDeleteAction
			},
			
			'sfunding form#sfundingform fieldset#funding grid#fundvdisplay' : {
				deleteRecord : this.onGridDeleteAction
			},

			'sconcepts form#conceptsform fieldset#cnresponsibility grid#cnrespdisplay' : {
				deleteRecord : this.onGridDeleteAction
			},

			'squestions form#questionsform fieldset#qdesign grid#qdesigndisplay' : {
				deleteRecord : this.onGridDeleteAction
			},
			
			'squestions form#questionsform fieldset#qtransation grid#qtranslationdisplay' : {
				deleteRecord : this.onGridDeleteAction
			},
			
			'sdatacollection form#datacollectionform fieldset#dcfsamplingresp grid#qsamplingrespdisplay' : {
				deleteRecord : this.onGridDeleteAction
			},

			'sdatacollection form#datacollectionform fieldset#dcfdcresp grid#dcrespdisplay' : {
				deleteRecord : this.onGridDeleteAction
			},
			
			'sdataprod form#elayoutform fieldset#dpresponsibility grid#dprespdisplay' : {
				deleteRecord : this.onGridDeleteAction
			},
			
			'sdataprod fieldset#dpvariables grid#variabledisplay toolbar button#addvariable' : {
				click : this.addVariable
			},			
			'addquestion button#save' : {
				click : this.saveQuestion
			},
			'addquestiontrans button#save' : {
				click : this.saveQuestionTrans
			},
			'addquestiontrans form#trquestionform fieldset#questiontrans combo#origqcombo' : {
				change : this.changeTranslatedQuestion
			},
			'sconcepts form#conceptsform fieldset#concepts treepanel#conceptsdisplay toolbar button#addconcept' : {
				click : this.addEmptyConcept
			},
			'sconcepts form#conceptsform fieldset#concepts treepanel#conceptsdisplay' : {
				 itemcontextmenu : this.onConceptContextMenu
			},
			'conceptcontextmenu menuitem#addconcept' : {
				 click: this.onMenuAddConcept	
			},
			'conceptcontextmenu menuitem#delete' : {
				 click: this.onMenuDeleteConcept
			},
			'studyadd button#sprev' : {
				 click : this.onPreviousClick			
			}, 
			'studyadd button#snext' : {
				 click : this.onNextClick			
			}, 
			'addvariable form#variableform fieldset#varinfo combo#qcombo' : {
				change : this.changeVariableQuestion
			},
			'addquestion checkbox[name=wvalues]' : {
				change : this.enableWValues

			},
			 "studyadd cancelsave button#save" : {
			        click : this.studySaveClick

			}
	    });
	},

	enableWValues : function (checkbox, newValue, oldValue, eOpts) {
		console.log(newValue);
		if (newValue == true) {
			console.log ('enabled');
			//this.getResponsecodegrid().lock('crespvalue');	
			var col = this.getResponsecodegrid().down('gridcolumn#crespvalue');
			console.log(col);
			col.show();
		} else if (newValue == false) {
			console.log ('disabled');
			//this.getResponsecodegrid().unlock('crespvalue');
			var col = this.getResponsecodegrid().down('gridcolumn#crespvalue');
			console.log(col);
			col.hide();
		}
		

	},
	
	
	addFile : function(button, e, options) {
			console.log ('adding file');
			var win = Ext.WindowMgr.get('addfile');
			if (!win) {
				win = Ext.create('RODAdmin.view.studies.CBEditor.AddFile');
			}
			win.show();
	},
	
	unToggleButtons: function() {
		var buttons = Ext.ComponentQuery.query('studyadd panel#buttongroup button');
		Ext.each(buttons, function(item) {
				if (item.pressed) {
					item.toggle();
				}
		});
	},
	
	onNextClick : function (button, e, options) {
		
		var next = this.getStudyadd().layout.getNext();		
		if (next) {
			this.unToggleButtons();
				this.getStudyadd().getLayout().setActiveItem(next);
				// sa vedem butonul
				var current = this.getStudyadd().getLayout().getActiveItem();
		
				var bname = 'button#'+current.itemId+'button';
				var mybutton = button.up('window').down(bname);
				console.log(mybutton);
				if (!mybutton.pressed) {
					mybutton.toggle();
				}
			}
	},
	
	onPreviousClick : function (button, e, options) {
		var prev = this.getStudyadd().layout.getPrev();		
			if (prev) {
				this.unToggleButtons();
				this.getStudyadd().getLayout().setActiveItem(prev);
				var current = this.getStudyadd().getLayout().getActiveItem();
				console.log(current.itemId);
				var bname = 'button#'+current.itemId+'button';
				var mybutton = button.up('window').down(bname);
				console.log(mybutton);
				mybutton.toggle();		
			}
	},
	
	
	onMenuAddConcept : function (component, event) {
		var conceptspanel = this.getSconcepts().down('fieldset#concepts').down('treepanel#conceptsdisplay');
	    var currentNode = conceptspanel.getSelectionModel().getLastSelected();
		var thisstore = conceptspanel.getStore(); 
		var genlang = this.getMainlang().getValue();
		var newid = this.getTreeStoreNextId(thisstore);
	    currentNode.data.leaf = false;
		if (genlang) {
		    currentNode.appendChild({id: newid, name:"", lang:genlang, leaf:true});	
		} else {
			currentNode.appendChild({id: newid, name:"", lang:"", leaf:true});			
		}
		currentNode.expand();
	},
	
	onMenuDeleteConcept : function (component, event) {
		var conceptspanel = this.getSconcepts().down('fieldset#concepts').down('treepanel#conceptsdisplay');
	    var currentNode = conceptspanel.getSelectionModel().getLastSelected();
		if (!currentNode.remove()) {
			alert('don\'t work');	
		}
	},
	
	
	onConceptContextMenu : function(component, record, item, index, e) {
			 e.stopEvent();
			 console.log(record.data.type);
			   if (!this.itemmenu) {
				   this.itemmenu = Ext.create('RODAdmin.view.studies.CBEditor.ConceptContextMenu');
			   }
			   this.itemmenu.showAt(e.getXY());
	},
	
	changeTranslatedQuestion: function (el, newval, oldval, eOpts) {

		var codestore = this.getTranscodegrid().getStore();
		codestore.removeAll();
		var catstore = this.getTranscatgrid().getStore();
		catstore.removeAll();
		var missingstore = this.getTransmissinggrid().getStore();
		missingstore.removeAll();
		var questionStore = this.getQuestionsgrid().getStore();
		console.log(questionStore);
		var currentQuestion = questionStore.getAt(questionStore.findExact('id', newval));		
		console.log(currentQuestion);

		var missings = currentQuestion.missing().getRange();
				Ext.each(missings, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.Missing(
							{
								label : item.data.label,
								value: 	item.data.value
							});
					missingstore.add(response);
					missingstore.commitChanges();
				});		
		
		
		var rdomain = currentQuestion.data.respdomain;
		if (rdomain == 'Code') {
			console.log('code layout');
			this.getTransdomaincard().getLayout().setActiveItem('trcoderesp');
			//inject into store
			//sa vedem ce store

			var responsecodes = currentQuestion.coderesponses().getRange();
				Ext.each(responsecodes, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.TransCode(
							{
								id : item.data.id,
								origlabel : item.data.label,
								origlang: item.data.lang,
								value: 	item.data.value
							});

					codestore.add(response);
					codestore.commitChanges();
				});
		} else if (rdomain == 'Category') {
			console.log('category layout');		
			this.getTransdomaincard().getLayout().setActiveItem('trcategoryresp');
			var responsecats = currentQuestion.catresponses().getRange();
				Ext.each(responsecats, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.TransCategory(
							{
								id : item.data.id,
								origlabel : item.data.label,
								origlang: item.data.lang
							});

					catstore.add(response);
					catstore.commitChanges();
				});
		} else if (rdomain == 'Numeric') {
			this.getTransdomaincard().getLayout().setActiveItem('numericresp');
		}
	},


	
	changeVariableQuestion: function (el, newval, oldval, eOpts) {

		console.log(el);
		var form = el.up('form#variableform');
		console.log(form);
		var cards = form.down('fieldset#vrinformation');
			console.log(cards);
		//de vazut ce store golim si de ce nu se goleste	
		console.log(newval);
		//empty all stores
		var codestore = this.getVardomaincard().down('grid#qcoderesp').getStore();
			codestore.removeAll();
		var catstore = this.getVardomaincard().down('grid#qcatresp').getStore();
			catstore.removeAll();
		var missingstore = form.down('fieldset#vmissing').down('grid#missing').getStore();
			missingstore.removeAll();

			
		var questionStore = this.getQuestionsgrid().getStore();
		var currentQuestion = questionStore.getAt(questionStore.findExact('id', newval));		
		console.log(currentQuestion);

		var missings = currentQuestion.missing().getRange();
				Ext.each(missings, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.Missing(
							{
								label : item.data.label,
								value: 	item.data.value
							});
					missingstore.add(response);
					missingstore.commitChanges();
				});		
		
		
		
		
		
		var rdomain = currentQuestion.data.respdomain;
		if (rdomain == 'Code') {
			console.log('code layout');
			this.getVardomaincard().getLayout().setActiveItem('coderesp');
			var responsecodes = currentQuestion.coderesponses().getRange();
				Ext.each(responsecodes, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.TransCode(
							{
								id : item.data.id,
								label : item.data.label,
								origlang: item.data.lang,
								value: 	item.data.value
							});
					codestore.add(response);
					codestore.commitChanges();
				});
		} else if (rdomain == 'Category') {
		
			this.getVardomaincard().getLayout().setActiveItem('categoryresp');
			// inject into store

			var responsecats = currentQuestion.catresponses().getRange();
				Ext.each(responsecats, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.TransCategory(
							{
								id : item.data.id,
								label : item.data.label,
								origlang: item.data.lang
							});

					catstore.add(response);
					catstore.commitChanges();
				});
		} else if (rdomain == 'Numeric') {
			this.getVardomaincard().getLayout().setActiveItem('numericresp');
			var numericshit = currentQuestion.numericresponse().getAt(0); 
			var numerictype = this.getVardomaincard().down('textfield#qnrtype');
			var numericlow = this.getVardomaincard().down('textfield#qnrlow');
			var numerichigh = this.getVardomaincard().down('textfield#qnrhigh');
			numerictype.setValue(numericshit.data.type);
			numericlow.setValue(numericshit.data.low);
			numerichigh.setValue(numericshit.data.high);
		}
	},
	
	
	
	
	addVariable : function(button, e, options) {

		var questionstore = this.getQuestionsgrid().getStore();
		if (questionstore.getCount() > 0) {
			var win = Ext.WindowMgr.get('addvariable');
			if (!win) {
				win = Ext.create('RODAdmin.view.studies.CBEditor.AddVariable');
			}
			var questioncombo = win.down('form#variableform').down('fieldset#varinfo').down('combo[name=oqtext]');
			console.log(questioncombo);
			questioncombo.bindStore(questionstore);
			win.setTitle('Add Variable');

			var missingstore = win.down('form#variableform').down('fieldset#vmissing').down('grid#missing').getStore();
			missingstore.removeAll();			
			var codestore = win.down('form#variableform').down('fieldset#vrinformation').down('grid#qcoderesp').getStore();
			codestore.removeAll();			
			var catstore = win.down('form#variableform').down('fieldset#vrinformation').down('grid#qcatresp').getStore();
			catstore.removeAll();			
			
			
			win.show();
		} else {
			alert('No questions available');
			return;
		}
	},
	
	
	
	
	addQuestionTranslation : function(button, e, options) {

		var questionstore = this.getQuestionsgrid().getStore();
		if (questionstore.getCount() > 0) {
			var win = Ext.WindowMgr.get('addquestiontrans');
			if (!win) {
				win = Ext.create('RODAdmin.view.studies.CBEditor.AddQuestionTranslation');
			}
			var questioncombo = win.down('form#trquestionform').down('fieldset#questiontrans').down('combo[name=oqtext]');
			questioncombo.bindStore(questionstore);
//sa golim store-urile			
			var missingstore = win.down('form#trquestionform').down('fieldset#qrmissing').down('grid#missing').getStore();
			missingstore.removeAll();
			var qcodestore = win.down('form#trquestionform').down('fieldset#qrinformation').down('grid#qcoderesp').getStore();
			qcodestore.removeAll();
			var qcatstore = win.down('form#trquestionform').down('fieldset#qrinformation').down('grid#qcatresp').getStore();
			qcatstore.removeAll();
			
			
			win.setTitle('Add Question Translation');
			win.show();
		} else {
			alert('No questions available');
			return;
		}
	},

	onVariableGridEditAction: function(grid, record, rowIndex, row) {
			var myrecord = grid.getStore().getAt(rowIndex);
			var win = Ext.WindowMgr.get('addvariable');
			if (!win) {
				win = Ext.create('RODAdmin.view.studies.CBEditor.AddVariable');
			}
			win.setTitle('Edit variable ' + myrecord.data.name);
			win.setMode('edit');
			win.setEditId(rowIndex);	
			win.down('form#variableform').getForm().loadRecord(myrecord);
			win.down('form#variableform').down('fieldset#varinfo').down('combo#qcombo').setValue(myrecord.data.oqid);
			win.down('form#variableform').down('fieldset#varinfo').down('combo#qcombo').disable();
// empty stores
			var missingstore = win.down('form#variableform').down('fieldset#vmissing').down('grid#missing').getStore();
			missingstore.removeAll();			
			var codestore = win.down('form#variableform').down('fieldset#vrinformation').down('grid#qcoderesp').getStore();
			codestore.removeAll();			
			var catstore = win.down('form#variableform').down('fieldset#vrinformation').down('grid#qcatresp').getStore();
			catstore.removeAll();			

			//acu umplem storeurile cu chestiile intrebarii curente			

			
			
			
		var questionStore = this.getQuestionsgrid().getStore();
		var currentQuestion = questionStore.getAt(questionStore.findExact('id', myrecord.data.oqid));		
		console.log(currentQuestion);

		var missings = currentQuestion.missing().getRange();
				Ext.each(missings, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.Missing(
							{
								label : item.data.label,
								value: 	item.data.value
							});
					missingstore.add(response);
					missingstore.commitChanges();
				});		
		
		
		var rdomain = currentQuestion.data.respdomain;
		if (rdomain == 'Code') {
			this.getVardomaincard().getLayout().setActiveItem('coderesp');
			var responsecodes = currentQuestion.coderesponses().getRange();
				Ext.each(responsecodes, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.TransCode(
							{
								id : item.data.id,
								label : item.data.label,
								origlang: item.data.lang,
								value: 	item.data.value
							});
					codestore.add(response);
					codestore.commitChanges();
				});
		} else if (rdomain == 'Category') {
			this.getVardomaincard().getLayout().setActiveItem('categoryresp');
			// inject into store

			var responsecats = currentQuestion.catresponses().getRange();
				Ext.each(responsecats, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.TransCategory(
							{
								id : item.data.id,
								label : item.data.label,
								origlang: item.data.lang
							});

					catstore.add(response);
					catstore.commitChanges();
				});
		} else if (rdomain == 'Numeric') {
			this.getVardomaincard().getLayout().setActiveItem('numericresp');
			var numericshit = currentQuestion.numericresponse().getAt(0); 
			var numerictype = this.getVardomaincard().down('textfield#qnrtype');
			var numericlow = this.getVardomaincard().down('textfield#qnrlow');
			var numerichigh = this.getVardomaincard().down('textfield#qnrhigh');
			numerictype.setValue(numericshit.data.type);
			numericlow.setValue(numericshit.data.low);
			numerichigh.setValue(numericshit.data.high);
		}
			
			win.show();	
	},	
	
	onTranslationGridEditAction: function(grid, record, rowIndex, row) {
		var myrecord = grid.getStore().getAt(rowIndex);
		// intai incercam manual, apoi vedem daca putem lega cu storeul
		var win = Ext.WindowMgr.get('addquestiontrans');
		if (!win) {
			win = Ext.create('RODAdmin.view.studies.CBEditor.AddQuestionTranslation');
		}
		win.setTitle('Edit Question Translation' + myrecord.data.text);
		win.setMode('edit');
		win.setEditId(rowIndex);	
		win.down('form#trquestionform').getForm().loadRecord(myrecord);
		win.down('form#trquestionform').down('fieldset#questiontrans').down('combo#origqcombo').setValue(myrecord.data.oqid);
		win.down('form#trquestionform').down('fieldset#questiontrans').down('combo#origqcombo').disable();
		
//empty all stores
		var codestore = this.getTranscodegrid().getStore();
		codestore.removeAll();
		var catstore = this.getTranscatgrid().getStore();
		catstore.removeAll();
		var missingstore = this.getTransmissinggrid().getStore();
		missingstore.removeAll();
		var questionStore = this.getQuestionsgrid().getStore();
		console.log(questionStore);

		
		var currentQuestion = questionStore.getAt(questionStore.findExact('id', myrecord.data.oqid));		


	// put missings	
		var newMStore = myrecord.missing();
		missingstore.loadRecords(newMStore.getRange(0, newMStore.getCount()), {
						addRecords : false
		});

		
		var rdomain = currentQuestion.data.respdomain;

		if (rdomain == 'Code') {
			console.log('code layout');
			this.getTransdomaincard().getLayout().setActiveItem('trcoderesp');
				
			var newStore = myrecord.coderesponses();

			codestore.loadRecords(newStore.getRange(0, newStore.getCount()), {
						addRecords : false
					});				
				
		} else if (rdomain == 'Category') {
			console.log('category layout');		
			this.getTransdomaincard().getLayout().setActiveItem('trcategoryresp');
			var newStore = myrecord.catresponses();
			catstore.loadRecords(newStore.getRange(0, newStore.getCount()), {
						addRecords : false
					});
			
		} else if (rdomain == 'Numeric') {
			this.getTransdomaincard().getLayout().setActiveItem('numericresp');
		}
		
		
		
		win.show();		
	},
	
	
	onQuestionEditAction : function(grid, record, rowIndex, row) {
		console.log('edit shit');
		var myrecord = grid.getStore().getAt(rowIndex);
		// intai incercam manual, apoi vedem daca putem lega cu storeul
		var win = Ext.WindowMgr.get('addquestion');
		if (!win) {
			win = Ext.create('RODAdmin.view.studies.CBEditor.AddQuestion');
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
		var catgrid = cards.down('grid#qcatresp');
		var missinggrid = win.down('grid#missing');
		var numerictype = cards.down('combo#qnrtype');
		
		var nlow = cards.down('textfield[name=qnrlow]');
		var nhigh = cards.down('textfield[name=qnrhigh]');
		// golim toate storeurile
		var codestore = codegrid.getStore();
		codestore.getProxy().clear();
		codestore.data.clear();
		codestore.sync();
		var catstore = catgrid.getStore();
		catstore.getProxy().clear();
		catstore.data.clear();
		catstore.sync();
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
		var newMStore = myrecord.missing();
		missingstore.loadRecords(newMStore.getRange(0, newMStore.getCount()), {
						addRecords : false
		});
		
		
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

	saveVariable : function(button, e, options) {
		//this should be easy, it has only name and label and related question info
			var questionid = button.up('window').down('form').down('fieldset#varinfo').down('combo[name=oqtext]').getValue();
			var questionname = button.up('window').down('form').down('fieldset#varinfo').down('combo[name=oqtext]').getRawValue();
			
			var varname = button.up('window').down('form').down('fieldset#varinfo').down('textfield[name=name]').getValue();
			var varlabel = button.up('window').down('form').down('fieldset#varinfo').down('textfield[name=label]').getValue();

			
			var questionStore = this.getQuestionsgrid().getStore();
			var currentQuestion = questionStore.getAt(questionStore.findExact('id', questionid));		
			if (varname.length < 2) {
				alert('variable name too small');
				return;
			}			
			if (varlabel.length < 2) {
				alert('variable label too small');
			}
			
			if (button.up('window').getMode() == 'edit') {		
		
			} else {
					var ourgrid = this.getVariablegrid();
					var ourstore = ourgrid.getStore();
					var nextId = this.getStoreNextId(ourstore);

					var variable = new RODAdmin.model.studies.CBEditor.StudyVariable({
							id : nextId,
							oqid: currentQuestion.data.id,
							oqtext : currentQuestion.data.text,
							name : varname,
							label : varlabel
						});
				ourstore.add(variable);
				ourstore.commitChanges();
			}		
		button.up('window').close();
	},

	saveFile : function(button, e, options) {
		//this should be easy, it has only name and label and related question info
			console.log('savefile');

			console.log(button.up('window').down('form').down('fieldset#fileinfo'));
			
			
			
			var filename = button.up('window').down('form').down('fieldset#fileinfo').down('textfield[name=name]').getValue();
			var fileuri = button.up('window').down('form').down('fieldset#fileinfo').down('textfield[name=uri]').getValue();

			var filetypeid = button.up('window').down('form').down('fieldset#fileinfo').down('combo[name=ftype]').getValue();
			var filetypename = button.up('window').down('form').down('fieldset#fileinfo').down('combo[name=ftype]').getRawValue();

			var filecases = button.up('window').down('form').down('fieldset#datafinfo').down('textfield[name=cases]').getValue();
			var filereccount = button.up('window').down('form').down('fieldset#datafinfo').down('textfield[name=reccount]').getValue();
			
			
			var filestore = this.getFilesgrid().getStore();

			if (filename.length < 2) {
				alert('file name too small');
				return;
			}			
			
			var nextId = this.getStoreNextId(filestore);

			var file = new RODAdmin.model.studies.CBEditor.File({
							id : nextId,
							fname: filename,
							uri : fileuri,
							ftype : filetypename,
							ftypeid : filetypeid,
							cases: filecases,
							reccount: filereccount
						});
				filestore.add(file);
				filestore.commitChanges();
		button.up('window').close();
	},
	
	
	
	
	saveQuestionTrans : function(button, e, options) {
		
			var questionid = button.up('window').down('form').down('fieldset#questiontrans').down('combo[name=oqtext]').getValue();
			var questionname = button.up('window').down('form').down('fieldset#questiontrans').down('combo[name=oqtext]').getRawValue();
			var questionlang = button.up('window').down('form').down('fieldset#questiontrans').down('combo[name=lang]').getValue();
			var trtext = button.up('window').down('form').down('fieldset#questiontrans').down('textareafield#trtextq').getValue();
			var questionStore = this.getQuestionsgrid().getStore();
			var currentQuestion = questionStore.getAt(questionStore.findExact('id', questionid));		
			if (trtext.length < 2) {
				alert('question text too small');
				return;
			}			
			if (questionlang.length < 2) {
				alert('please select a language');
			} else {
					if (questionlang == currentQuestion.data.lang) {
							alert(questionlang+' is the original language');
							return;
					}		
			}
			
			if (button.up('window').getMode() == 'edit') {		
		
			} else {
					var ourgrid = this.getQuestionstransgrid();
					var ourstore = ourgrid.getStore();
					var nextId = this.getStoreNextId(ourstore);

					var transquestion = new RODAdmin.model.studies.CBEditor.TranslatedQuestion({
							id : nextId,
							oqid: currentQuestion.data.id,
							text : trtext,
							oqtext : currentQuestion.data.text,
							oqlang : currentQuestion.data.lang,
							lang : questionlang,
							respdomain : currentQuestion.data.respdomain
						});

						//missing here
					var missingstore = button.up('window').down('form').down('fieldset#qrmissing').down('grid#missing').getStore();
					var responsecodes = missingstore.getRange();
					Ext.each(responsecodes, function(item) {
					var missing = new RODAdmin.model.studies.CBEditor.question.Missing(
							{
								value : item.data.value,
								label : item.data.label
							});
					transquestion.missing().add(missing);
					transquestion.missing().commitChanges();
					});
						
					// acu sa vedem care e raspunsul
				if (currentQuestion.data.respdomain == 'Code') {
					var codestore = button.up('window').down('form').down('fieldset#qrinformation').down('grid#qcoderesp').getStore();
					var responsecodes = codestore.getRange();
					Ext.each(responsecodes, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.Code(
							{
								id : item.data.id,
								value : item.data.value,
								label : item.data.label,
								lang : item.data.lang
							});
					transquestion.coderesponses().add(response);
					transquestion.coderesponses().commitChanges();
				});
				ourstore.add(transquestion);
				ourstore.commitChanges();
				} else if (currentQuestion.data.respdomain == 'Category') {
					var catstore = button.up('window').down('form').down('fieldset#qrinformation').down('grid#qcatresp').getStore();
					console.log(catstore);
					var responsecats = catstore.getRange();
					Ext.each(responsecats, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.Category(
							{
								id : item.data.id,
								label : item.data.label,
								lang : item.data.lang
							});
					transquestion.catresponses().add(response);
					transquestion.catresponses().commitChanges();
				});
				// var ourgrid = this.getQuestionsgrid();
				// var ourstore = ourgrid.getStore();
				ourstore.add(transquestion);
				ourstore.commitChanges();

					
					
					
					
					}	
			} 			//end add mode
			button.up('window').close();
	},
	
	saveQuestion : function(button, e, options) {
		// validari aici pentru partea generala
			console.log('savequestion');
			var questiontext = button.up('window').down('form').down('fieldset#questioninfo').down('textareafield[name=text]').getValue();
			var questionlang = button.up('window').down('form').down('fieldset#questioninfo').down('combo[name=lang]')	.getValue();
			var questionconcept = button.up('window').down('form').down('fieldset#questioninfo').down('treecombo[name=concept_id]').getValue();
			var questionconceptname = button.up('window').down('form').down('fieldset#questioninfo').down('treecombo[name=concept_id]').getRawValue();
			var responsedomain = button.up('window').down('form').down('fieldset#questioninfo').down('combo[name=respdomain]').getValue();
			
			if (questiontext.length < 2) {
				alert('question text too small');
				return;
			}
			if (questionlang.length < 2) {
				alert('question original language too small');
			}
			if (responsedomain.length < 2) {
				alert('response domain too small');
			}
			
			
		// do we edit or don't we
		if (button.up('window').getMode() == 'edit') {
			console.log('editMode, do something');
			var editId = button.up('window').getEditId();
			console.log('editId' + button.up('window').getEditId());
			var questionStore = this.getQuestionsgrid().getStore();
			var editedQuestion = questionStore.getAt(editId);
			console.log(editedQuestion);
//			questionStore.insert(question);
//			ourstore.commitChanges();
					
			editedQuestion.set('text',questiontext);
			console.log('after text');
			editedQuestion.set('lang',questionlang);			
			console.log('after lang');
			editedQuestion.set('respdomain',responsedomain);			
			editedQuestion.set('concept',questionconcept);
			editedQuestion.catresponses().data.clear();
			editedQuestion.coderesponses().data.clear();
			editedQuestion.numericresponse().data.clear();
			editedQuestion.missing().data.clear();

//missing					
			var thisstore = button.up('window').down('form')
						.down('fieldset#missinginfo').down('grid#missing')
						.getStore();
				var responsecodes = thisstore.getRange();
					Ext.each(responsecodes, function(item) {
					var missing = new RODAdmin.model.studies.CBEditor.question.Missing(
							{
//								id : item.data.id,
								value : item.data.value,
								label : item.data.label
							});
					editedQuestion.missing().add(missing);
					editedQuestion.missing().commitChanges();
				});		
			
			
			
			var activertype = button.up('window').down('form').down('fieldset#qrinformation').getLayout().getActiveItem();
			console.log(responsedomain);			
			if (responsedomain == 'Code') {

				var thisstore = button.up('window').down('form')
						.down('fieldset#qrinformation').down('grid#qcoderesp')
						.getStore();
				var responsecodes = thisstore.getRange();
					Ext.each(responsecodes, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.Code(
							{
								id : item.data.id,
								value : item.data.value,
								label : item.data.label,
								lang : item.data.lang
							});
					editedQuestion.coderesponses().add(response);
					editedQuestion.coderesponses().commitChanges();
				});
			} else if (responsedomain == 'Category') {
				var catstore = button.up('window').down('form').down('fieldset#qrinformation').down('grid#qcatresp').getStore();
				var responsecats = catstore.getRange();
				Ext.each(responsecats, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.Category(
							{
								id : item.data.id,
								label : item.data.label,
								lang : item.data.lang
							});
					editedQuestion.catresponses().add(response);
					editedQuestion.catresponses().commitChanges();
				});
			} else if (responsedomain == 'Numeric') {
				var numresponsetype = activertype.down('combo#qnrtype')
						.getValue();
				var numresponselow = activertype.down('textfield[name=qnrlow]')
						.getValue();
				var numresponsehigh = activertype
						.down('textfield[name=qnrhigh]').getValue();
					var numericresponse = new RODAdmin.model.studies.CBEditor.question.response.Numeric(
						{
							type : numresponsetype,
							low : numresponselow,
							high : numresponsehigh
						});
				question.numericresponse().add(numericresponse);
				question.numericresponse().commitChanges();
				
			}	
//start translations		

			button.up('window').close();
			
		} else {

			// end validari
			console.log('add mode');
			var ourgrid = this.getQuestionsgrid();
			var ourstore = ourgrid.getStore();
			var nextId = this.getStoreNextId(ourstore);
			console.log(nextId);
			var question = new RODAdmin.model.studies.CBEditor.StudyQuestion({
						id : nextId,
						text : questiontext,
						lang : questionlang,
						concept_id: questionconcept,
						conceptname: questionconceptname,
						respdomain : responsedomain
					});
					
//missing					
			var thisstore = button.up('window').down('form')
						.down('fieldset#missinginfo').down('grid#missing')
						.getStore();
				var responsecodes = thisstore.getRange();
					Ext.each(responsecodes, function(item) {
					var missing = new RODAdmin.model.studies.CBEditor.question.Missing(
							{
//								id : item.data.id,
								value : item.data.value,
								label : item.data.label
							});
					question.missing().add(missing);
					question.missing().commitChanges();
				});		
					
			var activertype = button.up('window').down('form')
					.down('fieldset#qrinformation').getLayout().getActiveItem();

			
			if (activertype.itemId == 'catresp') {

				// sa vedem cum obtinem datele din store. Intai ne trebuie
				// store-ul
				var thisstore = button.up('window').down('form')
						.down('fieldset#qrinformation').down('grid#qcoderesp')
						.getStore();
				var responsecodes = thisstore.getRange();
				// avem intrebarea si avem si raspunsurile, sa vedem daca putem
				// sa facem o relatie
				// modelele trebuie instantiate din nou, nu stiu de ce dar
				// banuiesc ca din lipsa de id
				Ext.each(responsecodes, function(item) {
					var response = new RODAdmin.model.studies.CBEditor.question.response.Code(
							{
								id : item.data.id,
								value : item.data.value,
								label : item.data.label,
								lang : item.data.lang
							});
					question.coderesponses().add(response);
					question.coderesponses().commitChanges();
				});
				ourstore.add(question);
				ourstore.commitChanges();
				button.up('window').close();
			} else if (activertype.itemId == 'numericresp') {
				console.log('numeric active');
				// asta e cel mai usor
				var numresponsetype = activertype.down('combo#qnrtype')
						.getValue();
				var numresponselow = activertype.down('textfield[name=qnrlow]')
						.getValue();
				var numresponsehigh = activertype
						.down('textfield[name=qnrhigh]').getValue();
				var numericresponse = new RODAdmin.model.studies.CBEditor.question.response.Numeric(
						{
							type : numresponsetype,
							low : numresponselow,
							high : numresponsehigh
						});
				question.numericresponse().add(numericresponse);
				question.numericresponse().commitChanges();
				ourstore.add(question);
				ourstore.commitChanges();
				button.up('window').close();		
			} else if (activertype.itemId == 'stringresp') {
				ourstore.add(question);
				ourstore.commitChanges();
				button.up('window').close();		
			}
			
				var tg = this.getTransfsgrid();
				tg.expand();
		}
	},

	onGridDeleteAction : function(grid, record, rowIndex, row) {
		grid.getStore().removeAt(rowIndex);
	},

	
	onGridQuestionDeleteAction : function(grid, record, rowIndex, row) {
		console.log('dependente???');
		console.log(record);
		//sa vedem cum aflam daca exista
		//avem doua chestii care depind de intrebari, traduceri si variabile. In mod normal, storeurile sunt incarcate asa ca ar trebui sa putem obtine referinte catre ele
		var translstore = this.getQuestionstransgrid().getStore();
		var varstore = this.getVariablegrid().getStore();
		console.log(translstore);	
		console.log(varstore);	
		//acu, daca avem cele doua storeuri trebuie sa cautam asa:
		// in translation store toate intrarile care au oqid corespunzator cu cel curent
		// in varstore la fel
		console.log('----------dataid');
		console.log(record.data.id);
		var transl = translstore.findExact('oqid', record.data.id);		
		var varbl = varstore.findExact('oqid', record.data.id);		

		
		if (transl != -1 || varbl != -1) {
			alert('Cannot delete question, it has dependencies. Please remove translations or variables and try again');
//		if (transl != -1) {
//			//let's get it
//			var ctrans =  translstore.getAt(transl);		
//			Ext.MessageBox.show({
//  			  title: "Question has dependencies",
//    			msg: "Translation: " + ctrans.data.text + "is connected to this question",
//    			icon: Ext.MessageBox.WARNING,
//    			buttons: Ext.MessageBox.OKCANCEL,
//    			fn: function(buttonId) {
//        			if (buttonId === "ok") {
//						translstore.removeAt(transl);
//        				grid.getStore().removeAt(rowIndex);
//        			}
//    				}
//				});
//		} else if (varbl != -1) {
//			var cvarbl =  varstore.getAt(varbl);
//			Ext.MessageBox.show({
//  			  title: "Question has dependencies",
//    			msg: "Variable: " + cvarbl.data.label + "is connected to this question",
//    			icon: Ext.MessageBox.WARNING,
//    			buttons: Ext.MessageBox.OKCANCEL,
//    			fn: function(buttonId) {
//        			if (buttonId === "ok") {
//						varstore.removeAt(varbl);
//        				grid.getStore().removeAt(rowIndex);
//        			}
//    				}
//				});
		} else {
			grid.getStore().removeAt(rowIndex);
		}
		console.log(transl);
		console.log(varbl);
		
//		grid.getStore().removeAt(rowIndex);		
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

	addEmptyResponseCode : function(button, e, options) {
		// sa vedem daca avem limba la intrebarea curenta
		var qlang = button.up('window').down('fieldset#questioninfo').down('combo[name=lang]').getValue();
		var ourstore = this.getResponsecodegrid().getStore();
		var nextStoreId = this.getStoreNextId(ourstore);
		if (qlang) {
			var newrecord = new RODAdmin.model.studies.CBEditor.question.response.Code({
					id : nextStoreId,
					value : '',
					label : '',
					lang : qlang
				});
			ourstore.add(newrecord);
			ourstore.commitChanges();
		} else {
			var newrecord = new RODAdmin.model.studies.CBEditor.question.response.Code({
					id : nextStoreId,
					value : '',
					label : '',
					lang : ''
				});
			ourstore.add(newrecord);
			ourstore.commitChanges();
		}
	},

	addEmptyMissing : function(button, e, options) {
		var ourstore = this.getMissinggrid().getStore();
		var nextStoreId = this.getStoreNextId(ourstore);
		var newrecord = new RODAdmin.model.studies.CBEditor.question.Missing({
					id : nextStoreId,
					value : '',
					label : ''
				});
		ourstore.add(newrecord);
		ourstore.commitChanges();
	},

	
	
	addEmptyResponseCat : function(button, e, options) {
		var qlang = button.up('window').down('fieldset#questioninfo').down('combo[name=lang]').getValue();
		var ourstore = this.getResponsecatgrid().getStore();
		var nextStoreId = this.getStoreNextId(ourstore);
		if (qlang) {
			var newrecord = new RODAdmin.model.studies.CBEditor.question.response.Category({
					id : nextStoreId,
					value : '',
					label : '',
					lang : qlang
				});
			ourstore.add(newrecord);
			ourstore.commitChanges();
		} else {
			var newrecord = new RODAdmin.model.studies.CBEditor.question.response.Category({
					id : nextStoreId,
					value : '',
					label : '',
					lang : ''
				});
			ourstore.add(newrecord);
			ourstore.commitChanges();
		}
	},

	addEmptyQuestion : function(button, e, options) {
		console.log('add empty question');
		var newrecord = new RODAdmin.model.studies.CBEditor.StudyQuestion({
					qvalue : '',
					qlabel : '',
					lang : 'ro',
					respdomain : 'code'
				});

		var ourstore = this.getSquestions().down('fieldset#questions')
				.down('grid#questionsdisplay').getStore();
		var ourgrid = this.getSquestions().down('fieldset#questions')
				.down('grid#questionsdisplay');
		ourstore.add(newrecord);
		ourstore.commitChanges();
		console.log(Ext.Array.pluck(ourstore.data.items, 'data'));
	},

	addEmptyConcept : function(button, e, options) {
		//sa vedem care e limba aia generala, daca e
		var genlang = this.getMainlang().getValue();
		if (genlang) {
			var newrecord = new RODAdmin.model.studies.CBEditor.Concept({
					name : '',
					lang : genlang,
					leaf: true
				});
			var ourstore = this.getSconcepts().down('fieldset#concepts').down('treepanel#conceptsdisplay').getStore();
			var root  = ourstore.getRootNode();
			root.appendChild(newrecord);
		} else {
			var newrecord = new RODAdmin.model.studies.CBEditor.Concept({
					name : '',
					lang : '',
					leaf: true
			});
			var ourstore = this.getSconcepts().down('fieldset#concepts').down('treepanel#conceptsdisplay').getStore();
			var root  = ourstore.getRootNode();
			root.appendChild(newrecord);
		}	
	},

	
	/**
	 * @method
	 */
	sProposalClick : function(button, e, options) {
		console.log('sproposal');
		this.refreshWindowStore();		
		this.getStudyadd().layout.setActiveItem('sproposal');
		this.unToggleButtons();
		var mybutton = button.up('window').down('button#sproposalbutton');
		console.log(mybutton);
		mybutton.toggle();
	},
	/**
	 * @method
	 */
	sFoundingClick : function(button, e, options) {
		console.log('Founding');
		this.refreshWindowStore();
		this.getStudyadd().layout.setActiveItem('sfunding');
		this.unToggleButtons();
		var mybutton = button.up('window').down('button#sfundingbutton');
		console.log(mybutton);
		mybutton.toggle();
		},
	/**
	 * @method
	 */
	sConceptsClick : function(button, e, options) {
		console.log('Concepts');
		this.refreshWindowStore();		
		this.getStudyadd().layout.setActiveItem('sconcepts');
		this.unToggleButtons();
		var mybutton = button.up('window').down('button#sconceptsbutton');
		console.log(mybutton);
		mybutton.toggle();
	},
	/**
	 * @method
	 */
	sQuestionsClick : function(button, e, options) {
		console.log('Questions');
		this.refreshWindowStore();		
		this.getStudyadd().layout.setActiveItem('squestions');
		this.unToggleButtons();
		var mybutton = button.up('window').down('button#squestionsbutton');
		console.log(mybutton);
		mybutton.toggle();
	},
	/**
	 * @method
	 */
	sDataCollectionClick : function(button, e, options) {
		console.log('Data Collection');
		this.refreshWindowStore();
		this.getStudyadd().layout.setActiveItem('sdatacollection');
		this.unToggleButtons();
		var mybutton = button.up('window').down('button#sdatacollectionbutton');
		console.log(mybutton);
		mybutton.toggle();
	},
	/**
	 * @method
	 */
	sDataProductionClick : function(button, e, options) {
		console.log('Data production');
		this.refreshWindowStore();
		this.getStudyadd().layout.setActiveItem('sdataprod');
		this.unToggleButtons();
		var mybutton = button.up('window').down('button#sdataprodbutton');
		console.log(mybutton);
		mybutton.toggle();
	},
	/**
	 * @method
	 */
	addPrincipalInvestigator : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		var ourstore = this.getSproposal().down('fieldset#prinvfs')
				.down('grid#prinvdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Principal Investigator');
		win.show();
	},

	addFoundingAgency : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		console.log(this.getSfunding());
		var ourstore = this.getSfunding().down('fieldset#funding')
				.down('grid#fundvdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Founding Agency');
		win.show();
	},

	addQuestionaireDesign : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		var ourstore = this.getSquestions().down('fieldset#qdesign')
				.down('grid#qdesigndisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Questionaire design Responsibility Entity');
		win.show();
	},

	addQtranslation : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		var ourstore = this.getSquestions().down('fieldset#qtransation')
				.down('grid#qtranslationdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Questionaire design Responsibility Entity');
		win.show();
	},

	addSamplingresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		var ourstore = this.getSdatacollection()
				.down('fieldset#dcfsamplingresp')
				.down('grid#qsamplingrespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Sampling Responsibility Entity');
		win.show();
	},

	addDCollectionresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		var ourstore = this.getSdatacollection().down('fieldset#dcfdcresp')
				.down('grid#dcrespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Sampling Responsibility Entity');
		win.show();
	},

	addDProdresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		var ourstore = this.getSdataprod().down('fieldset#dpresponsibility')
				.down('grid#dprespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Data Production Responsibility Entity');
		win.show();
	},

	addConceptresp : function(button, e, options) {
		var win = Ext.create('RODAdmin.view.studies.CBEditor.AddPersonOrg');
		var ourstore = this.getSconcepts().down('fieldset#cnresponsibility')
				.down('grid#cnrespdisplay').getStore();
		win.setOriginalStore(ourstore);
		win.setTitle('Add Concepts Responsibility Entity');
		win.show();
	},
	
	
	addQuestion : function(button, e, options) {
		var win = Ext.WindowMgr.get('addquestion');
		if (!win) {
			win = Ext.create('RODAdmin.view.studies.CBEditor.AddQuestion');
		}

		// set general language value
		var genlang = this.getMainlang().getValue();
		var langcombo = win.down('form#questionform').down('fieldset#questioninfo').down('combo[name=lang]');
		if (genlang) {
			langcombo.setValue(genlang);
		}
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
//		var catstore = catgrid.getStore();
//		catstore.getProxy().clear();
//		catstore.data.clear();
//		catstore.sync();
		var missingstore = missinggrid.getStore();
		missingstore.getProxy().clear();
		missingstore.data.clear();
		missingstore.sync();
		win.setTitle('Add Question');
		win.show();
	},

	savePersonOrg : function(button, e, options) {
		console.log(button.up('window').getOriginalStore());
		var ourstore = button.up('window').getOriginalStore();
		// console.log(button.up('window'));
		// salvam formularul din tabul activ
		var activeform = button.up('window').down('tabpanel').getActiveTab();
		// console.log(activeform);

		if (activeform.itemId == 'personform') {
			console.log('person active');
			var existingselected = activeform.down('fieldset#experson')
					.down('combo#expersonselect').getValue();
			if (existingselected > 0) {
				var personitem = activeform.down('fieldset#experson')
						.down('combo#expersonselect')
						.findRecordByValue(existingselected);
				var selectedname = personitem.data.name;
				// var ourstore =
				// this.getSproposal().down('fieldset#prinvfs').down('grid#prinvdisplay').getStore();
				var newrecord = {
					'type' : 'person',
					'id' : existingselected,
					'selectedname' : selectedname,
					'status' : 'existing'
				};
				ourstore.add(newrecord);
				button.up('window').close();
			} else {
				// presupunem ca avem un new si incercam sa facem ceva cu el
				var addfs = activeform.down('fieldset#newperson');
				console.log(addfs);
				var cprefix = addfs.down('combo#cprefix').getValue();
				var csufix = addfs.down('combo#csuffix').getValue();
				var cfname = addfs.down('textfield[name=fname]').getValue();
				var clname = addfs.down('textfield[name=lname]').getValue();
				var cemail = addfs.down('textfield[name=email]').getValue();
				var cphone = addfs.down('textfield[name=phone]').getValue();
				var addressfs = addfs.down('fieldset#newpersonAddress');
				var address1 = addressfs.down('textfield[name=address1]')
						.getValue();
				var address2 = addressfs.down('textfield[name=address2]')
						.getValue();
				var pcode = addressfs.down('textfield[name=postalcode]')
						.getValue();
				var city = addressfs.down('combo[name=city]').getValue();

				var ourstore = this.getSproposal().down('fieldset#prinvfs')
						.down('grid#prinvdisplay').getStore();
				var newrecord = {
					'type' : 'person',
					'selectedname' : cfname + ' ' + clname,
					'status' : 'new',
					'persprefix' : cprefix,
					persfname : cfname,
					perslname : clname,
					persemail : cemail,
					persphone : cphone,
					persaddr1 : address1,
					persaddr2 : address2,
					perscode : pcode,
					perscity : city
				};
				ourstore.add(newrecord);
				button.up('window').close();
			}

		} else if (activeform.itemId == 'orgform') {
			console.log('org active');
			var exorgselected = activeform.down('fieldset#exorg')
					.down('combo#exorgselect').getValue();
			if (exorgselected > 0) {
				var orgitem = activeform.down('fieldset#exorg')
						.down('combo#exorgselect')
						.findRecordByValue(exorgselected);
				var selectedname = orgitem.data.name;
				// var ourstore =
				// this.getSproposal().down('fieldset#prinvfs').down('grid#prinvdisplay').getStore();
				var newrecord = {
					'type' : 'org',
					'id' : exorgselected,
					'selectedname' : selectedname,
					'status' : 'existing'
				};
				ourstore.add(newrecord);
				button.up('window').close();
			} else {
				var addorg = activeform.down('fieldset#neworg');
				var cprefix = addorg.down('combo#orgprefix').getValue();
				var csufix = addorg.down('combo#orgsuffix').getValue();
				var csname = addorg.down('textfield[name=shortname]')
						.getValue();
				var cfname = addorg.down('textfield[name=fullname]').getValue();
				// var cemail = addorg.down('textfield[name=email]').getValue();
				// var cphone = addorg.down('textfield[name=phone]').getValue();
				var addressfs = addorg.down('fieldset#neworgAddress');
				var address1 = addressfs.down('textfield[name=address1]')
						.getValue();
				var address2 = addressfs.down('textfield[name=address2]')
						.getValue();
				var pcode = addressfs.down('textfield[name=postalcode]')
						.getValue();
				var city = addressfs.down('combo[name=city]').getValue();
				var ourstore = this.getSproposal().down('fieldset#prinvfs')
						.down('grid#prinvdisplay').getStore();
				var newrecord = {
					'type' : 'org',
					'selectedname' : csname,
					'status' : 'new',
					'orgprefix' : cprefix,
					orgsname : csname,
					orglname : clname,
					// orgemail: cemail,
					// orgphone: cphone,
					orgaddr1 : address1,
					orgaddr2 : address2,
					orgcode : pcode,
					orgcity : city
				};
				ourstore.add(newrecord);
				button.up('window').close();

			}
		}
		// //sa vedem daca avem existing
		// var existingselected =
		// activeform.down('fieldset#experson').down('combo#expersonselect').getValue();
		// if (existingselected > 0) {
		// var personitem =
		// activeform.down('fieldset#experson').down('combo#expersonselect').findRecordByValue(existingselected);
		// var selectedname = personitem.data.name;
		// var ourstore =
		// this.getSproposal().down('fieldset#prinvfs').down('grid#prinvdisplay').getStore();
		// var newrecord = {'type': 'person', 'id': existingselected,
		// 'selectedname': selectedname, 'status': 'existing'};
		//    			
		// ourstore.add(newrecord);
		// button.up('window').close();
		// console.log(ourstore);
		// }
		//
		//		
		// var eorgselected =
		// activeform.down('fieldset#exorg').down('combo#exorgselect').getValue();
		// if (exorgselected > 0) {
		// //adaugam chestia in storeul din dataview
		// //sa vedem cum capatam si eticheta
		// var orgitem =
		// activeform.down('fieldset#exorg').down('combo#exorgselect').findRecordByValue(exorgselected);
		// var selectedname = orgitem.data.name;
		// var ourstore =
		// this.getSproposal().down('fieldset#prinvfs').down('grid#prinvdisplay').getStore();
		// var newrecord = {'type': 'org', 'id': exorgselected, 'selectedname':
		// selectedname, 'status': 'existing'};
		// ourstore.add(newrecord);
		// button.up('window').close();
		// console.log(ourstore);
		// }
		//		

		// var personform =
		// button.up('window').down('tabpanel').down('form#personform');
		// var orgform =
		// console.log(button.up('window').down('tabpanel').down('form#orgform'));

		// console.log(personform);
		// console.log(orgform);

		// console.log(button.up('window').down('tabpanel').query('form#personform'));
		// console.log(button.up('window').down('tabpanel').query('form#orgform'));

		// console.log(button.up('window').down('form
		// #personform').down('fieldset #experson'));

		// console.log(button.up('fieldset #experson')).down('combo
		// #expersonselect');

		// sa vedem daca avem ceva selectat
		// in primul rand sa vedem daca sunt selectate alea existente

	},

	studySaveClick  : function(button, e, options) {
		this.refreshWindowStore();
		 var win = button.up('studyadd');
	     win.close();
	},
	
	
	refreshWindowStore : function() {
		var allmydata = {};
		allmydata.studyProposal = this.getSproposalData();
		
		allmydata.studyFunding = this.getFundingData();
		allmydata.studyConcepts = this.getConceptData();
		allmydata.studyQuestions = this.getQuestionsData();
		allmydata.dataCollection = this.getDataCollectionData();
		allmydata.dataProduction = this.getDataProductionData();
		console.log(allmydata);
		//ok, try save
		//sa vedem daca e nou sau nu
		
		console.log(this.getStudyaddmain());
		
		var mytitle = this.getSproposal().down('textareafield#studytitle').getValue();
		if (this.getStudyaddmain().getMode() == 'add') {
			console.log('add mode -------------------------------------');
			Ext.Ajax.request({
				url: '/roda/j/admin/cmsjsoncreate',
				waitTitle: 'Connecting',
				waitMsg: 'Sending data...',                                     
				params: {
					"name" : mytitle,
				},
				scope:this,
				jsonData: allmydata,
				success : function(response, opts) {
					console.log ('success');
					console.log (response.responseText);					
					var resp = Ext.JSON.decode(response.responseText);
					console.log (resp);
					if (resp.success) {
							console.log ('response success');
                        //	RODAdmin.util.Alert.msg('Success!', response.message);
                        	this.getStudyaddmain().setMode('edit');
                        	this.getStudyaddmain().setEditindex(resp.id);
                        	this.getTempstudygrid().store.load();
        					console.log (resp.id);					

					} else {
						console.log ('error');
						RODAdmin.util.Util.showErrorMsg(response.message);
					}
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
		} else if (this.getStudyaddmain().getMode() == 'edit') {
			console.log('edit mode -------------------------------------');
			var myid = this.getStudyaddmain().getEditindex();
			Ext.Ajax.request({
			    url: '/roda/j/admin/cmsjsonsave/',
			    waitTitle: 'Connecting',
			    waitMsg: 'Sending data...',  
			    method: 'POST',  
			    params: {
			        "name" : mytitle,
			        "id" : myid,
			    },
			    jsonData: allmydata,
			    scope:this,
			    success : function(response, opts) {
                	this.getTempstudygrid().store.load();
			    	console.log('success');

			    },                                
			    failure: function(){console.log('failure');}
			});
			
		}
		
	},

	getSproposalData : function() {
		var sproposaldata = {};
		if (this.getSproposal().down('datefield#startdate').getValue()){
			sproposaldata.startdate = this.getSproposal().down('datefield#startdate').getValue().toJSON();
		} else {
			sproposaldata.startdate = '';
		}

		if (this.getSproposal().down('datefield#enddate').getValue()) {
			sproposaldata.enddate = this.getSproposal().down('datefield#enddate').getValue().toJSON();
		} else {
			sproposaldata.enddate = '';
		}
		
		var ourstore = this.getSproposal().down('fieldset#prinvfs').down('grid#prinvdisplay').getStore();
		sproposaldata.principalinvestigator = Ext.Array.pluck(ourstore.data.items, 'data');
		sproposaldata.genlanguage = this.getSproposal().down('combo#genlanguage').getValue();
		
		sproposaldata.studytitle = this.getSproposal().down('textareafield#studytitle').getValue();
		sproposaldata.altitle = this.getSproposal().down('textareafield#altitle').getValue();

		sproposaldata.stabstract = this.getSproposal().down('textareafield#stabstract').getValue();
		sproposaldata.geocoverage = this.getSproposal().down('textareafield#geocoverage').getValue();
		sproposaldata.geounit = this.getSproposal().down('textareafield#geounit').getValue();
		sproposaldata.resinstrument = this.getSproposal().down('textareafield#resinstrument').getValue();
		// acu sa vedem storeul de la grid. Sa vedem daca putem sa-l convingem
		// sa se transforme in json fara scandal.
		return sproposaldata;
	},

	getFundingData : function() {
		var sfundingdata = {};

		if ( this.getSfunding().down('datefield#startfunding').getValue() ) {
			sfundingdata.sfunding = this.getSfunding().down('datefield#startfunding').getValue().toJSON();
		} else {
			sfundingdata.sfunding = '';
		}
		
		
		if ( this.getSfunding().down('datefield#endfunding').getValue()) {
			sfundingdata.endfunding = this.getSfunding().down('datefield#endfunding').getValue().toJSON();
		} else {
			sfundingdata.endfunding = '';
		}
		
		sfundingdata.grantnr = this.getSfunding().down('textfield#grantnr').getValue();
		var ourstore = this.getSfunding().down('fieldset#funding').down('grid#fundvdisplay').getStore();
		sfundingdata.fundingAgency = Ext.Array.pluck(ourstore.data.items,	'data');
		return sfundingdata;
	},
	
	getConceptData : function() {
		var sconceptdata = {};
		
		if (this.getSconcepts().down('datefield#stconcepts').getValue()) {
			sconceptdata.startqdesign = this.getSconcepts().down('datefield#stconcepts').getValue().toJSON();
		} else {
			sconceptdata.startqdesign = '';
		}
		
		if ( this.getSconcepts().down('datefield#endconcepts').getValue() ){
			sconceptdata.endqdesign = this.getSconcepts().down('datefield#endconcepts').getValue().toJSON();
		} else {
			sconceptdata.endqdesign = '';
		}
		
		
		var conceptdesignstore = this.getSconcepts().down('fieldset#cnresponsibility').down('grid#cnrespdisplay').getStore();
		sconceptdata.cdesignresp = Ext.Array.pluck(conceptdesignstore.data.items,'data');
		var conceptsstore = this.getSconcepts().down('fieldset#concepts').down('treepanel#conceptsdisplay').getStore();
		sconceptdata.concepts = this.recursiveConcepts(conceptsstore);
		return sconceptdata; 
	},	
	
	
	getQuestionsData : function() {
		//that will be interesting. Can we pluck recursive? I don't think so
		var squestiondata = {};

		 if( this.getSquestions().down('datefield#startqdesign').getValue() ) {
				squestiondata.startqdesign = this.getSquestions().down('datefield#startqdesign').getValue().toJSON();
		 } else {
		 	squestiondata.startqdesign = '';
		 }
		
		if (this.getSquestions().down('datefield#endqdesign').getValue()) {
			squestiondata.endqdesign = this.getSquestions().down('datefield#endqdesign').getValue().toJSON();
		} else {
			squestiondata.endqdesign = '';
		}
		
		var qdesignstore = this.getSquestions().down('fieldset#qdesign').down('grid#qdesigndisplay').getStore();
		squestiondata.qdesignresp = Ext.Array.pluck(qdesignstore.data.items,'data');
		var questionsstore = this.getSquestions().down('fieldset#questions').down('grid#questionsdisplay').getStore();
		squestiondata.questions = [];
		questionsstore.each(function(record) {
			var cquestion = record.data;
			if (record.data.respdomain == 'Code') {
				cquestion.coderesponses = Ext.Array.pluck(record.coderesponses().data.items,'data');
			} else if (record.data.respdomain == 'Category') {
				cquestion.catsponses = Ext.Array.pluck(record.catresponses().data.items,'data');
			} else if (record.data.respdomain == 'Numeric') {
				cquestion.numericresponse = Ext.Array.pluck(record.numericresponse().data.items,'data');
			}
			squestiondata.questions.push(cquestion);
		} );
		
// translation stuff

				if (this.getSquestions().down('datefield#qtranslationstart').getValue()) {
						squestiondata.startqtranslation = this.getSquestions().down('datefield#qtranslationstart').getValue().toJSON();
				} else {
						squestiondata.startqtranslation = '';
				}
		
				if ( this.getSquestions().down('datefield#qtranslationend').getValue() ) {
					squestiondata.endqtranslation = this.getSquestions().down('datefield#qtranslationend').getValue().toJSON();
				} else {
					squestiondata.endqtranslation = '';
				}
		
		
		var qtransrespstore = this.getSquestions().down('fieldset#qtransation').down('grid#qtranslationdisplay').getStore();		
		squestiondata.transresponsability = Ext.Array.pluck(qtransrespstore.data.items,'data');
		
		squestiondata.translations = [];
		var questiontrstore = this.getSquestions().down('fieldset#translatedqfs').down('grid#translationsdisplay').getStore();		
		questiontrstore.each(function(record) {
			var trquestion = record.data;
			if (record.data.respdomain == 'Code') {
				trquestion.coderesponses = Ext.Array.pluck(record.coderesponses().data.items,'data');
			} else if (record.data.respdomain == 'Category') {
				trquestion.catsponses = Ext.Array.pluck(record.catresponses().data.items,'data');
			} else if (record.data.respdomain == 'Numeric') {
				trquestion.numericresponse = Ext.Array.pluck(record.numericresponse().data.items,'data');
			}
			squestiondata.translations.push(trquestion);
		} );
		return squestiondata;	
	},


	getDataCollectionData : function() {
		var sdcoldata = {};
		if (this.getSdatacollection().down('datefield#startsampling').getValue()) {
				sdcoldata.startsampling = this.getSdatacollection().down('datefield#startsampling').getValue().toJSON();
		} else {
				sdcoldata.startsampling = '';
		}

		
		if ( this.getSdatacollection().down('datefield#endsampling').getValue() ) {
				sdcoldata.endsampling = this.getSdatacollection().down('datefield#endsampling').getValue().toJSON();
		} else {
				sdcoldata.endsampling = '';
		}

		var samplingrespstore = this.getSdatacollection().down('fieldset#dcfsamplingresp').down('grid#qsamplingrespdisplay').getStore();
		sdcoldata.samplingresp = Ext.Array.pluck(samplingrespstore.data.items,'data');
		
		sdcoldata.sampledescr = this.getSdatacollection().down('textareafield#sampldescription').getValue()

		if (this.getSdatacollection().down('datefield#startdatacollection').getValue()) {
				sdcoldata.startdatacollection = this.getSdatacollection().down('datefield#startdatacollection').getValue().toJSON();
		} else {
				sdcoldata.startdatacollection = '';
		}
		if ( this.getSdatacollection().down('datefield#enddatacollection').getValue() ) {
				sdcoldata.enddatacollection = this.getSdatacollection().down('datefield#enddatacollection').getValue().toJSON();
		} else {
				sdcoldata.enddatacollection = '';
		}
		
		var dcolrespstore = this.getSdatacollection().down('fieldset#dcfdcresp').down('grid#dcrespdisplay').getStore();
		sdcoldata.datacolresp = Ext.Array.pluck(dcolrespstore.data.items,'data');
		
		sdcoldata.dcolmode = this.getSdatacollection().down('combo#dcollmode').getValue();
		sdcoldata.dcolsituation = this.getSdatacollection().down('textareafield#collectionsituation').getValue();		
		
		return sdcoldata;

	},

	getDataProductionData : function() {
		var sdproddata = {};
		if (this.getSdataprod().down('datefield#stdataprod').getValue()) {
				sdproddata.startsampling = this.getSdataprod().down('datefield#stdataprod').getValue().toJSON();
		} else {
				sdproddata.startsampling = '';
		}

		
		if ( this.getSdataprod().down('datefield#enddataprod').getValue() ) {
				sdproddata.endsampling = this.getSdataprod().down('datefield#enddataprod').getValue().toJSON();
		} else {
				sdproddata.endsampling = '';
		}

		var dataprodrespstore = this.getSdataprod().down('fieldset#dpresponsibility').down('grid#dprespdisplay').getStore();
		sdproddata.dataprodresp = Ext.Array.pluck(dataprodrespstore.data.items,'data');

		var variablestore = this.getSdataprod().down('fieldset#dpvariables').down('grid#variabledisplay').getStore();
		sdproddata.variables = Ext.Array.pluck(variablestore.data.items,'data');


		var filestore = this.getSdataprod().down('fieldset#dpdatafiles').down('grid#filesdisplay').getStore();
		sdproddata.files = Ext.Array.pluck(filestore.data.items,'data');
		return sdproddata;
	},

	
	getStoreNextId : function(ourstore) {
		var maxId = 0;
		if (ourstore.getCount() > 0) {
			maxId = ourstore.getAt(0).get('id'); // initialise to the first
													// record's id value.
			ourstore.each(function(rec) // go through all the records
					{
						maxId = Math.max(maxId, rec.get('id'));
					});
		}
		return maxId + 1;
	},
	
	getTreeStoreNextId : function(ourtreestore) {
		var maxId = 0;
		var root = ourtreestore.getRootNode();
		root.cascadeBy(function(){
			if (this.data.id) {
				maxId = Math.max(maxId, this.data.id);
			}
        });
		return maxId + 1;
	},

	//for the moment, this reeches all the branches but does not return
	//nothing but the first level	
	
	getConceptChildren: function ( node ) {
			var children = [];
					var me = this;
					node.eachChild(function(child) {
						var c  = {};
						c.children = me.getConceptChildren(child);
						c.id = child.data.id;
						c.text = child.data.text;
						c.lang = child.data.lang;
						children.push(c);
					});
			return children;
	},
	
	recursiveConcepts : function (ourtreestore) {
//		console.log(ourtreestore);
		var concepts = {};
		var root = ourtreestore.getRootNode();
		var rjson = root.data;
		concepts.id = root.data.id;
		concepts.text = root.data.text;
		concepts.lang = root.data.lang;
		concepts.children =  this.getConceptChildren(root);
		return concepts;
	}
	
	
	
	
});