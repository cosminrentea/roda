/**
 * 
 * Audit - controller care se ocupa de interfata de audit 
 */
Ext.define('RODAdmin.controller.audit.AuditMain', {
    extend : 'Ext.app.Controller',
    
    stores : [
              'audit.Revisions',
              'audit.RevisionInfo',
              'audit.RevisedObjects',
              'audit.RevisedUsers',
              'audit.RevisedDates',
              'audit.RevisionbyObject',
      ], 
    
    
    views : [
             'RODAdmin.view.audit.Audit',
             'RODAdmin.view.audit.AuditItemsview',
             'RODAdmin.view.audit.details.RevisionProperties',
             'RODAdmin.view.audit.AuditDetails',
             'RODAdmin.view.audit.details.ObjectProperties'
             
     ],
 
     refs : [
             {
            	 ref: 'audititemsview',
            	 selector: 'audititemsview'
             },
             {
            	 ref: 'auditdetails',
            	 selector: 'auditdetails'
             },
             {
            	 ref: 'revisionproperties',
            	 selector: 'revisionproperties'
             },
             {
            	 ref: 'objectproperties',
            	 selector: 'objectproperties'
             },
             {
            	 ref: 'revisiondata',
            	 selector: 'revisionproperties grid#revisiondata'
             },
             {
            	 ref: 'objrevisiondata',
            	 selector: 'objectproperties grid#revisiondata'
             },
             {
            	 ref: 'revisionrows',
            	 selector: 'revisionproperties grid#revisionrows'
             },
             {
            	 ref: 'objrevisionrows',
            	 selector: 'objectproperties grid#revisionrows'
             },
             {
            	 ref: 'objectrows',
            	 selector: 'objectproperties grid#objectrows'
             },
             
             {
            	 ref: 'revisionfields',
            	 selector: 'revisionproperties panel#revisionfields'
             },             
             {
                 ref : 'auditrevisions',
                 selector : 'audititemsview grid#auditrevisions'
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
 	        },
 	       'revisionproperties grid#revisiondata' : {
 	    	  selectionchange : this.onObjectViewSelectionChange, 
 	       },
 	      'revisionproperties grid#revisionrows' : {
 	    	  selectionchange : this.onRowViewSelectionChange,
 	      },
 	       "audititemsview grid#auditrevisions" : {
			   /**
			    * @listener layoutitemsview-treepanel-folderview-selectionchange
			    *           triggered-by:
			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
			    *           treepanel#lyfolderview executes
			    *           {@link #onFolderviewSelectionChange}
			    */
			   selectionchange : this.onGridViewSelectionChange,
			   /**
			    * @listener layoutitemsview-treepanel-folderview-itemcontextmenu
			    *           triggered-by:
			    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
			    *           treepanel#lyfolderview executes
			    *           {@link #onTreeContextMenu}
			    */
			  // itemcontextmenu : this.onTreeContextMenu
		   },
		   
		     "audititemsview grid#auditobjects" : {
				   /**
				    * @listener layoutitemsview-treepanel-folderview-selectionchange
				    *           triggered-by:
				    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
				    *           treepanel#lyfolderview executes
				    *           {@link #onFolderviewSelectionChange}
				    */
				   selectionchange : this.onMainObjectViewSelectionChange,
		     },
		     "objectproperties grid#revisiondata" : {
		    	  selectionchange : this.onObjectRevisionSelectionChange,
		     }
 	        
 	        
 	    });
 	   	this.listen({
 	          controller: {
 	              '*': {
 	                   controllerAuditmainInitView: this.initView
 	              }
 	          }
 	  	 });
     },
     
     /**
 	 * @method
 	 */
     initView : function() {
     	console.log('InitView, baby');	
    	this.getAuditrevisions().store.load();
     },

     onObjectViewSelectionChange: function(component, selected, event) {
    	 console.log('selection o change');
    	 var revdata = this.getRevisiondata().store;
    	 var revrows = this.getRevisionrows();
//    	 console.log(revdata.rowsStore);
    	 var record = selected[0];
    	 if (record != null) {
    		 revrows.bindStore(record.rows());
    	 } else {
    		 
    	 }
     },

     onRowViewSelectionChange: function(component, selected, event) {
    	 console.log('selection row change');
    	 var revfields = this.getRevisionfields();
//    	 var revrows = this.getRevisionrows();
    	 
    	 var record = selected[0];
    	 if (record != null) {
    		 console.log(record);
    		 console.log(record.auditfields());
    		 revfields.update(record.auditfields());
    	 } else {
//    		 
    	 }
     },

     onObjectRevisionSelectionChange: function(component, selected, event) {
    	 console.log('object revision selection o change');
    	 var revdata = this.getObjrevisiondata().store;
    	 var revrows = this.getObjrevisionrows();
//    	 console.log(revdata.rowsStore);
    	 var record = selected[0];
    	 if (record != null) {
    		 revrows.bindStore(record.rows());
    	 } else {
    		 
    	 }
     },
    
     
     
     onMainObjectViewSelectionChange: function(component, selected, event) {
      	console.log('selection g change');
 	    var objprop = this.getObjectproperties();
 	    var revdata = this.getObjrevisiondata();
 	    var objrows = this.getObjectrows();
 	    var record = selected[0];
 	    console.log(objprop);
 	    if (record != null) {

 	    	objprop.setTitle(record.data.object);
 		    var objstore = Ext.StoreManager.get('audit.RevisionbyObject');
 		    objstore.load({
 		        id : record.data.object,
 		        scope : this,
 		        callback : function(records, operation, success) {
 			        if (success) {
 				        var revitem = objstore.first();
 				        console.log(revitem);
 				        console.log(revitem.revisionsStore);
// 				        
 				        var ms = revitem.revisions().getAt(0);
 				        console.log(revdata);
 				        revdata.bindStore(revitem.revisionsStore);
// 				        revdata.getSelectionModel().select(0);
// 				        
// 				        
 			        }
 		        }
 		    });
 	    } else {
// 		    pgdetails.setTitle('');
// 		    pageprop.update('');
// 	        pgcode.setValue('');
// 	    	revdata.unbindStore();
 	    }     	
      	
      	
      },
     
     
     
     onGridViewSelectionChange: function(component, selected, event) {
     	console.log('selection g change');
	    var revprop = this.getRevisionproperties();
	    var revdata = this.getRevisiondata();
	    var revrows = this.getRevisionrows();
	    var record = selected[0];
	    console.log(revprop);
	    if (record != null) {
		    revprop.setTitle(record.data.title);
		    var revstore = Ext.StoreManager.get('audit.RevisionInfo');
		    revstore.load({
		        id : record.data.revision,
		        scope : this,
		        callback : function(records, operation, success) {
			        if (success) {
				        var revitem = revstore.first();
				        console.log(revitem);
				        console.log(revitem.mobjStore);
				        console.log(revdata);
				        var ms = revitem.mobj().getAt(0);
				        console.log(ms);
				        revdata.bindStore(revitem.mobjStore);
				        revdata.getSelectionModel().select(0);
				        
				        
			        }
		        }
		    });
	    } else {
//		    pgdetails.setTitle('');
//		    pageprop.update('');
//	        pgcode.setValue('');
	    	revdata.unbindStore();
	    }     	
     	
     	
     },
     
     /**
 	 * @event
 	 */
     onAuditRevisionsClick : function(button, e, options) {
 	    this.getAudititemsview().layout.setActiveItem('auditrevisions');
// 	    var store = Ext.StoreManager.get('cms.layout.Layout');
// 	    store.load();
  	    this.getAuditdetails().layout.setActiveItem('revisionproperties');
     },
     /**
 	 * @event
 	 */
     onAuditObjectsClick : function(button, e, options) {
    	 this.getAudititemsview().layout.setActiveItem('auditobjects');
  	    var store = Ext.StoreManager.get('audit.RevisedObjects');
  	    store.load();
  	    this.getAuditdetails().layout.setActiveItem('objectproperties');
      },
     /**
  	 * @event
  	 */
      onAuditUsersClick : function(button, e, options) {
    	  this.getAudititemsview().layout.setActiveItem('auditusers');
   	    var store = Ext.StoreManager.get('audit.RevisedUsers');
   	    store.load();
       },
     /**
   	 * @event
   	 */
       onAuditDatesClick : function(button, e, options) {
    	   this.getAudititemsview().layout.setActiveItem('auditdays');
    	    var store = Ext.StoreManager.get('audit.RevisedDates');
    	    store.load();
        },


     
     
});