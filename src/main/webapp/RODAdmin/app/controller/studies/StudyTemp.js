Ext.define('RODAdmin.controller.studies.StudyTemp', {
    extend : 'Ext.app.Controller',
    stores : [
              'studies.TempStudy', 
//              'common.Audit'
              ],
    
    views : [
            'RODAdmin.view.studies.StudyItemstempview',
            'RODAdmin.view.studies.StudiesTemp'
    ],
    /**
	 * @cfg
	 */
    refs : [
            {
                ref : 'iconview',
                selector : 'studyitemstempview grid#sticonview'
            }
           ],
            
    init : function(application) {
       	    this.control({
    	        "studyitemstempview grid#sticonview" : {
    	            selectionchange : this.onSelectionChange,
    	        },       	        
       	    });
        	this.listen({
                controller: {
                    '*': {
                        controllerStudiestempInitView: this.initView
                    }
                }
       		});
    },

    initView : function() {
    	console.log('InitView temp');	
    	this.getIconview().getStore().load();
    	
//    	Ext.History.add('menu-studiesmain');
    },
    onSelectionChange : function(component, selected, event) {
	    console.log('folderviewselectionchange');
	    var record = selected[0];
	    
	    if (record != null) {
	    	console.log(record.filepropertiesset());
	    }
    }
    

});    