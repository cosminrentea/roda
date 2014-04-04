/**
 * 
 * Audit - controller care se ocupa de interfata de audit 
 */
Ext.define('RODAdmin.controller.audit.AuditMain', {
    extend : 'Ext.app.Controller',
    
    stores : [
              'audit.Revisions',
              'audit.RevisionInfo'
      ], 
    
    
    views : [
             'RODAdmin.view.audit.Audit',
             'RODAdmin.view.audit.AuditItemsview',
             'RODAdmin.view.audit.details.RevisionProperties',
             'RODAdmin.view.audit.AuditDetails'
     ],
 
     refs : [
             {
            	 ref: 'audititemsview',
            	 selector: 'audititemsview'
             },
             {
            	 ref: 'revisionproperties',
            	 selector: 'revisionproperties'
             },
             
             {
            	 ref: 'revisiondata',
            	 selector: 'revisionproperties grid#revisiondata'
             },
             {
            	 ref: 'revisionrows',
            	 selector: 'revisionproperties grid#revisionrows'
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