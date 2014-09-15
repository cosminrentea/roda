/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.studyadd.sProposal', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.sproposal',
   	autoScroll: true,
    title : 'Study Proposal',
    items : [
	    {
	        xtype : 'form',
	        itemId : 'sproposalform',
			bodyPadding : 5,
	        items : [
	                 {
	                	 xtype: 'fieldset',
	                	collapsible: true, 
	                	layout : {
							type : 'hbox'
						},
	                	 title: 'Date Information',
	                	 items: [
	                	         {
	                	        	 xtype : 'datefield',
						  			fieldLabel : 'Start Date',
	                	        	 name : 'sdate',
	                	        	 itemId : 'startdate',
	                	        	 value : '',
     	 								flex : 1,
									padding : '5'	                	        	 
	                	         }, 
	                	         {
 	                	        	 xtype : 'datefield',
	                	        	 fieldLabel : 'End Date',
	                	        	 name : 'edate',
	                	        	 itemId : 'enddate',
	                	        	 value : '',
     	 								flex : 1,
	                	        	 padding : '5'
	                	         }
	                	         ]
	                 },
	                 {
	                	xtype: 'fieldset',
	                	title: 'Principal Investigators',
	                	collapsible: true,
	                	itemId: 'prinvfs',
	                	items: [
	                	    {
	                		xtype: 'grid',
	                		itemId: 'prinvdisplay',
	                		store: 'studies.CBEditor.PrincipalInv',
	                        columns : [{
                                             itemId : 'status',
                                             text : 'Status',
                                             sortable : true,
                                             flex: 1,                                             
                                             dataIndex : 'status'
                                           },{
                                             itemId : 'type',
                                             text : 'Type',
                                             sortable : true,
                                             flex: 1,
                                             dataIndex : 'type'
                                           },{
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
														itemId : 'actionpinvresp',
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
	                		},
	                		{
	                			xtype:'button',
	                			itemId: 'addpinv',
	                			text: 'Add Principal Investigator'
	                		}
	                		]
	                	
	                	},{
	                 		xtype: 'fieldset',	 
	                 		itemId: 'genprops',
		                	collapsible: true,
	                	layout : {
							type : 'anchor'
						},
	                 		items:[  
								{
								xtype : 'combo',
								fieldLabel : 'Original language',
								itemId : 'genlanguage',
								name : 'lang',
								valueField : 'id',
								store : 'studies.CBEditor.Lang',
								displayField : 'name',
								autoSelect : true,
								forceSelection : true,
								anchor : '100%'
								},	                 		       
	                 		       {
	                 		    	   xtype : 'textareafield',
	                 		    	   fieldLabel : 'Study Title',
	                 		    	   itemId : 'studytitle',
	                 		    	   name : 'studytitle',
	                 		    	   allowBlank : false,
	                 		    	   anchor:'98%',
	                 		    	   value : ''
	                 		       },
	                 		       {
	                 		    	   xtype : 'textareafield',
	                 		    	   fieldLabel : 'Alternative title',
	                 		    	   itemId : 'altitle',
	                 		    	   allowBlank : false,
	                 		    	   anchor:'98%',
	                 		    	   name : 'altitle',
	                 		    	   value : ''
	                 		       }, 
	                 		       {
	                 		    	   xtype : 'textareafield',
	                 		    	   fieldLabel : 'Study abstract',
	                 		    	   itemId : 'stabstract',
	                 		    	   name : 'stabstract',
	                 		    	   height: 50,
	                 		    	   anchor:'98%',	                 		    	   
	                 		    	   value : ''
	                 		       },
	                 		       {
	                 		    	   xtype : 'textareafield',
	                 		    	   fieldLabel : 'Geographic coverage',
	                 		    	   itemId : 'geocoverage',
	                 		    	   name : 'geocoverage',
	                 		    	   anchor:'98%',	                 		    	   
	                 		    	   height: 50,
	                 		    	   value : ''
	                 		       }, 
	                 		       {
	                 		    	   xtype : 'textareafield',
	                 		    	   fieldLabel : 'Geographic unit',
	                 		    	   itemId : 'geounit',
	                 		    	   name : 'geounit',
	                 		    	   height: 50,
	                 		    	   anchor:'98%',	                 		    	   
	                 		    	   value : ''
	                 		       }, 
	                 		       {
	                 		    	   xtype : 'textareafield',
	                 		    	   fieldLabel : 'Research instrument',
	                 		    	   itemId : 'resinstrument',
	                 		    	   name : 'resinstrument',
	                 		    	   height: 50,
	                 		    	   anchor:'98%',	                 		    	   
	                 		    	   value : ''
	                 		       }
	                 		       
	                 		       ]
	                 	}]
	    } ]
});
