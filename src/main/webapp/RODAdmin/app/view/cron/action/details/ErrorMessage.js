Ext.define('RODAdmin.view.cron.action.details.ErrorMessage', {
	             
	extend : 'RODAdmin.view.common.InformationWindow',
	alias : 'widget.cronerrormessage',

	height : '90%',
	width : '50%',

	requires : ['RODAdmin.util.Util'],

	layout : {
		type : 'border'
	},

    config: {
        cnode: {}
    },


     initComponent : function() {
		var me = this;
		Ext.applyIf(me, {

			items : [{
				region : 'center',
				collapsible : false,
				flex:3,
				split : true,
				layout : 'fit',
					items : [{
								xtype : 'panel',
								title : 'Error Message',
								html:'<div style="padding:20px; width:100%">java.lang.IllegalStateException:<br><br>Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)<br>at ro.roda.domain.Topic.solrServer_aroundBody28(Topic.java:150)<br>at ro.roda.domain.Topic.solrServer(Topic.java:1)<br>at ro.roda.domain.Topic.indexTopics_aroundBody20(Topic.java:125)<br>at ro.roda.domain.Topic$AjcClosure21.run(Topic.java:1)<br>at org.springframework.scheduling.aspectj.AbstractAsyncExecutionAspect.ajc$around$org_springframework_scheduling_aspectj_AbstractAsyncExecutionAspect$1$6c004c3eproceed(AbstractAsyncExecutionAspect.aj:58)<br>at org.springframework.scheduling.aspectj.AbstractAsyncExecutionAspect$AbstractAsyncExecutionAspect$1.call(AbstractAsyncExecutionAspect.aj:66)<br>at java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:334)<br>at java.util.concurrent.FutureTask.run(FutureTask.java:166)<br>at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)<br>at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)<br>at java.lang.Thread.run(Thread.java:724)<br></div>',								

							}
					]
// }]
// end regions
			}]
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});


 
//	extend : 'RODAdmin.view.common.WindowForm',
//
//	height : '90%',
//	width : '60%',
//
//	   initComponent : function() {
//			var me = this;
//			Ext.applyIf(me, {
//
//				items : [{	
//					xtype: 'panel',
//					html:'cucubauu man...'
//				}]
//			});
//	   }
//	
//});
