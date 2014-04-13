/**
 * 
 */
Ext.define('RODAdmin.controller.cms.page.PageEdit', {
    extend : 'Ext.app.Controller',

    views : [
	    "RODAdmin.view.cms.page.EditPageWindow"
    ],
    stores : [
             'local.PagePublished', 'cms.pages.PageType'
      ],
      
      
      /**
  	 * @cfg
  	 */
      refs : [
              {
                  ref : 'pagetree',
                  selector : 'pagesitemsview treepanel#pgfolderview'
              },
              {
                  ref : 'parentselect',
                  selector : 'pageedit treepanel#parentselect'
              }
      ],
      /**
  	 * @method
  	 */
      init : function(application) {
  	    this.control({
  	        "pageedit button#save" : {
  	        	 /**
  				 * @listener button-save-click triggered-by:
  				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} button#save    
  				 *           executes {@link #onLayoutEditSaveClick}  
  				 */	        	
  		        click : this.onPageEditSaveClick
  	        },
  	        "pageedit button#cancel" : {
 	        	 /**
 				 * @listener button-save-click triggered-by:
 				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} button#save    
 				 *           executes {@link #onLayoutEditSaveClick}  
 				 */	        	
 		        click : this.onButtonClickCancel
 	        },  	        
  	        "pageedit button#pagepreview" : {
 	        	 /**
 				 * @listener button-save-click triggered-by:
 				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} button#save    
 				 *           executes {@link #onLayoutEditSaveClick}  
 				 */	        	
 		        click : this.onPagePreviewClick
 	        },  	        
  	        "pageadd button#save" : {
 	        	 /**
 				 * @listener button-save-click triggered-by:
 				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} button#save    
 				 *           executes {@link #onLayoutEditSaveClick}  
 				 */	        	
 		        click : this.onPageAddSaveClick
 	        },
  	        
	        "pageedit treepanel#parentselect" : {
	        	 /**
				 * @listener groupselect-load triggered-by:
				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} treepanel#groupselect    
				 *           executes {@link #folderLoad}  
				 */
	            load : this.folderLoad, // this is the only event fired
	            // after loading the store in a
	            // tree view, apparently. This
	            // is REALLY stupid because it
	            // is probabily fired multiple
	            // times.
	        	 /**
				 * @listener groupselect-cellclick triggered-by:
				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} treepanel#groupselect    
				 *           executes {@link #onGroupselectCellClick}  
				 */
	            cellclick : this.onTreeSelectCellClick
	        },
  	    });
      },     
     /**
   	 * @method
   	 */
      onPageEditSaveClick : function(button, e, options) {
  	    var win = button.up('window');
  	    var formPanel = win.down('form');

  	    var me = this;
  	    if (formPanel.getForm().isValid()) {
  		    formPanel.getForm().submit({
  		        clientValidation : true,
  		        url : '/roda/admin/cmspagesave',
  		        success : function(form, action) {
  			        var result = action.result;
  			        if (result.success) {
  				        RODAdmin.util.Alert.msg('Success!', 'Page saved.');
  				        win.close();
  				        me.getController('RODAdmin.controller.cms.page.PageTree').onReloadTreeClick();
  			        }
  			        else {
  				        RODAdmin.util.Util.showErrorMsg(result.msg);
  			        }
  		        },
  		        failure : function(form, action) {
  			        console.log(action.failureType);
  			        console.log(action);
  			        switch (action.failureType) {
  			        case Ext.form.action.Action.CLIENT_INVALID:
  				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
  				        break;

  			        case Ext.form.action.Action.CONNECT_FAILURE:
  				        Ext.Msg.alert('Failure', 'doesn\'t work');
  				        break;
  			        case Ext.form.action.Action.SERVER.INVALID:
  				        Ext.Msg.alert('Failure', action.result.msg);
  				        break;
  			        }
  		        }
  		    });
  	    }

      },

      onButtonClickCancel: function(button, e, options) {
          button.up('window').close();
       },
      
      
      /**
     	 * @method
     	 */
        onPagePreviewClick : function(button, e, options) {
    	    var win = button.up('window');
    	    var formPanel = win.down('form');

    	    var me = this;
    	    if (formPanel.getForm().isValid()) {
    		    formPanel.getForm().submit({
    		        clientValidation : true,
    		        url : '/roda/admin/cmspagepreview',
    		        success : function(form, action) {
    			        var result = action.result;
    			        if (result.success) {
    			    	    var win = Ext.WindowMgr.get('pagepreview');
    			    	    if (!win) {
    			     		    win = Ext.create('RODAdmin.view.cms.page.PagePreviewWindow');
    			     	   	}
    			    	    win.setTitle('Preview');
    			    	    var smth = win.down('panel');

    			    	    console.log(smth);
    			    	    win.show();
    			    	    smth.update(result.message);
    			    	    console.log(result);	
    			        }
    			        else {
    				        RODAdmin.util.Util.showErrorMsg(result.msg);
    			        }
    		        },
    		        failure : function(form, action) {
    			        console.log(action.failureType);
    			        console.log(action);
    			        switch (action.failureType) {
    			        case Ext.form.action.Action.CLIENT_INVALID:
    				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
    				        break;

    			        case Ext.form.action.Action.CONNECT_FAILURE:
    				        Ext.Msg.alert('Failure', 'doesn\'t work');
    				        break;
    			        case Ext.form.action.Action.SERVER.INVALID:
    				        Ext.Msg.alert('Failure', action.result.msg);
    				        break;
    			        }
    		        }
    		    });
    	    }

        },

      
      /**
     	 * @method
     	 */
      onPageAddSaveClick : function(button, e, options) {
    	    var win = button.up('window');
    	    var formPanel = win.down('form');

    	    var me = this;
    	    if (formPanel.getForm().isValid()) {
    		    formPanel.getForm().submit({
    		        clientValidation : true,
    		        url : '/roda/admin/cmspagesave',
    		        success : function(form, action) {
    			        var result = action.result;
    			        if (result.success) {
    				        RODAdmin.util.Alert.msg('Success!', 'Page saved.');
    				        win.close();
    				        me.getController('RODAdmin.controller.cms.page.PageTree').onReloadTreeClick();

//    				        var active = me.getItemsview().layout.getActiveItem();
//    				        if (active.itemId == 'lyfolderview') {
//    					        me.getController('RODAdmin.controller.cms.layout.LayoutTree').onReloadTreeClick();
//    				        }
//    					        me.getController('RODAdmin.controller.cms.layout.LayoutList').onReloadTreeClick();
//    				        }
    			        }
    			        else {
    				        RODAdmin.util.Util.showErrorMsg(result.msg);
    			        }
    		        },
//    				        else if (active.itemId == 'lyiconview') {
    		        failure : function(form, action) {
    			        console.log(action.failureType);
    			        console.log(action);
    			        switch (action.failureType) {
    			        case Ext.form.action.Action.CLIENT_INVALID:
    				        Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
    				        break;

    			        case Ext.form.action.Action.CONNECT_FAILURE:
    				        Ext.Msg.alert('Failure', 'doesn\'t work');
    				        break;
    			        case Ext.form.action.Action.SERVER.INVALID:
    				        Ext.Msg.alert('Failure', action.result.msg);
    				        break;
    			        }
    		        }
    		    });

        }
      },
      
      
      /**
  	 * @method
      }
  	 */

      folderLoad : function(component, options) {
//  	    var active = this.getPagetree().layout.getActiveItem();
  	    var pnode = this.getPagetree().getSelectionModel().getLastSelected();
  	    var rnode = this.getParentselect().getRootNode();
  	    var cnode = rnode.findChild('indice', pnode.data.parentid, true);
  	    console.log(this.getPagetree().getSelectionModel().getLastSelected());  
  	    console.log(pnode);   
  	    console.log(cnode);   
  	    if (cnode != null) {
  		    this.getParentselect().getSelectionModel().select(cnode);
  	    }      
      },
      /**
  	 * @method
  	 */
      onTreeSelectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
//    	console.log(component.up('pageedit').down('form').down('fieldset'));  
    	  
  	    component.up('pageedit').down('displayfield[name=parent]').setValue(record.data.title + '('+record.data.indice+')');
  	    component.up('pageedit').down('hiddenfield[name=parentid]').setValue(record.data.indice);
      },
});
