/**
 * 
 */
Ext.define('RODAdmin.controller.cms.layout.LayoutEdit', {
    extend : 'Ext.app.Controller',
    /**
	 * @cfg
	 */
    views : [
	    "RODAdmin.view.cms.layout.EditLayoutWindow",
	    "RODAdmin.view.cms.layout.AddLayoutToGroupWindow",
	    "RODAdmin.view.cms.layout.GroupWindow"
    ],
    /**
	 * @cfg
	 */
    refs : [
            {
                ref : 'itemsview',
                selector : 'layoutitemsview'
            }, {
                ref : "folderview",
                selector : "layoutitemsview treepanel#lyfolderview"
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
	        "layoutedit treepanel#groupselect" : {
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
	            cellclick : this.onGroupselectCellClick
	        },
	        "layoutedit button#save" : {
	        	 /**
				 * @listener button-save-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} button#save    
				 *           executes {@link #onLayoutEditSaveClick}  
				 */	        	
		        click : this.onLayoutEditSaveClick
	        },
	        "layoutgadd button#save" : {
	        	 /**
				 * @listener layoutgadd-button-save-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.AddLayoutToGroupWindow AddLayoutToGroupWindow} button#save    
				 *           executes {@link #onLayoutAddGroupSaveClick}  
				 */	
	        	click : this.onLayoutAddGroupSaveClick
	        },
	        "lygroupadd button#save" : {
	        	 /**
				 * @listener lygroupadd-button-save-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.GroupWindow GroupWindow} button#save    
				 *           executes {@link #onGroupSaveClick}  
				 */	
	        	click : this.onGroupSaveClick

	        }
	    });
    },
    /**
	 * @method
	 * Se ocupa de scrierea unui grup modificat.  
	 */
    onGroupSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
/**
 * @todo Store 
 * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
 */
	    
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/admin/layoutgroupsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Layout group saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'lyfolderview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'lyiconview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutList').onReloadTreeClick();
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

    onLayoutEditSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var bt = button;
	    var formPanel = win.down('form');
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var folderview = this.getFolderview()
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/admin/layoutsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Layout saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'lyfolderview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'lyiconview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutList').onReloadGridClick();
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
				        Ext.Msg.alert('Failure', 'Form fields may must be submitted with invalid values');
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

    onLayoutAddGroupSaveClick : function(button, e, options) {
	    console.log('small step for man...');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/admin/layoutsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        console.log('closing');
				        console.log(win);
			        	RODAdmin.util.Alert.msg('Success!', 'Layout saved on the server.');
				        // store.load();
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'lyfolderview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'lyiconview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutList').onReloadTreeClick();
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
	    var cnode = rnode.findChild('indice', pnode.data.groupid, true);
	    
	    if (cnode != null) {
		    this.getFolderselect().getSelectionModel().select(cnode);
	    }
    },
    /**
	 * @method
	 */

    onGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    component.up('layoutedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name + '('+record.data.indice+')');
	    component.up('layoutedit').down('hiddenfield#groupid').setValue(record.data.indice);
    },

});