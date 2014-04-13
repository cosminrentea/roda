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
              'audit.RevisionbyUser',
      ], 
    
    
    views : [
             'RODAdmin.view.audit.Audit',
             'RODAdmin.view.audit.AuditItemsview',
             'RODAdmin.view.audit.details.RevisionProperties',
             'RODAdmin.view.audit.AuditDetails',
             'RODAdmin.view.audit.details.ObjectProperties',
             'RODAdmin.view.audit.details.UserProperties'
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
            	 ref: 'userproperties',
            	 selector: 'userproperties'
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
            	 ref: 'userrevisiondata',
            	 selector: 'userproperties grid#revisiondata'
             },
             {
            	 ref: 'revisionrows',
            	 selector: 'revisionproperties grid#revisionrows'
             },
             {
            	 ref: 'userobjrows',
            	 selector: 'userproperties grid#revisionrows'
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
            	 ref: 'objfields',
            	 selector: 'objectproperties panel#revisionfields'
             },    
             {
            	 ref: 'userobjfields',
            	 selector: 'userproperties panel#revisionfields'
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
             },
             {
            	 ref: "userrevisionobj",
            	 selector : "userproperties grid#userobjects"
             },
             {
            	 ref: "bauditrevisions",
            	 selector: "auditmain toolbar button#bauditrevisions"
             },
             {
            	 ref: "bauditusers",
            	 selector: "auditmain toolbar button#bauditusers"
             },
             {
            	 ref: "bauditdates",
            	 selector: "auditmain toolbar button#bauditdates"
             },
             {
            	 ref: "bauditobjects",
            	 selector: "auditmain toolbar button#bauditobjects"
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
		     "audititemsview grid#auditusers" : {
				   /**
				    * @listener layoutitemsview-treepanel-folderview-selectionchange
				    *           triggered-by:
				    *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
				    *           treepanel#lyfolderview executes
				    *           {@link #onFolderviewSelectionChange}
				    */
				   selectionchange : this.onMainUserViewSelectionChange,
		     },
		     "objectproperties grid#revisiondata" : {
		    	  selectionchange : this.onObjectRevisionSelectionChange,
		     },
		     "userproperties grid#revisiondata" : {
		    	  selectionchange : this.onUserRevisionSelectionChange,
		     },		     
   	         'objectproperties grid#revisionrows' : {
	 	    	  selectionchange : this.onObjectRowViewSelectionChange,
	 	      },
	 	      'userproperties grid#userobjects' : {
	 	    	  selectionchange : this.onUserObjectRowViewSelectionChange,
	 	      },
   	         'userproperties grid#revisionrows' : {
	 	    	  selectionchange : this.onUserObjectFieldViewSelectionChange,
	 	      },
 	        
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
     	console.log('InitView');	
    	this.getAuditrevisions().store.load();
     },

     onObjectViewSelectionChange: function(component, selected, event) {
    	 console.log('onObjectViewSelectionChange');
    	 var revdata = this.getRevisiondata().store;
    	 var revrows = this.getRevisionrows();
//    	 console.log(revdata.rowsStore);
    	 var record = selected[0];
    	 if (record != null) {
    		 revrows.bindStore(record.rows());
    	 } else {
    		 
    	 }
     },

     onUserObjectRowViewSelectionChange: function(component, selected, event) {
    	 console.log('onObjectViewSelectionChange');
//    	 var revdata = this.getRevisiondata().store;
    	 var uorows = this.getUserobjrows();
//    	 console.log(revdata.rowsStore);
    	 var record = selected[0];
    	 if (record != null) {
    		 uorows.bindStore(record.rows());
    	 } else {
    		 
    	 }
     },     
     
     onRowViewSelectionChange: function(component, selected, event) {
    	 console.log('onRowViewSelectionChange');
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
     
     onObjectRowViewSelectionChange  : function(component, selected, event) {
    	 console.log('onObjectRowViewSelectionChange');
    	 var objfields = this.getObjfields();
    	 var record = selected[0];
    	 if (record != null) {
    		 console.log(record);
    		 console.log(record.auditfields());
    		 objfields.update(record.auditfields());
    	 } else {
    		 
    	 }
     },   
     onUserObjectFieldViewSelectionChange  : function(component, selected, event) {
    	 console.log('onUserObjectRowViewSelectionChange');
    	 var userobjfields = this.getUserobjfields();
    	 var record = selected[0];
    	 if (record != null) {
    		 console.log(record);
    		 console.log(record.auditfields());
    		 userobjfields.update(record.auditfields());
    	 } else {
    		 
    	 }
     },   
    
     
     onObjectRevisionSelectionChange: function(component, selected, event) {
    	 console.log('onObjectRevisionSelectionChange');
    	 var revdata = this.getObjrevisiondata().store;
    	 var revrows = this.getObjrevisionrows();
//    	 console.log(revdata.rowsStore);
    	 var record = selected[0];
    	 if (record != null) {
    		 revrows.bindStore(record.rows());
    	 } else {
    		 
    	 }
     },
    
     onUserRevisionSelectionChange: function(component, selected, event) {
    	 console.log('onUserRevisionSelectionChange');
//    	 var revdata = this.getObjrevisiondata().store;
    	 var revobj = this.getUserrevisionobj();
//    	 console.log(revdata.rowsStore);
    	 var record = selected[0];
    	 if (record != null) {
    		 revobj.bindStore(record.objects());
    	 } else {
    		 
    	 }
     },
     
     
     onMainObjectViewSelectionChange: function(component, selected, event) {
      	console.log('onMainObjectViewSelectionChange');
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
      onMainUserViewSelectionChange: function(component, selected, event) {
       	console.log('onMainUserViewSelectionChange');
//-----> here

       	var userprop = this.getUserproperties();
   	    var revdata = this.getUserrevisiondata();
//   	    var objrows = this.getObjectrows();
   	    var record = selected[0];
//   	    console.log(objprop);
   	    if (record != null) {

   	    	userprop.setTitle(record.data.object);
   		    var userstore = Ext.StoreManager.get('audit.RevisionbyUser');
   		    userstore.load({
   		        id : record.data.user,
   		        scope : this,
   		        callback : function(records, operation, success) {
   			        if (success) {
   				        var revitem = userstore.first();
    				        
   				        var ms = revitem.revisions().getAt(0);
   				        console.log(revdata);
   				        revdata.bindStore(revitem.revisionsStore);
//   				        revdata.getSelectionModel().select(0);
//   				        
//   				        
   			        }
   		        }
   		    });
   	    } else {
//   		    pgdetails.setTitle('');
//   		    pageprop.update('');
//   	        pgcode.setValue('');
//   	    	revdata.unbindStore();
   	    }     	
        	
        	
        },
     
     
     
     onGridViewSelectionChange: function(component, selected, event) {
     	console.log('onGridViewSelectionChange');
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
 	    button.pressed = true;
 	    this.getBauditusers().toggle(false);
 	    this.getBauditdates().toggle(false);
 	    this.getBauditobjects().toggle(false);
 	    //toolbar button  	    
// 	    var store = Ext.StoreManager.get('cms.layout.Layout');
// 	    store.load();
  	    this.getAuditdetails().layout.setActiveItem('revisionproperties');
     },
     /**
 	 * @event
 	 */
     onAuditObjectsClick : function(button, e, options) {
  	    button.pressed = true;

  	    this.getBauditusers().toggle(false);
  	    this.getBauditdates().toggle(false);
  	    this.getBauditrevisions().toggle(false);
  	    this.getAudititemsview().layout.setActiveItem('auditobjects');
  	    var store = Ext.StoreManager.get('audit.RevisedObjects');
  	    store.load();
  	    this.getAuditdetails().layout.setActiveItem('objectproperties');
      },
     /**
  	 * @event
  	 */
      onAuditUsersClick : function(button, e, options) {
   	    button.pressed = true;
 	    this.getBauditrevisions().toggle(false);
 	    this.getBauditdates().toggle(false);
 	    this.getBauditobjects().toggle(false);
   	    this.getAudititemsview().layout.setActiveItem('auditusers');
   	    var store = Ext.StoreManager.get('audit.RevisedUsers');
   	    store.load();
   	 this.getAuditdetails().layout.setActiveItem('userproperties');
       },
     /**
   	 * @event
   	 */
       onAuditDatesClick : function(button, e, options) {
    	    button.pressed = true;
     	    this.getBauditusers().toggle(false);
     	    this.getBauditrevisions().toggle(false);
     	    this.getBauditobjects().toggle(false);
    	    this.getAudititemsview().layout.setActiveItem('auditdays');
    	    var store = Ext.StoreManager.get('audit.RevisedDates');
    	    store.load();
        },


     
     
});