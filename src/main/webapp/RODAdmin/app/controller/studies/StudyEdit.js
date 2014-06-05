/**
 * 
 */
Ext.define('RODAdmin.controller.studies.StudyEdit', {
    extend : 'Ext.app.Controller',
    /**
	 * @cfg
	 */
    views : [
	    "RODAdmin.view.studies.EditStudyWindow",
	    "RODAdmin.view.studies.AddStudyToGroupWindow",
	    "RODAdmin.view.studies.GroupWindow"
    ],
    /**
	 * @cfg
	 */
    refs : [
            {
                ref : 'itemsview',
                selector : 'studyitemsview'
            }, {
                ref : "folderview",
                selector : "studyitemsview treepanel#stfolderview"
            }, {
                ref : 'folderselect',
                selector : 'studyedit treepanel#groupselect'
            }, {
                ref : 'gfolderselect',
                selector : 'catalogedit treepanel#groupselect'
            }, {
                ref : 'groupedit',
                selector : 'catalogedit'
            }
    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
	        "studyedit treepanel#groupselect" : {
	        	 /**
				 * @listener groupselect-load triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
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
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
				 *           executes {@link #onGroupselectCellClick}  
				 */
	            cellclick : this.onGroupselectCellClick
	        },
	        "studyedit button#save" : {
	        	 /**
				 * @listener button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} button#save    
				 *           executes {@link #onStudyEditSaveClick}  
				 */	        	
		        click : this.onStudyEditSaveClick
	        },
	        
	        
	        "catalogedit treepanel#groupselect" : {
	        	 /**
				 * @listener groupselect-load triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
				 *           executes {@link #folderLoad}  
				 */
	            load : this.GfolderLoad, // this is the only event fired
	            // after loading the store in a
	            // tree view, apparently. This
	            // is REALLY stupid because it
	            // is probabily fired multiple
	            // times.
	        	 /**
				 * @listener groupselect-cellclick triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} treepanel#groupselect    
				 *           executes {@link #onGroupselectCellClick}  
				 */
	            cellclick : this.onGGroupselectCellClick
	        },
	        "catalogedit button#save" : {
	        	 /**
				 * @listener button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.EditStudyWindow EditStudyWindow} button#save    
				 *           executes {@link #onStudyEditSaveClick}  
				 */	        	
		        click : this.onCatalogEditSaveClick
	        },
	        "studygadd button#save" : {
	        	 /**
				 * @listener studygadd-button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.AddStudyToGroupWindow AddStudyToGroupWindow} button#save    
				 *           executes {@link #onStudyAddGroupSaveClick}  
				 */	
	        	click : this.onStudyAddGroupSaveClick
	        },
	        "stgroupadd button#save" : {
	        	 /**
				 * @listener stgroupadd-button-save-click triggered-by:
				 *           {@link RODAdmin.view.studies.GroupWindow GroupWindow} button#save    
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
		        url : '/roda/j/admin/catalogsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Study group saved.');
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadTreeClick();
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

    onStudyEditSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var bt = button;
	    var formPanel = win.down('form');
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var folderview = this.getFolderview()
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/studysave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Study saved.');
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadGridClick();
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

    onStudyAddGroupSaveClick : function(button, e, options) {
	    console.log('small step for man...');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/studysave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        console.log('closing');
				        console.log(win);
			        	RODAdmin.util.Alert.msg('Success!', 'Study saved on the server.');
				        // store.load();
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadTreeClick();
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
    
    onCatalogEditSaveClick : function(button, e, options) {
	    console.log('group save...');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : '/roda/j/admin/catalogsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        console.log('closing');
				        console.log(win);
			        	RODAdmin.util.Alert.msg('Success!', 'Study saved on the server.');
				        // store.load();
				        win.close();
				        var active = me.getItemsview().study.getActiveItem();
				        if (active.itemId == 'stfolderview') {
					        me.getController('RODAdmin.controller.studies.StudyTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'sticonview') {
					        me.getController('RODAdmin.controller.studies.StudyList').onReloadTreeClick();
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
	    var active = this.getItemsview().study.getActiveItem();
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

    GfolderLoad : function(a, b, c, d, e, f) {
    	
    	console.log('gfolderload');
//    	var groupid = this.getGroupedit().down('form').query('hiddenfield[name=group]').value;
//
//    	console.log(this.getGroupedit().down('form').query('hiddenfield[name=group]'));
//	    console.log(groupid);
	    var active = this.getItemsview().study.getActiveItem();
	    console.log(active);
	    var pnode = active.getSelectionModel().getLastSelected();
	    var rnode = this.getGfolderselect().getRootNode();
	    console.log(pnode);
	    var cnode = rnode.findChild('indice', pnode.data.groupid, true);
	    console.log(cnode);	    
	    if (cnode != null) {
		    this.getGfolderselect().getSelectionModel().select(cnode);
	    } else {
	    	this.getGfolderselect().getSelectionModel().select(rnode);
	    }
    },
    
    /**
	 * @method
	 */

    onGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    component.up('studyedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name + '('+record.data.indice+')');
	    component.up('studyedit').down('hiddenfield#groupid').setValue(record.data.indice);
    },

    onGGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	
	    component.up('catalogedit').down('form').query('displayfield')[0].setValue(record.data.name + '('+record.data.indice+')');
	    
	    
	    
	    component.up('catalogedit').down('hiddenfield#groupid').setValue(record.data.indice);
    },
    
    
    
});