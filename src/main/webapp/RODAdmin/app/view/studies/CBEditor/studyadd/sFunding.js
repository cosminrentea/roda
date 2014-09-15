/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.studyadd.sFunding', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.sfunding',
   	autoScroll: true,
    title : 'Study Funding',
    items : [
	    {
	        xtype : 'form',
	        itemId : 'sfundingform',
   			bodyPadding : 5,
	        items : [
	                 {
	                	 xtype: 'fieldset',
	                	 title: 'Date Information',
  	 					layout : {
							type : 'hbox'
						},
	                	 items: [
	                	         {
	                	        	 xtype : 'datefield',
										  fieldLabel : 'Start Founding',
		                	        	 name : 'sfunding',
	                	        		 itemId : 'startfunding',
	                	        		 flex: 1,
	                	        		 padding: 5,
	                	        	 	value : ''
	                	         }, 
	                	         {
	                	        	 xtype : 'datefield',
	                	        	 fieldLabel : 'End Funding',
	                	        	 name : 'endfunding',
	                	        	 itemId : 'endfunding',
	               	        		 flex: 1,
	               	        		 padding: 5,
	                	        	 value : ''
	                	         }
	                	         ]
	                 }, {
	                	xtype: 'fieldset',
	                	title: 'Funding agency',
	                	itemId: 'funding',
	                	items: [
	                	    {
	                		xtype: 'grid',
	                		itemId: 'fundvdisplay',
	                		store: 'studies.CBEditor.Funder' ,
	                        columns : [{
                                             itemId : 'status',
                                             text : 'Status',
                                             flex: 1,
                                             sortable : true,
                                             dataIndex : 'status'
                                           },{
                                             itemId : 'type',
                                             text : 'Type',
                                             flex: 1,                                             
                                             sortable : true,
                                             dataIndex : 'type'
                                           },{
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
	                			itemId: 'addfund',
	                			text: 'Add Funding Agency'
	                		}
	                		]
	                	
	                	} ,{
	                 		xtype: 'fieldset',	 
	                 		items:[  
	                 		       
	                 		       {
	                 		    	   xtype : 'textareafield',
									   anchor : '100%',	                 		    	   
	                 		    	   fieldLabel : 'Grant Number',
	                 		    	   itemId : 'grantnr',
	                 		    	   name : 'grantnr'
	                 		       }
	                 			]
	                 			}

	                 			]
	    }
    ]
});
