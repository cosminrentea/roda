/**
 * 
 */
Ext.define('RODAdmin.controller.cms.Cmslayouts', {
    extend : 'Ext.app.Controller',

    views :[
            'RODAdmin.view.cms.layout.Layouts'            
            
            ],
    
    refs : [
            {
                ref : 'itemsview',
                selector : 'layoutitemsview'
            }, 
            {
                ref : 'layoutgrid',
                selector : 'layoutitemsview grid#lyiconview'
            }, 
            {
                ref : "folderview",
                selector : "layoutitemsview treepanel#lyfolderview"
            }, {
                ref : 'folderselect',
                selector : 'layoutedit treepanel#groupselect'
            }, {
                ref : 'layoutproperties',
                selector : 'layoutproperties panel#lydata '                
//                selector : 'layoutproperties panel#lycontent'
            }

    ],

    init : function(application) {
	    this.control({
	        "cmslayouts toolbar button#icon-view" : {
	            /**
				 * @listener cmslayouts-toolbar-button-icon-view-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.Layouts Layouts}
				 *           toolbar button#icon-view
				 *           {@link #onIconViewClick}
				 */	
		        click : this.onIconViewClick
	        },
	        "cmslayouts toolbar button#tree-view" : {
	            /**
				 * @listener cmslayouts-toolbar-button-tree-view-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.Layouts Layouts}
				 *           toolbar button#tree-view
				 *           {@link #onTreeViewClick}
				 */	
		        click : this.onTreeViewClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#editlayout" : {
	            /**
				 * @listener layoutproperties-toolbar-lyproptoolbar-button-editlayout-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.details.LayoutProperties LayoutProperties}
				 *           toolbar#lyproptoolbar button#editlayout
				 *           {@link #onlytoolbarEditClick}
				 */	
		        click : this.onlytoolbarEditClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#deletelayout" : {
	            /**
				 * @listener layoutproperties-toolbar-lyproptoolbar-button-deletelayout-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.details.LayoutProperties LayoutProperties}
				 *           toolbar#lyproptoolbar button#deletelayout
				 *           {@link #onlytoolbarDeleteClick}
				 */	
		        click : this.onlytoolbarDeleteClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#getlayoutaudit" : {
	            /**
				 * @listener layoutproperties-toolbar-lyproptoolbar-button-getlayoutaudit-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.details.LayoutProperties LayoutProperties}
				 *           toolbar#lyproptoolbar button#getlayoutaudit
				 *           {@link #onlytoolbarAuditClick}
				 */	
		        click : this.onlytoolbarAuditClick
	        },
	    });
    	this.listen({
            controller: {
                '*': {
                    controllerCmslayoutsInitView: this.initView
                }
            }
    	 });
    },

    /**
	 * @method
	 */
    initView : function() {
    	console.log('InitView, baby');	
    	 this.getLayoutgrid().store.load();
    	 Ext.History.add('menu-cmslayouts');
 //   	 this.getFolderview().store.reload(); not loading
    },
    /**
	 * @method
	 */
    onIconViewClick : function(button, e, options) {
	    console.log('onIconviewClick new controller');
	    this.getItemsview().layout.setActiveItem('lyiconview');
	    var store = Ext.StoreManager.get('cms.layout.Layout');
	    store.load();
    },
    /**
	 * @method
	 */
    onTreeViewClick : function(button, e, options) {
	    console.log('onfolderviewClick new controller');
	    this.getItemsview().layout.setActiveItem('lyfolderview');
	    var store = Ext.StoreManager.get('cms.layout.LayoutTree');
	    store.load();
    },
    /**
	 * @method
	 */
    onlytoolbarEditClick : function(button, e, options) {
	    var fp = this.getLayoutproperties().data;
	    var llp = this.getLayoutproperties();
	    console.log(llp);
	    console.log(fp);
		   console.log('edit layout smth');
    	   if (fp.data.itemtype == 'layoutgroup') {	        
    		   console.log('edit layout group');
        	   win = Ext.WindowMgr.get('layoutgroupedit');
        	   console.log(win);
        	   if (!win) {
        		   win = Ext.create('RODAdmin.view.cms.layout.EditLayoutGroupWindow');
        	   }
        	   win.setTitle('Edit Layout Group');
        	   var wtree = win.down('treepanel');
        	   var layoutgroupstore = Ext.create('RODAdmin.store.cms.layout.LayoutGroup');
        	   layoutgroupstore.load({
        		   id : fp.data.id, // set the id here
        		   scope : this,
        		   callback : function(records, operation, success) {
        			   if (success) {
        				   var layoutgroup = layoutgroupstore.first();
        				   win.down('form').getForm().loadRecord(layoutgroup);
        				   win.down('form').down('hiddenfield#groupid').setValue(layoutgroup.data.groupid);
        			   }
        		   }
        	   });
        	   win.show();
    	   } else {	   
		   console.log('edit layout');	                        		   
    	   win = Ext.WindowMgr.get('layoutedit');
    	   if (!win) {
    		   win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
    	   }
    	   win.setTitle('Edit Layout');
    	   var wtree = win.down('treepanel');
    	   var layoutitemstore = Ext.create('RODAdmin.store.cms.layout.LayoutItem');
    	   layoutitemstore.load({
    		   id : fp.data.id, // set the id here
    		   scope : this,
    		   callback : function(records, operation, success) {
    			   if (success) {
    				   var layoutitem = layoutitemstore.first();
    				   win.down('form').getForm().loadRecord(layoutitem);
    				   win.down('form').down('hiddenfield#groupid').setValue(layoutitem.data.groupid);
    			   }
    		   }
    	   });
    	   win.show();
    	   }
    },
    /**
	 * @method
	 */
    onlytoolbarDeleteClick : function(button, e, options) {
	    console.log('editfile clicked');
	    var fp = this.getLayoutproperties().data;

	    /**
	     * @todo Store 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */	    

	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the ' + fp.data.itemtype + ' '
	            + fp.data.name + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    var url = '';
			    if (fp.data.itemtype == 'layoutgroup') {
				    url = RODAdmin.util.Globals.baseurl + '/adminjson/layoutgroupdrop';
				    parms = {'groupid' : fp.data.id };
			    }
			    else {
				    url = RODAdmin.util.Globals.baseurl + '/adminjson/layoutdrop';
				    parms = {'layoutid' : fp.data.id };
			    }

			    Ext.Ajax.request({
			        url : url,
			        method : "POST",
			        params : parms,	
			        success : function() {
				        RODAdmin.util.Alert.msg('Success!', 'Deleted.');
				        Ext.StoreManager.get('cms.layout.Layout').load();
				        Ext.StoreManager.get('cms.layout.LayoutTree').load();
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
    onlytoolbarAuditClick : function(button, e, options) {
	    console.log('auditfile clicked, cool stuff ahead');
	    var fp = this.getLayoutproperties().data;
	    var win = Ext.create('RODAdmin.view.common.AuditWindow');
	    win.setTitle('Audit data for "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    win.show();
	    var auditgrid = win.down('grid[itemId=auditgrid]');
	    auditgrid.store.load();
	    console.log(auditgrid.store);
    },

});