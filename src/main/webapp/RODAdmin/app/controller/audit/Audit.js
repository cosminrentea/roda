/**
 * 
 * Audit - controller care se ocupa de interfata de audit 
 */
Ext.define('RODAdmin.controller.audit.Audit', {
    extend : 'Ext.app.Controller',
    
    views : [
             'RODAdmin.view.audit.Audit',
             'RODAdmin.view.audit.AuditItemsview',
     ],
 
     refs : [
             {
            	 ref: 'audititemsview',
            	 selector: 'audititemsview'
             },
             {
                 ref : 'auditrevisions',
                 selector : 'audititemsview grid# auditrevisions'
             }, {
                 ref : "auditobjects",
                 selector : "audititemsview treepanel#auditobjects"
             }, {
                 ref : "auditusers",
                 selector : "audititemsview treepanel#auditusers"
             }, {
                 ref : "auditdates",
                 selector : "audititemsview treepanel#auditdates"
             }
     ],
/**
 * @method
 */
     init : function(application) {
 	    this.control({
 	        "auditmain toolbar button#bauditrevisions" : {
 		        click : this.onAuditRevisionsClick
 	        },
 	        "auditmain toolbar button#bauditobjects" : {
 		        click : this.onAuditObjectsClick
 	        },
 	       "auditmain toolbar button#bauditusers" : {
		        click : this.onAuditUsersClick
	        },
	        "auditmain toolbar button#bauditdates" : {
 		        click : this.onAuditDatesClick
 	        }
 	    });
     },
     /**
 	 * @event
 	 */
     onAuditRevisionsClick : function(button, e, options) {
 	    this.getAudititemsview().layout.setActiveItem('auditrevisions');
// 	    var store = Ext.StoreManager.get('cms.layout.Layout');
// 	    store.load();
     },
     /**
 	 * @event
 	 */
     onAuditObjectsClick : function(button, e, options) {
    	 this.getAudititemsview().layout.setActiveItem('auditobjects');
//  	    var store = Ext.StoreManager.get('cms.layout.Layout');
//  	    store.load();
      },
     /**
  	 * @event
  	 */
      onAuditUsersClick : function(button, e, options) {
    	  this.getAudititemsview().layout.setActiveItem('auditusers');
//   	    var store = Ext.StoreManager.get('cms.layout.Layout');
//   	    store.load();
       },
     /**
   	 * @event
   	 */
       onAuditDatesClick : function(button, e, options) {
    	   this.getAudititemsview().layout.setActiveItem('auditdates');
//    	    var store = Ext.StoreManager.get('cms.layout.Layout');
//    	    store.load();
        },


     
     
});