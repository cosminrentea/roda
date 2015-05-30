/**
 * 
 */
Ext.define('RODAdmin.controller.cms.news.NewsEdit', {
    extend : 'Ext.app.Controller',

    views : [
	    "RODAdmin.view.cms.news.EditNewsWindow",
    ],

    refs : [
            {
                ref : 'itemsview',
                selector : 'newsitemsview grid#newsiconview'
            }
    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
	        "newsedit button#save" : {
	            /**
				 * @listener snippetedit-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.EditSnippetWindow EditSnippetWindow}
				 *           button#save
				 *           {@link #onSnippetEditSaveClick}
				 */	
	        	click : this.onNewsEditSaveClick
	        },

	    });
    },

    /**
	 * @method
	 */   
    onNewsEditSaveClick : function(button, e, options) {
	    // ok, now we're here. Let's save the little fucker.
	    // first, we need to find the window
	    var win = button.up('window');
	    // then, the form
	    var formPanel = win.down('form');
	    // we will need to reload the store, this is tricky since we need to
		// determine which view is active. But we'll leave this for later
	    var currentNode = this.getItemsview().getSelectionModel().getLastSelected();
	    var itemsview = this.getItemsview()
	    var me = this;

	    /**
	     * @todo Store3 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */
	    
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : RODAdmin.util.Globals.baseurl + 'adminjson/newssave',

		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Newsitem saved.');
				        win.close();
				        //ToDO
					        me.getController('RODAdmin.controller.cms.news.NewsList').onReloadGridClick(); 
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