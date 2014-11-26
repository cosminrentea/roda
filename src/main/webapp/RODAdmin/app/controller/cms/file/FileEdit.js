/**
 * 
 */
Ext.define('RODAdmin.controller.cms.file.FileEdit', {
    extend : 'Ext.app.Controller',

    views : [
	    "RODAdmin.view.cms.files.EditFileWindow",
	    "RODAdmin.view.cms.files.FileWindow"
    ],

    refs : [
            {
                ref : 'itemsview',
                selector : 'itemsview'
            }, {
                ref : 'filetree',
                selector : 'itemsview treepanel#folderview'
            }, {
                ref : 'folderselect',
                selector : 'fileedit treepanel#folderselect'
            },
    ],
/**
 * @method
 */
    init : function(application) {
	    this.control({
	        "fileedit treepanel#folderselect" : {
	            /**
				 * @listener fileedit-treepanel-folderselect-load triggered-by:
				 *           {@link RODAdmin.view.cms.files.EditFileWindow EditFileWindow}
				 *           treepanel#folderselect  
				 *           this is the only event fired after loading the store in a tree view, apparently. This
	             * 		  	 is REALLY stupid because it is probabily fired multiple times.  
				 *           {@link #folderLoad}
				 */	        	
	            load : this.folderLoad, // this is the only event fired
	            /**
				 * @listener fileedit-treepanel-folderselect-cellclick triggered-by:
				 *           {@link RODAdmin.view.cms.files.EditFileWindow EditFileWindow}
				 *           treepanel#folderselect
				 *           {@link #onFolderselectCellClick}
				 */	

	            cellclick : this.onFolderselectCellClick
	        },
	        "fileadd button#save" : {
	            /**
				 * @listener fileadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.files.FileWindow FileWindow}
				 *           button#save
				 *           {@link #onButtonClickFileSave}
				 */	
	        	click : this.onButtonClickFileSave
	        },
	        "folderadd button#save" : {
	            /**
				 * @listener folderadd-button-save-click triggered-by:
				 *           {@link RODAdmin.view.cms.files.FolderWindow FolderWindow}
				 *           button#save
				 *           {@link #onButtonClickFolderSave}
				 */	
		        click : this.onButtonClickFolderSave
	        },
	        "fileedit cancelsave button#save" : {
	            /**
				 * @listener fileedit-cancelsave-button-save-click triggered-by:
				 *           {@link RODAdmin.view.cms.files.EditFileWindow EditFileWindow}
				 *           cancelsave button#save
				 *           {@link #fileEditSave}
				 */	
		        click : this.fileEditSave
	        }
	    // groupadd here
	    });
    },
    /**
	 * @method
	 */
    folderLoad : function(component, options) {
	    var pnode = this.getItemsview().getSelectionModel().getLastSelected();
	    var rnode = this.getFolderselect().getRootNode();
	    var cnode = rnode.findChild('id', pnode.data.folderid, true);
	    if (cnode != null) {
		    this.getFolderselect().getSelectionModel().select(cnode);
	    }
    },
    /**
	 * @method
	 */
    onFolderselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    component.up('fileedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name);
	    component.up('fileedit').down('fieldset').query('hiddenfield')[0].setValue(record.data.id);
    },
    /**
	 * @method
	 */
    onButtonClickFileSave : function(button, e, options) {
	    console.log('');
	    var win = button.up('fileadd');
	    form = win.down('form');
	    /**
	     * @todo StoreCall
	     * Apel intern catre store
	     * @todo Store 
	     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
	     */

	    var store = Ext.StoreManager.get('cms.files.FileTree');

	    if (form.getForm().isValid()) {

		    form.getForm().submit({
		        clientValidation : true,
		        method: 'POST',
		        url : RODAdmin.util.Globals.baseurl + '/adminjson/cmsfilesave',
		        success : function(form, action) {

			        var result = action.result;
			        if (result.success) {

				        RODAdmin.util.Alert.msg('Success!', 'File saved.');
				        store.load();
				        win.close();

			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
				        break;
			        case Ext.form.action.Action.CONNECT_FAILURE:
				        Ext.Msg.alert('Failure', 'Ajax communication failed');
				        break;
			        case Ext.form.action.Action.SERVER_INVALID:
				        Ext.Msg.alert('Failure', action.result.msg);
			        }
		        }
		    });
	    }
	    else {

	    }
    },
    /**
	 * @method
	 */
    onButtonClickFolderSave : function(button, e, options) {
	    var win = button.up('folderadd');
	    form = win.down('form');
	    var store = Ext.StoreManager.get('cms.files.FileTree');
	    if (form.getForm().isValid()) {

		    form.getForm().submit({
		        clientValidation : true,
		        method: 'POST',
		        url : RODAdmin.util.Globals.baseurl + '/adminjson/cmsfoldersave',
		        success : function(form, action) {

			        var result = action.result;
			        if (result.success) {

				        RODAdmin.util.Alert.msg('Success!', 'Folder saved.');
				        store.load();
				        win.close();

			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
				        break;
			        case Ext.form.action.Action.CONNECT_FAILURE:
				        Ext.Msg.alert('Failure', 'Ajax communication failed');
				        break;
			        case Ext.form.action.Action.SERVER_INVALID:
				        Ext.Msg.alert('Failure', action.result.msg);
			        }
		        }
		    });
	    }
	    else {

	    }
    },
    /**
	 * @method
	 */
    fileEditSave : function(button, e, options) {
	    console.log('');
	    var win = button.up('fileedit');
	    form = win.down('form');
	    var store = Ext.StoreManager.get('cms.files.File');

	    if (form.getForm().isValid()) {

		    form.getForm().submit({
		        clientValidation : true,
		        method: 'POST',
		        url : RODAdmin.util.Globals.baseurl + '/adminjson/cmsfoldersave',
		      
		        success : function(form, action) {

			        var result = action.result;
			        if (result.success) {

				        RODAdmin.util.Alert.msg('Success!', 'File saved.');
				        store.load();
				        win.close();

			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may not be submitted with invalid values');
				        break;
			        case Ext.form.action.Action.CONNECT_FAILURE:
				        Ext.Msg.alert('Failure', 'Ajax communication failed');
				        break;
			        case Ext.form.action.Action.SERVER_INVALID:
				        Ext.Msg.alert('Failure', action.result.msg);
			        }
		        }
		    });
	    }
	    else {

	    }
    }

});