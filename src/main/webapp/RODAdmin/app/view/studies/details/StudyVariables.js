/**
 * 
 */
Ext.define('RODAdmin.view.studies.details.StudyVariables', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.studyvariables',
//    itemId: 'studyvariables',
    title:'Variables',
    autoScroll : true,
    layout : {
        type : 'border',
    },
    
    items : [
             {
                 xtype : 'grid',
                 region : 'center',
                 collapsible : true,
                 split : false,
                 itemId : 'studyvars',
                 width : '100%',
                 flex : 1,
                 autoScroll : true,
                 remoteSort : false,
                 title:'Variables',
                 collapsible: true,
                 columns: [
                           {
                        	   text: 'Id',
                        	   width: 100,
                        	   dataIndex: 'indice'
                           },
                           {
                        	   text: 'Name',
                        	   flex: 1,
                        	   dataIndex: 'name'
                           },
                           {
                        	   text: 'Label',
                        	   flex: 1,
                        	   dataIndex: 'label'
                           }
                           ]
             },
             {
                 xtype : 'tabpanel',
                 region : 'south',
                 collapsible : true,
                 title:'Variable details',
                 split : true,
                 flex : 1,
                 layout : 'fit',
                 items : [
                          {
                              xtype : 'panel',
                              itemId: 'vardetails',
                              title : 'variable details',
                              title:'Response type',
                              autoScroll : true,
                              layout : 'card',
                              activeItem: 0,
                              items : [
                                       {xtype: 'panel',
                                    	title: 'Whatever',
                                    	html: 'Select some variable'
                                       },
                                       {
                                    	   xtype: 'grid',
                                    	   title: 'Category',
                                    	   itemId: 'respcategory',
                                    	   columns: [
                                    	           {
                                    	        	 name: 'Label',
                                    	        	 text: 'Label',
                                    	        	 dataIndex: 'label'
                                    	           }  
                                    	           ]
                                       },{
                                    	   xtype: 'grid',
                                    	   title: 'Code',
                                    	   itemId: 'respcode',
                                    	   columns: [
                                    	           {
                                    	        	 name: 'Label',
                                    	        	 text: 'Label',
                                    	        	 dataIndex: 'label'
                                    	           },
                                    	           {
                                      	        	 name: 'Value',
                                      	        	 text: 'Value',
                                      	        	 dataIndex: 'value'
                                      	           },
                                    	           ]
                                    	   
                                       },{
                                    	   xtype: 'panel',
                                    	   itemId: 'respnumeric',
                                    	   title: 'Numeric',
                                    	   
                                       }
                                       
                                       ]
                          },
                          {
                              xtype : 'grid',
                              autoScroll : true,
                              itemId: 'missingval',
                              title:'Missing values',
                       	   	  columns: [
                      	           {
                      	        	 name: 'Label',
                      	        	text: 'Label',
                      	        	 dataIndex: 'label'
                      	           },
                      	           {
                        	        	 name: 'Value',
                        	        	 text: 'Value',
                        	        	 dataIndex: 'value'
                        	           },
                      	           ]
                          }]
             }
             ]
             
});
