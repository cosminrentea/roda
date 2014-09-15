/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.studyadd.sQuestions', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.squestions',
   	autoScroll: true,
	onRemoveClick : function(grid, rowIndex) {
		this.getStore().removeAt(rowIndex);
	},
	title : 'Study Question',
	items : [{
		xtype : 'form',
		bodyPadding : 5,		
		itemId : 'questionsform',
		items : [{
					xtype : 'fieldset',
					flex : 1,
					layout : {
						type : 'hbox'
					},
					title : 'Date Information',
					items : [{
				 	        	 xtype : 'datefield',
								fieldLabel : 'Start questionaire design',
								name : 'sfounding',
								itemId : 'startqdesign',
								value : '',
								padding : '5',
								flex : 1
							}, {
                	        	 xtype : 'datefield',
								fieldLabel : 'End questionaire design',
								name : 'endfounding',
								itemId : 'endqdesign',
								value : '',
								flex : 1,
								padding : '5'
							}]
				}, {
					xtype : 'fieldset',
					flex : 2,
					title : 'Questionaire design',
					itemId : 'qdesign',
					items : [{
								xtype : 'grid',
								itemId : 'qdesigndisplay',
								store : 'studies.CBEditor.Qdesign',
								columns : [{
											itemId : 'status',
											text : 'Status',
											sortable : true,
											flex: 1,
											dataIndex : 'status'
										}, {
											itemId : 'type',
											text : 'Type',
											flex: 1,
											sortable : true,
											dataIndex : 'type'
										}, {
											itemId : 'name',
											text : 'Name',
											sortable : true,
											flex: 2,											
											dataIndex : 'selectedname'
										},
										{
														xtype : 'actioncolumn',
														width : 30,
														sortable : false,
														menuDisabled : true,
														itemId : 'actionqcoderesp',
														items : [{
															icon : 'images/delete.gif',
															tooltip : 'Delete Plant',
															scope : this,
															handler : function(view,	rowIndex,	colIndex,item, e,	record, row) {
																var mygrid = view.up('grid');
																mygrid.fireEvent('deleteRecord',mygrid,record,rowIndex,row);
															}
														}]
													}
										]
							}, {
								xtype : 'button',
								itemId : 'addqdesign',
								text : 'Add Design responsibility'
							}]

				}, {
					xtype : 'fieldset',
					title : 'Questions',
					flex : 3,
					itemId : 'questions',
					items : [{
						xtype : 'grid',
						itemId : 'questionsdisplay',
						store : 'studies.CBEditor.Questions',
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit : 2
								})],
						selModel : {
							selType : 'cellmodel'
						},
						tbar : [{
									text : 'Add Question',
									itemId : 'addquestionwin'
								}],
						columns : [{
									itemId : 'id',
									text : 'ID',
									sortable : true,
									dataIndex : 'id'
								}, {
									itemId : 'text',
									text : 'Question text',
									sortable : true,
									flex : 3,
									dataIndex : 'text',
									editor : {
										xtype : 'textfield',
										allowBlank : false
									}
								}, 
									{
										itemId : 'concept',
										text : 'Concept',
										sortable : true,
										dataIndex : 'conceptname'
									},
								{
									itemId : 'lang',
									text : 'Language',
									sortable : true,
									dataIndex : 'lang'
								}, {
									itemId : 'respdomain',
									text : 'Response domain',
									sortable : true,
									dataIndex : 'respdomain'
								}, {
									xtype : 'actioncolumn',
									width : 60,
									sortable : false,
									menuDisabled : true,
									itemId : 'actionquestionsdisplay',
									items : [{
										icon : 'images/cog_edit.png',
										tooltip : 'Edit Question',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event edit');
											var mygrid = view.up('grid');
											mygrid.fireEvent('editRecord',
													mygrid, record, rowIndex,
													row);
										}
									}, {
										icon : 'images/delete.gif',
										tooltip : 'Delete Question',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event delete');
											var mygrid = view.up('grid');
											mygrid.fireEvent('deleteRecord',
													mygrid, record, rowIndex,
													row);
										}
									}]
								}]
					}]

				}, // end questions fieldset
				{
					xtype : 'fieldset',
					title : 'Questionaire translation date',
					flex : 1,
					layout : {
						type : 'hbox'
						,
					},
					items : [{
	                	        	 xtype : 'datefield',
								fieldLabel : 'Start Questionaire translation',
								name : 'qtranslationstart',
								itemId : 'qtranslationstart',
								value : '',
								flex : 1,
								padding : '5'
							}, {
	                	        	 xtype : 'datefield',
								fieldLabel : 'End Questionaire Translation',
								name : 'qtranslationend',
								itemId : 'qtranslationend',
								value : '',
								flex : 1,
								padding : '5'
							}]
				}, {
					xtype : 'fieldset',
					title : 'Questionaire translation responsibility',
					itemId : 'qtransation',
					flex : 1,
					items : [{
								xtype : 'grid',
								itemId : 'qtranslationdisplay',
								store : 'studies.CBEditor.Qtranslation',
								columns : [{
											itemId : 'status',
											text : 'Status',
											sortable : true,
											flex: 1,
											dataIndex : 'status'
										}, {
											itemId : 'type',
											text : 'Type',
											sortable : true,
											flex: 1,
											dataIndex : 'type'
										}, {
											itemId : 'name',
											text : 'Name',
											sortable : true,
											flex: 2,											
											dataIndex : 'selectedname'
										},
												{
														xtype : 'actioncolumn',
														width : 30,
														sortable : false,
														menuDisabled : true,
														itemId : 'actionqcoderesp',
														items : [{
															icon : 'images/delete.gif',
															tooltip : 'Delete Plant',
															scope : this,
															handler : function(view,	rowIndex,	colIndex,item, e,	record, row) {
																var mygrid = view.up('grid');
																mygrid.fireEvent('deleteRecord',mygrid,record,rowIndex,row);
															}
														}]
													}
										]
							}, {
								xtype : 'button',
								itemId : 'addqtranslationresp',
								text : 'Add Translation responsibility'
							}]

				}, {
					xtype : 'fieldset',
					title : 'Translated questions',
					itemId : 'translatedqfs',
					collapsible : true,
					collapsed : true,
					flex : 1,
					items : [{
						xtype : 'grid',
						itemId : 'translationsdisplay',
						store : 'studies.CBEditor.TransQuestions',
						selModel : {
							selType : 'cellmodel'
						},
						tbar : [{
									text : 'Add Question Translation',
									itemId : 'addqtranslation'
								}],
						columns : [{
									itemId : 'id',
									text : 'Question ID',
									sortable : true,
									dataIndex : 'id',
									hidden: true
								}, {
									itemId : 'text',
									text : 'Question original text',
									sortable : true,
									flex : 3,
									dataIndex : 'oqtext'
								}, 
								 {
									itemId : 'translatedtext',
									text : 'Translated text',
									sortable : true,
									dataIndex : 'text'
								}, 									
								{
									itemId : 'respdomain',
									text : 'Response domain',
									sortable : true,
									dataIndex : 'respdomain'
								},									
								{
									itemId : 'lang',
									text : 'Language',
									sortable : true,
									dataIndex : 'oqlang'
								},  
								 {
									itemId : 'translatedlang',
									text : 'Translated language',
									sortable : true,
									dataIndex : 'lang'
								}, 									
								{
									xtype : 'actioncolumn',
									width : 60,
									sortable : false,
									menuDisabled : true,
									itemId : 'actionquestionsdisplay',
									items : [{
										icon : 'images/cog_edit.png',
										tooltip : 'Edit Question',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event edit');
											var mygrid = view.up('grid');
											mygrid.fireEvent('editRecord',
													mygrid, record, rowIndex,
													row);
										}
									}, {
										icon : 'images/delete.gif',
										tooltip : 'Delete Question',
										scope : this,
										handler : function(view, rowIndex,
												colIndex, item, e, record, row) {
											console.log('firing event delete');
											var mygrid = view.up('grid');
											mygrid.fireEvent('deleteRecord',
													mygrid, record, rowIndex,
													row);
										}
									}]
								}]
					}]


				}]
	}]
});
