/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.studyadd.sConcepts', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.sconcepts',
   	autoScroll: true,
    title : 'Concepts',
    items : [
	    {
	        xtype : 'form',
   			bodyPadding : 5,
	        itemId : 'conceptsform',
	        items : [
						{
							xtype : 'fieldset',
							itemId: 'conceptsdate',
							title : 'Date',
							layout: 'hbox',
							items : [{
	               	        	 xtype : 'datefield',
								fieldLabel : 'Start concept definition',
								name : 'stdataprod',
								itemId : 'stconcepts',
								padding: 5,
								flex: 1,
								value : ''
							}, {
                	        	 xtype : 'datefield',
								fieldLabel : 'End concept definition',
								name : 'enddataprod',
								itemId : 'endconcepts',
								padding: 5,
								flex: 1,
								value : ''
							}]
	        			},
	        			{
							xtype : 'fieldset',
							itemId: 'cnresponsibility',
							title : 'Concepts responsibility',
							collapsible: true,
							items : [
								{
								xtype : 'grid',
								itemId : 'cnrespdisplay',
								store : 'studies.CBEditor.ConceptResp',
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
											flex: 2,											
											sortable : true,
											dataIndex : 'selectedname'
										},
												{
														xtype : 'actioncolumn',
														width : 30,
														sortable : false,
														menuDisabled : true,
														itemId : 'actionqcoderesp',
														items : [{
															icon : 'resources/images/icons/fam/delete.gif',
															tooltip : 'Delete Plant',
															scope : this,
															handler : function(view,	rowIndex,	colIndex,item, e,	record, row) {
																var mygrid = view.up('grid');
																mygrid.fireEvent('deleteRecord',mygrid,record,rowIndex,row);
															}
														}]
													}
										]
								},	{
								xtype : 'button',
								itemId : 'conceptresp',
								text : 'Add Concept Responsibility'
							}]
	        			},	{
							xtype : 'fieldset',
							itemId: 'concepts',
							title : 'Concepts',
							items : [
								{
								xtype : 'treepanel',
								itemId : 'conceptsdisplay',
								store : 'studies.CBEditor.ConceptsDisp',
								tbar : [{
									text : 'Add Concept',
									itemId : 'addconcept'
								}],
								plugins : [Ext.create(
											'Ext.grid.plugin.CellEditing', {
											clicksToEdit : 2
								})],
								selModel : {
											selType : 'cellmodel'
								},
								columns : [
									{
											itemId : 'text',
											text : 'Name',
											sortable : true,
											flex:2,
											dataIndex : 'text',
											xtype: 'treecolumn',
											editor: {
											 				xtype: 'textfield',
													 		allowBlank: false
                										}
										},
										{
											itemId : 'lang',
											text : 'Language',
											flex:1,
											sortable : true,
											dataIndex : 'lang',
											editor: {
											 				xtype: 'textfield',
													 		allowBlank: false
                										}
											}
                					  ]
								}]

							
							
						},
	        			
	        			
	        ]
	    }
    ]
});
