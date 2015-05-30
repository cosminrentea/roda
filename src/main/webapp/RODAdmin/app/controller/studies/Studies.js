/**
 * 
 */
Ext.define('RODAdmin.controller.studies.Studies', {
    extend : 'Ext.app.Controller',

    views :[
            'RODAdmin.view.studies.Studies',            
            'RODAdmin.view.studies.StudyItemsview',
            ],
    
    refs : [
            {
                ref : 'itemsview',
                selector : 'studyitemsview'
            }, 
            {
                ref : 'studygrid',
                selector : 'studyitemsview grid#sticonview'
            }, 
            {
                ref : "folderview",
                selector : "studyitemsview treepanel#stfolderview"
            }, {
                ref : 'folderselect',
                selector : 'studyedit treepanel#groupselect'
            }, {
                ref : 'studyproperties',
                selector : 'studyproperties panel#stdata ' 
            }

    ],

    init : function(application) {
	    this.control({
	        "studiesmain toolbar button#icon-view" : {
	            /**
				 * @listener studies-toolbar-button-icon-view-click triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#icon-view
				 *           {@link #onIconViewClick}
				 */	
		        click : this.onIconViewClick
	        },
	        "studiesmain toolbar button#tree-view" : {
	            /**
				 * @listener studies-toolbar-button-tree-view-click triggered-by:
				 *           {@link RODAdmin.view.studies.Studies Studies}
				 *           toolbar button#tree-view
				 *           {@link #onTreeViewClick}
				 */	
		        click : this.onTreeViewClick
	        },

	        "studyproperties toolbar#stproptoolbar button#editstudy" : {
	            /**
				 * @listener studyproperties-toolbar-stproptoolbar-button-editstudy-click triggered-by:
				 *           {@link RODAdmin.view.studies.details.StudyProperties StudyProperties}
				 *           toolbar#stproptoolbar button#editstudy
				 *           {@link #onsttoolbarEditClick}
				 */	
		        click : this.onsttoolbarEditClick
	        },
	        "studyproperties toolbar#stproptoolbar button#deletestudy" : {
	            /**
				 * @listener studyproperties-toolbar-stproptoolbar-button-deletestudy-click triggered-by:
				 *           {@link RODAdmin.view.studies.details.StudyProperties StudyProperties}
				 *           toolbar#stproptoolbar button#deletestudy
				 *           {@link #onsttoolbarDeleteClick}
				 */	
		        click : this.onsttoolbarDeleteClick
	        },
	        "studyproperties toolbar#stproptoolbar button#getstudyaudit" : {
	            /**
				 * @listener studyproperties-toolbar-stproptoolbar-button-getstudyaudit-click triggered-by:
				 *           {@link RODAdmin.view.studies.details.StudyProperties StudyProperties}
				 *           toolbar#stproptoolbar button#getstudyaudit
				 *           {@link #onsttoolbarAuditClick}
				 */	
		        click : this.onsttoolbarAuditClick
	        },
	    });
    	this.listen({
            controller: {
                '*': {
                    controllerStudiesmainInitView: this.initView
                }
            }
    	 });
    },

    /**
	 * @method
	 */
    initView : function() {
    	console.log('InitView');	
    	Ext.History.add('menu-studiesmain');
   	//	 this.getStudygrid().store.load();
    //	 this.getFolderview().store.reload(); 
    },
    /**
	 * @method
	 */
    onIconViewClick : function(button, e, options) {
	    console.log('onIconviewClick new controller');
	    console.log(this.getItemsview());
	    this.getItemsview().layout.setActiveItem('sticonview');
	    var store = Ext.StoreManager.get('studies.Study');
	    store.load();
    },
    /**
	 * @method
	 */
    onAddStudyClick : function(button, e, options) {
	    console.log('onAddStudyClick new controller');
	    win = Ext.WindowMgr.get('studyadd');
	    console.log(win);
	    if (!win) {
    	 win = Ext.create('RODAdmin.view.studies.CBEditor.AddStudyWindow');
	    //win = Ext.create('RODAdmin.view.studies.AddStudyWindow');
	    }
	    win.setTitle('Add a new Study');
	    win.show();
    },
    
    /**
	 * @method
	 */
    onTreeViewClick : function(button, e, options) {
	    console.log('onfolderviewClick new controller');
	    this.getItemsview().layout.setActiveItem('stfolderview');
	    var store = Ext.StoreManager.get('studies.StudyTree');
	    store.load();
    },
    /**
	 * @method
	 */
    onsttoolbarEditClick : function(button, e, options) {
	    var fp = this.getStudyproperties().data;
	    var llp = this.getStudyproperties();
	    console.log(llp);
	    console.log(fp);
		   console.log('edit study smth');
    	   if (fp.data.itemtype == 'studygroup') {	        
    		   console.log('edit catalog');
        	   win = Ext.WindowMgr.get('catalogedit');
        	   console.log(win);
        	   if (!win) {
        		   win = Ext.create('RODAdmin.view.studies.EditCatalogWindow');
        	   }
        	   win.setTitle('Edit Catalog');
        	   var wtree = win.down('treepanel');
        	   var catalogstore = Ext.create('RODAdmin.store.studies.Catalog');
        	   catalogstore.load({
        		   id : fp.data.id, // set the id here
        		   scope : this,
        		   callback : function(records, operation, success) {
        			   if (success) {
        				   var catalog = catalogstore.first();
        				   win.down('form').getForm().loadRecord(catalog);
        				   win.down('form').down('hiddenfield#groupid').setValue(catalog.data.groupid);
        			   }
        		   }
        	   });
        	   win.show();
    	   } else {	   
		   console.log('edit study');	                        		   
    	   win = Ext.WindowMgr.get('studyedit');
    	   if (!win) {
    		   win = Ext.create('RODAdmin.view.studies.EditStudyWindow');
    	   }
    	   win.setTitle('Edit Study');
    	   var wtree = win.down('treepanel');
    	   var studyitemstore = Ext.create('RODAdmin.store.studies.StudyItem');
    	   studyitemstore.load({
    		   id : fp.data.id, // set the id here
    		   scope : this,
    		   callback : function(records, operation, success) {
    			   if (success) {
    				   var studyitem = studyitemstore.first();
    				   win.down('form').getForm().loadRecord(studyitem);
    				   win.down('form').down('hiddenfield#groupid').setValue(studyitem.data.groupid);
    			   }
    		   }
    	   });
    	   win.show();
    	   }
    },
    /**
	 * @method
	 */
    onsttoolbarDeleteClick : function(button, e, options) {
	    console.log('editfile clicked');
	    var fp = this.getStudyproperties().data;

	    /**
	     * @todo Store 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */	    

	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the ' + fp.data.itemtype + ' '
	            + fp.data.name + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    var url = '';
			    if (fp.data.itemtype == 'studygroup') {
				    url = RODAdmin.util.Globals.baseurl + 'adminjson/catalogdrop';
				    parms = {'groupid' : fp.data.id };
			    }
			    else {
				    url = RODAdmin.util.Globals.baseurl + 'adminjson/studydrop';
				    parms = {'studyid' : fp.data.id };
			    }

			    Ext.Ajax.request({
			        url : url,
			        method : "POST",
			        params : parms,	
			        success : function() {
				        RODAdmin.util.Alert.msg('Success!', 'Deleted.');
				        Ext.StoreManager.get('studies.Study').load();
				        Ext.StoreManager.get('studies.StudyTree').load();
			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
    },
    /**
	 * @method
	 */
    onsttoolbarAuditClick : function(button, e, options) {
	    console.log('auditfile clicked, cool stuff ahead');
	    var fp = this.getStudyproperties().data;
	    var win = Ext.create('RODAdmin.view.common.AuditWindow');
	    win.setTitle('Audit data for "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    win.show();
	    var auditgrid = win.down('grid[itemId=auditgrid]');
	    auditgrid.store.load();
	    console.log(auditgrid.store);
    },

});