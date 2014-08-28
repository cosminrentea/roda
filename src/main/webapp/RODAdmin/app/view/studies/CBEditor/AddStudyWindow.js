Ext.define('RODAdmin.view.studies.CBEditor.AddStudyWindow', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.studyadd',
	height : '90%',
	width : '60%',
	closeAction: 'destroy',
	itemId:'studyadd',
	requires : ['RODAdmin.util.Util'],
	layout : {
		type : 'border'
	},
    config: {
        cnode: {},
    },
    initComponent : function() {
		var me = this;
		Ext.applyIf(me, {

			items : [{
				region : 'center',
				collapsible : false,
//				overflowY: 'scroll',		
//	        	autoscroll: true,
//				width : '50%',
				flex:3,
				xtype:'panel',
				itemId : 'studyaddmain',
				split : true,
				activeItem: 0,
				layout: {
					type: 'border',
					header: false,
//					aligh: 'stretch'
				},
				items: [
				        {
				        	xtype: 'panel',
				        	region: 'north',
				        	height: '100px',
				        	width: '100%',
				        	items: [
							        {
							        	xtype:'button',
							        	text: 'Previous step',
							        	itemId: 'sprev',
							        },
							        {
							        	xtype:'button',
							        	text: 'Next step',
							        	itemId: 'snext',
							        }
							        ]
				        },
				        {	
				        	xtype: 'panel',
				        	region:'center',
				        	width: '100%',
				        	layout : {
				        		type : 'card',
				        		cardSwitchAnimation: 'flip',
				        		deferredRender : true,
				        		align : 'stretch'
				        	},
				        	itemId : 'studyaddform',
				        	items : [
				        	         {xtype:'sproposal', itemId:'sproposal'},
				        	         {xtype:'sfunding', itemId:'sfunding'},
				        	         {xtype:'sconcepts', itemId:'sconcepts'},
				        	         {xtype:'squestions', itemId:'squestions'},
				        	         {xtype:'sdatacollection', itemId:'sdatacollection'},
				        	         {xtype:'sdataprod', itemId:'sdataprod'},				         
				        	         ]
				}]
			}, {
				region : 'west',
				xtype: 'panel',
				collapsible : true,
				itemId:'buttongroup',
//				width : '50%',
				flex:1,
				split : true,
				layout : 'anchor',
				items: [
				        {
				        	xtype:'button',
				        	text: 'Study proposal',
				        	itemId: 'sproposalbutton',
							enableToggle : false,				        	
				        	anchor:'100%'
				        },{
				        	xtype:'button',
				        	text: 'Study funding',
				        	itemId: 'sfundingbutton',
							enableToggle : false,
				        	//				        	pressed:true,
				        	anchor:'100%'				        	
				        },
						{
				        	xtype:'button',
				        	text: 'Concepts',
				        	itemId: 'sconceptsbutton',
							enableToggle : false,
				        	anchor:'100%'				        	
				        },				        	
	        			{
				        	xtype:'button', 
				        	text: 'Questions',
				        	anchor:'100%',
							enableToggle : false,				        	
				        	itemId: 'squestionsbutton'
				        },{
				        	xtype:'button', 
				        	text: 'Data Collection',
				        	anchor:'100%',
							enableToggle : false,				        	
				        	itemId: 'sdatacollectionbutton'				        		
				        },{
				        	xtype:'button',
				        	text: 'Data production',
				        	anchor:'100%',
							enableToggle : false,				        	
				        	itemId: 'sdataprodbutton'
				        }]				
				}]
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});