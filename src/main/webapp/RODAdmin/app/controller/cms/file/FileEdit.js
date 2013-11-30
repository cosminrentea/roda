Ext.define('RODAdmin.controller.cms.file.FileEdit', {
    extend : 'Ext.app.Controller',

    views : [
	    "cms.files.EditFileWindow"
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

    init : function(application) {
	    this.control({
	        "fileedit treepanel#folderselect" : {
	            load : this.folderLoad, // this is the only event fired
	            // after loading the store in a
	            // tree view, apparently. This
	            // is REALLY stupid because it
	            // is probabily fired multimple
	            // times.
	            cellclick : this.onFolderselectCellClick
	        },
	        "fileadd button#save" : {
		        click : this.onButtonClickFileSave
	        },
	        "folderadd button#save" : {
		        click : this.onButtonClickFolderSave
	        },
	        "fileedit cancelsave button#save" : {
		        click : this.fileEditSave
	        }
	    // groupadd here
	    });
    },

    folderLoad : function(component, options) {
	    var pnode = this.getIconview().getSelectionModel().getLastSelected();
	    var rnode = this.getFolderselect().getRootNode();
	    var cnode = rnode.findChild('id', pnode.data.folderid, true);
	    if (cnode != null) {
		    this.getFolderselect().getSelectionModel().select(cnode);
	    }
    },
    onFolderselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    component.up('fileedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name);
	    component.up('fileedit').down('fieldset').query('hiddenfield')[0].setValue(record.data.id);
    },

    onButtonClickFileSave : function(button, e, options) {
	    console.log('');
	    var win = button.up('fileadd');
	    form = win.down('form');
	    var store = Ext.StoreManager.get('cms.files.FileTree');

	    if (form.getForm().isValid()) {

		    form.getForm().submit({
		        clientValidation : true,
		        // method: 'POST',
		        // url : 'http://roda.apiary.io/admin/cms/filesave',
		        url : 'http://localhost/RODAdmin/php/saveFile.php',
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
    onButtonClickFolderSave : function(button, e, options) {
	    var win = button.up('folderadd');
	    form = win.down('form');
	    var store = Ext.StoreManager.get('cms.files.FileTree');
	    if (form.getForm().isValid()) {

		    form.getForm().submit({
		        clientValidation : true,
		        // method: 'POST',
		        url : 'http://roda.apiary.io/admin/cms/foldersave',
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

    fileEditSave : function(button, e, options) {
	    console.log('');
	    var win = button.up('fileedit');
	    form = win.down('form');
	    var store = Ext.StoreManager.get('cms.files.File');

	    if (form.getForm().isValid()) {

		    form.getForm().submit({
		        clientValidation : true,
		        // method: 'POST',
		        // url : 'http://roda.apiary.io/admin/cms/filesave',
		        url : 'http://localhost/RODAdmin/php/saveFile.php',
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