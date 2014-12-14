Ext.define('RODAdmin.view.studies.CBEditor.studyadd.sProcessinfo', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.sprocessinfo',
    autoScroll : true,
    title : 'Processing options',
    items : [
	    {
	        xtype : 'form',
	        bodyPadding : 5,
	        itemId : 'processinfoform',
	        items : [
		        {
		            xtype : 'fieldset',
		            itemId : 'processinfo',
		            title : 'Process information',
                	layout : {
						type : 'anchor'
					},
		            items : [
		                     {
		                    	 xtype: 'checkbox',
		                    	 name: 'correct',
		                    	 labelWidth : 300,
		                    	 anchor : '100%',
		                    	 fieldLabel : 'RODA can correct minor errors (such as person names, spell errors, etc.)',	
		                    	 itemId: 'ccorrect'
		                     },		                     
		                     {
		                    	 xtype: 'checkbox',
		                    	 name: 'qextract',
		                    	 labelWidth : 300,
		                    	 anchor : '100%',
		                    	 fieldLabel : 'RODA can extract questions and variables from your questionaire',	
		                    	 itemId: 'qextract'
		                     },		                     
		                     {
		                    	 xtype: 'checkbox',
		                    	 name: 'dataextract',
		                    	 labelWidth : 300,
		                    	 anchor : '100%',
		                    	 fieldLabel : 'RODA can extract data from attached files',	
		                    	 id: 'dataextract'
		                     },		                     
		                     
		            ]
		        }, 
		        {
		            xtype : 'fieldset',
		            itemId : 'rights',
		            title : 'Data rights',
                	layout : {
						type : 'anchor'
					},
		            items : [
		                    	 {
										xtype : 'combo',
										labelWidth : 80,
										name : 'datarights',
										itemId : 'datarights',
										fieldLabel : 'Data rights',
										anchor : '100%',
										displayField : 'level',
										// typeAhead: true,
										valueField : 'id',
//										tpl : '<tpl for=".">{level}<br>{description}<hr></tpl>',
 			                            tpl: '<tpl for="."><div class="x-boundlist-item"><strong>{level}</strong><br><i>{description}</i><hr></div></tpl>',
										store : 'studies.CBEditor.SRights'
									}
		            ]
		        }, 
		        
		        
		        
	        ]
	    }
    ]
});