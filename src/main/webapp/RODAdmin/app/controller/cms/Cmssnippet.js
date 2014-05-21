Ext.define('RODAdmin.controller.cms.Cmssnippet', {
    extend : 'Ext.app.Controller',

    
    views : [
             'RODAdmin.view.cms.snippet.Snippets',
             'RODAdmin.view.cms.snippet.SnippetItemsview',
             'RODAdmin.view.cms.snippet.SnippetDetails',
             'RODAdmin.view.cms.snippet.details.SnippetProperties',
//             "cms.snippet.EditSnippetWindow",
             'RODAdmin.view.cms.snippet.SnippetContextMenu',
             'RODAdmin.view.cms.snippet.SnippetGroupContextMenu',
//             'cms.snippet.AddSnippetToGroupWindow',
//             'cms.snippet.SnippetItemviewContextMenu',
             'RODAdmin.view.cms.snippet.details.SnippetUsage'
     ],
    
    refs : [
            {
                ref : 'itemsview',
                selector : 'snippetitemsview'
            }, {
                ref : "folderview",
                selector : "snippetitemsview treepanel#snfolderview"
            }, {
                ref : 'folderselect',
                selector : 'snippetedit treepanel#folderselect'
            }, {
                ref : 'snippetproperties',
                selector : 'snippetproperties panel#sndata '
            }

    ],

    init : function(application) {
	    this.control({
	        "cmssnippets toolbar button#icon-view" : {
	            /**
				 * @listener cmssnippets-toolbar-button-icon-view-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.Snippets Snippets}
				 *           toolbar button#icon-view 
				 *           {@link #onIconViewClick}
				 */		        	
		        click : this.onIconViewClick
	        },
	        "cmssnippets toolbar button#tree-view" : {
	            /**
				 * @listener cmssnippets-toolbar-button-tree-view-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.Snippets Snippets}
				 *           toolbar button#tree-view 
				 *           {@link #onTreeViewClick}
				 */		        	
		        click : this.onTreeViewClick
	        },
	        "snippetproperties toolbar#snproptoolbar button#editsnippet" : {
	            /**
				 * @listener snippetproperties-toolbar-snproptoolbar-button-editsnippet-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.details.SnippetProperties SnippetProperties}
				 *           toolbar#snproptoolbar button#editsnippet 
				 *           {@link #onsntoolbarEditClick}
				 */		        	
		        click : this.onsntoolbarEditClick
	        },
	        "snippetproperties toolbar#snproptoolbar button#deletesnippet" : {
	            /**
				 * @listener snippetproperties-toolbar-snproptoolbar-button-deletesnippet-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.details.SnippetProperties SnippetProperties}
				 *           toolbar#snproptoolbar button#deletesnippet
				 *           {@link #onsntoolbarDeleteClick}
				 */		        	
		        click : this.onsntoolbarDeleteClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#getsnippetaudit" : {
	            /**
				 * @listener snippetproperties-toolbar-snproptoolbar-button-getsnippetaudit-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.details.SnippetProperties SnippetProperties}
				 *           toolbar#snproptoolbar button#getsnippetaudit
				 *           {@link #onsntoolbarAuditClick}
				 */		        	
	        	click : this.onsntoolbarAuditClick
	        },
	    });
    	this.listen({
            controller: {
                '*': {
                    controllerCmssnippetsInitView: this.initView
                }
            }
    	 });
    },
    initView : function() {
    	console.log('InitView, baby');	
    },
    /**
	 * @method
	 */
    onIconViewClick : function(button, e, options) {
	    console.log('onIconviewClick');
	    this.getItemsview().layout.setActiveItem('sniconview');
	    var store = Ext.StoreManager.get('cms.snippet.Snippet');
	    store.load();
    },
    /**
	 * @method
	 */
    onTreeViewClick : function(button, e, options) {
	    console.log('onfolderviewClick');
	    this.getItemsview().layout.setActiveItem('snfolderview');
	    var store = Ext.StoreManager.get('cms.snippet.SnippetTree');
	    store.load();
    },
    /**
	 * @method
	 */    
    onsntoolbarEditClick : function(button, e, options) {
//	    var fp = this.getSnippetproperties().data;
//	    var llp = this.getSnippetproperties();
//	    var win = Ext.create('RODAdmin.view.cms.snippet.EditSnippetWindow');
//	    win.setTitle('Edit File "' + fp.data.name + '" (id: ' + fp.data.id + ')');
//	    var wtree = win.down('treepanel');
//	    wtree.expandAll();
//	    win.show();
//	    win.down('form').getForm().loadRecord(fp);
//	    
	    
	    
	    var fp = this.getSnippetproperties().data;
	    var llp = this.getSnippetproperties();

	    	console.log('edit layout smth');
    	   if (fp.data.itemtype == 'snippetgroup') {	        
    		   console.log('edit snippet group');
        	   win = Ext.WindowMgr.get('snippetgroupedit');
        	   console.log(win);
        	   if (!win) {
        		   win = Ext.create('RODAdmin.view.cms.snippet.EditSnippetGroupWindow');
        	   }
        	   win.setTitle('Edit Snippet Group');
        	   var wtree = win.down('treepanel');
        	   var snippetgroupstore = Ext.create('RODAdmin.store.cms.snippet.SnippetGroup');
        	   snippetgroupstore.load({
        		   id : fp.data.id, // set the id here
        		   scope : this,
        		   callback : function(records, operation, success) {
        			   if (success) {
        				   var snippetgroup = snippetgroupstore.first();
        				   win.down('form').getForm().loadRecord(snippetgroup);
        				   win.down('form').down('hiddenfield#groupid').setValue(snippetgroup.data.groupid);
        			   }
        		   }
        	   });
        	   win.show();
    	   } else {	   
		   console.log('edit snippet');	                        		   
    	   win = Ext.WindowMgr.get('snippetedit');
    	   if (!win) {
    		   win = Ext.create('RODAdmin.view.cms.snippet.EditSnippetWindow');
    	   }
    	   win.setTitle('Edit Snippet');
    	   var wtree = win.down('treepanel');
    	   var snippetitemstore = Ext.create('RODAdmin.store.cms.snippet.SnippetItem');
    	   snippetitemstore.load({
    		   id : fp.data.id, // set the id here
    		   scope : this,
    		   callback : function(records, operation, success) {
    			   if (success) {
    				   var snippetitem = snippetitemstore.first();
    				   win.down('form').getForm().loadRecord(snippetitem);
    				   win.down('form').down('hiddenfield#groupid').setValue(snippetitem.data.groupid);
    			   }
    		   }
    	   });
    	   win.show();
    	   }	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
    },
    /**
	 * @method
	 */
    onsntoolbarDeleteClick : function(button, e, options) {
	    var fp = this.getSnippetproperties().data;
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
				    url = '/roda/j/admin/layoutgroupdrop';
				    parms = {'groupid' : fp.data.id };
			    }
			    else {
				    url = '/roda/j/admin/layoutdrop';
				    parms = {'layoutid' : fp.data.id };
			    }

			    Ext.Ajax.request({
			        url : url,
			        method : "POST",
			        params : parms,	
			        success : function() {
				        RODAdmin.util.Alert.msg('Success!', 'Deleted.');
				        Ext.StoreManager.get('cms.snippet.Snippet').load();
				        Ext.StoreManager.get('cms.snippet.SnippetTree').load();
				        //imediat ce aflam cum dracu se selecteaza un nod, trebuie sa invatam sa selectam nodul superior celui pe care l-am sters				        

			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
	    // event.stopEvent();
    },
    /**
	 * @method
	 */
    onsntoolbarAuditClick : function(button, e, options) {
	    console.log('auditfile clicked, cool stuff ahead');
	    var fp = this.getSnippetproperties().data;
	    var win = Ext.create('RODAdmin.view.common.AuditWindow');
	    win.setTitle('Audit data for "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    win.show();
	    var auditgrid = win.down('grid[itemId=auditgrid]');
	    auditgrid.store.load();
    },

});