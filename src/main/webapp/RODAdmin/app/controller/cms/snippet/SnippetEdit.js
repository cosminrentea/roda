/**
 * 
 */
Ext.define('RODAdmin.controller.cms.snippet.SnippetEdit', {
    extend : 'Ext.app.Controller',

    views : [
	    "RODAdmin.view.cms.snippet.EditSnippetWindow",
	    "RODAdmin.view.cms.snippet.AddSnippetToGroupWindow",
	    "RODAdmin.view.cms.snippet.GroupWindow"
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
                selector : 'snippetedit treepanel#groupselect'
            }
    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
	        "snippetedit treepanel#groupselect" : {
	            /**
				 * @listener snippetedit-treepanel-groupselect triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.EditSnippetWindow EditSnippetWindow}
				 *           treepanel#groupselect  
				 *           this is the only event fired after loading the store in a tree view, apparently. This
	             * 		  	 is REALLY stupid because it is probabily fired multiple times.  
				 *           {@link #folderLoad}
				 */		        	
	            load : this.folderLoad, 
	            cellclick : this.onGroupselectCellClick
	        },
	        "snippetedit button#save" : {
	            /**
				 * @listener snippetedit-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.EditSnippetWindow EditSnippetWindow}
				 *           button#save
				 *           {@link #onSnippetEditSaveClick}
				 */	
	        	click : this.onSnippetEditSaveClick
	        },
	        "snippetgadd button#save" : {
	            /**
				 * @listener snippetgadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.AddSnippetToGroupWindow AddSnippetToGroupWindow}
				 *           button#save
				 *           {@link #onSnippetAddGroupSaveClick}
				 */	
	        	click : this.onSnippetAddGroupSaveClick
	        },
	        "sngroupadd button#save" : {
	            /**
				 * @listener sngroupadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.GroupWindow GroupWindow}
				 *           button#save
				 *           {@link #onGroupSaveClick}
				 */	
		        click : this.onGroupSaveClick

	        }
	    });
    },
    /**
	 * @method
	 */
    
    onSnippetAddGroupSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;

	    /**
	     * @todo Store1 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */	    
	    
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/snippetsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Snippet saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'snfolderview') {
					        me.getController('RODAdmin.controller.cms.snippet.SnippetTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sniconview') {
					        me.getController('RODAdmin.controller.cms.snippet.SnippetList').onReloadTreeClick();
				        }

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
				        // Ext.Msg.alert('Failure', action.result.msg);
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
    
    onGroupSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    
	    /**
	     * @todo Store2 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */	    
	    
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/snippetgroupsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Snippet group saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'snfolderview') {
					        me.getController('RODAdmin.controller.cms.snippet.SnippetTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sniconview') {
					        me.getController('RODAdmin.controller.cms.snippet.SnippetList').onReloadTreeClick();
				        }
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

				        // Ext.Msg.alert('Failure', action.result.msg);
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
    onSnippetEditSaveClick : function(button, e, options) {
	    // ok, now we're here. Let's save the little fucker.
	    // first, we need to find the window
	    var win = button.up('window');
	    // then, the form
	    var formPanel = win.down('form');
	    // we will need to reload the store, this is tricky since we need to
		// determine which view is active. But we'll leave this for later
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var folderview = this.getFolderview()
	    var me = this;

	    /**
	     * @todo Store3 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */
	    
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/snippetsave',

		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Snippet saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'snfolderview') {
					        me.getController('RODAdmin.controller.cms.snippet.SnippetTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'lyiconview') {
					        me.getController('RODAdmin.controller.cms.snippet.SnippetList').onReloadTreeClick();
				        }
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

				        // Ext.Msg.alert('Failure', action.result.msg);
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
    
    folderLoad : function(component, options) {
	    var active = this.getItemsview().layout.getActiveItem();
	    var pnode = active.getSelectionModel().getLastSelected();
	    var rnode = this.getFolderselect().getRootNode();
	    var cnode = rnode.findChild('id', pnode.data.folderid, true);
	    if (cnode != null) {
		    this.getFolderselect().getSelectionModel().select(cnode);
	    }
    },
    /**
	 * @method
	 */
    onGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    component.up('snippetedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name + '('+record.data.indice+')');
	    component.up('snippetedit').down('hiddenfield#groupid').setValue(record.data.indice);
    },
    
});