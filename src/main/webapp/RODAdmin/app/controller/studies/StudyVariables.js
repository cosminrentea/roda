
Ext.define('RODAdmin.controller.studies.StudyVariables', {
	extend : 'Ext.app.Controller',

	/**
	 * @todo getstores De convertit toate apelurile catre store-uri spre
	 *       getStore.
	 */

	stores : [
	         
	          ],
    refs : [
            {
         	   ref : 'vardetailspanel',
         	   selector : 'studyvariables panel#vardetails'
            }, 
            {
          	   ref : 'vdcode',
          	   selector : 'studyvariables grid#respcode'
             }, 
             {
            	   ref : 'vdcat',
            	   selector : 'studyvariables grid#respcat'
               }, 
               {
              	   ref : 'vdnumeric',
              	   selector : 'studyvariables panel#respnumeric'
                 }, 
                 {
                	   ref : 'vdmissing',
                	   selector : 'studyvariables grid#missingval'
                   }, 
            ],
    
    init : function(application) {
 	   this.control({
 		  "studyvariables grid#studyvars" : {
			   /**
			    * @listener studyitemsview-treepanel-folderview-selectionchange
			    *           triggered-by:
			    *           {@link RODAdmin.view.studies.StudyItemview StudyItemsview}
			    *           treepanel#stfolderview executes
			    *           {@link #onFolderviewSelectionChange}
			    */
			   selectionchange : this.onVariableSelectionChange,
 		  }
 	   });
    },
    
    onVariableSelectionChange : function(component, selected, event) {
    	var record = selected[0];
    	console.log ('variableview selection change');
    	console.log(record);
    	 var varitemstore = Ext.create('RODAdmin.store.studies.VariableItem');
    	 varitemstore.load({
  		  // id : record.data.indice, // set the id here
  		   scope : this,
  		   callback : function(records, operation, success) {
  			   if (success) {
  				   console.log('success');
  				   var vdpanel = this.getVardetailspanel();
  				   console.log(vdpanel);
  				   var varitem = varitemstore.first();
  				   console.log(varitem);
  				   console.log(varitem.data.respdomain);
  				   if (varitem.missingStore) {
  					   this.getVdmissing().bindStore(varitem.missingStore);
  				   }
  				   if (varitem.data.respdomain == 'Category') {
  					   vdpanel.layout.setActiveItem('respcategory');
  					   this.getVdcat().bindStore(varitem.catresponsesStore);
  					   console.log ('category');
  				   } else if (varitem.data.respdomain == 'Code') {
  					   console.log ('code');
  					   vdpanel.layout.setActiveItem('respcode');
  					   this.getVdcode().bindStore(varitem.coderesponsesStore);
  				   } else if (varitem.data.respdomain == 'Numeric') {
  					   vdpanel.layout.setActiveItem('respnumeric');
  					   console.log ('numeric');
  				   }	   
  			   }
  		   }
  	   });    	
    }
    
    
});