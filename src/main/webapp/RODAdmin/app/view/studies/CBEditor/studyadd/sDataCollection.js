/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.studyadd.sDataCollection', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.sdatacollection',
   	autoScroll: true,    
    title : 'Data Collection',
    items : [
	    {
	        xtype : 'form',
   			bodyPadding : 5,	        
	        itemId : 'datacollectionform',
	        items : [
	        			{
							xtype : 'fieldset',
							itemId: 'dcfsamplingdate',
							collapsible: true,
		                	layout : {
								type : 'hbox'
							},							
							title : 'Sampling date',
							items : [{
                	        	 xtype : 'datefield',
								fieldLabel : 'Start Sampling',
								name : 'ssampling',
								itemId : 'startsampling',
								value : '',
								flex: 1,
								padding: 5,
							}, {
                	        	 xtype : 'datefield',
								fieldLabel : 'End Sampling',
								name : 'endsampling',
								itemId : 'endsampling',
								value : '',
								flex: 1,
								padding: 5,
							}]
	        			},
	        			{
							xtype : 'fieldset',
							itemId: 'dcfsamplingresp',
							title : 'Sampling responsibility',
							collapsible: true,
							items : [
								{
								xtype : 'grid',
								itemId : 'qsamplingrespdisplay',
								store : 'studies.CBEditor.Qsamplingresp',
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
										},	{
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
								xtype : 'button',
								itemId : 'addsamplingresp',
								text : 'Add Sampling Responsibility'
							}]
	        			
	        			},
	        			{
							xtype : 'fieldset',
							itemId: 'dcfsamplingdescr',		
							collapsible: true,							
							title : 'Sampling description',
	                		layout : {
								type : 'anchor'
							},							
							items: [
										{
						 					xtype: 'textareafield',
	                	        	 		fieldLabel : 'Sampling description',
	                	        	 		name : 'sampldescription',
	                	        	 		itemId : 'sampldescription',
											anchor:'98%'
	                	        	 		}
										]
							},
		        			{
							xtype : 'fieldset',
							itemId: 'dcfdatacolldate',		
							collapsible: true,							
							title : 'Data Collection date',
						   	layout : {
								type : 'hbox'
							},	
							items : [{
	                	        	 xtype : 'datefield',
									fieldLabel : 'Start Data collection',
									name : 'sdcollection',
									itemId : 'startdatacollection',
									value : '',
									flex: 1,
									padding: 5
									}, {
	                	        	 xtype : 'datefield',
									fieldLabel : 'End Data collection',
									name : 'enddatacollection',
									itemId : 'enddatacollection',
									value : '',
									flex: 1,
									padding: 5
									}]
							},
	    	    			{
							xtype : 'fieldset',
							itemId: 'dcfdcresp',
							collapsible: true,							
							title : 'Data Collection responsibility',
							items : [
								{
								xtype : 'grid',
								itemId : 'dcrespdisplay',
								store : 'studies.CBEditor.DataCollectionResp',
								columns : [{
											itemId : 'status',
											text : 'Status',
											flex: 1,
											sortable : true,
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
										},	{
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
								xtype : 'button',
								itemId : 'adddcresp',
								text : 'Add Data Collection Responsibility'
							}]
	        				},
	        			{
							xtype : 'fieldset',
							itemId: 'dcfcollmode',
							collapsible: true,	
	                		layout : {
								type : 'hbox'
							},		
							title : 'Mode of collection and collection situation',
							items : [{
														xtype : 'combo',
														labelWidth : 60,
														name : 'collmode',
														itemId : 'dcollmode',
														fieldLabel : 'Mode of collection',
														// anchor : '100%',
														displayField : 'name',
														// typeAhead: true,
														valueField : 'id',
														flex: 1,
														// tpl: '<tpl
														// for="."><div
														// class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
														// store :
														// 'cms.layout.Layout'
														store : 'studies.CBEditor.CollectionMode'
							},
							{
						 			  		xtype : 'textareafield',
	                	        	 		fieldLabel : 'Collection Situation',
	                	        	 		name : 'collectionsituation',
	                	        	 		flex: 2,
	                	        	 		padding: 5,
	                	        	 		itemId : 'collectionsituation'
										}
							
							
							
							
							
							
							
							
							]
							
							
							
	        			}]
	    }
    ]
});

