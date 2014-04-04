/**
 * 
 */
Ext.define('RODAdmin.controller.cron.ActionEdit', {
    extend : 'Ext.app.Controller',

    views : [
	    "RODAdmin.view.cron.action.EditCronActionWindow"
    ],

    refs : [
            {
                ref : 'itemsview',
                selector : 'layoutitemsview'
            }, {
                ref : 'folderselect',
                selector : 'layoutedit treepanel#groupselect'
            }
    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
		    "cronactionedit button#save" : {
	            /**
				 * @listener cronactionedit-button-save-click triggered-by:
				 *           {@link RODAdmin.view.cron.action.EditCronActionWindow EditCronActionWindow}
				 *           button#save
				 *           {@link #onEditSaveClick}
				 */		        	
			    click : this.onEditSaveClick
		    },
	    });
    },
    /**
	 * @method
	 */
    onEditSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var currentNode = this.getItemsview().getSelectionModel().getLastSelected();
	    var itemsview = this.getItemsview()
	    var me = this;
	    /**
	     * @todo Store 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */	    
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Action saved.');
				        win.close();
				        me.getController('RODAdmin.controller.cron.ActionList').onReloadTreeClick();
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

});