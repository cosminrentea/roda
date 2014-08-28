/**
 * 
 */
Ext.define('RODAdmin.view.studies.StudyDetails', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.studydetails',
		    title : 'select a study',
//			store: 'cms.layoutdetails',
			itemId: 'studydetails',
			//            layout: {
//                         type:'vbox',
//                         align:'center',
//                         align:'stretch'
//                         },            
//			id: 'studydetails',
			items : [
			{
				xtype: 'studyproperties',
			},
			{
				xtype: 'studyvariables',
			},
			{
				xtype: 'studykeywords',
			}
			]
	});