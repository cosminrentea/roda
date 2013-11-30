Ext.define('RODAdmin.controller.cms.file.FileList', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.files.FileTree', 'cms.files.FileItem',  'cms.files.File', 'common.Audit'
    ],

    views : [
            'cms.files.Files', 'cms.files.Itemsview', 'cms.files.FileDetails', 'cms.files.filedetails.FileProperties',
            'cms.files.filedetails.SpecificProperties', 'cms.files.FileContextMenu', 'cms.files.FolderContextMenu',
            'cms.files.IconviewContextMenu', 'cms.files.FileWindow', 'cms.files.EditFileWindow',
            'cms.files.FolderWindow', 'common.AuditWindow', 'cms.files.filedetails.FileUsage'
    ],

    refs : [
            {
                ref : 'iconview',
                selector : 'itemsview grid#iconview'
            }, {
                ref : 'fileproperties',
                selector : 'fileproperties'
            }, {
                ref : 'filespecificpropertiespanel',
                selector : 'filespecificproperties'
            }, {
                ref : 'fileusagepanel',
                selector : 'fileusage'
            }, {
                ref : 'fdetailspanel',
                selector : 'cmsfiles panel#fdetailscontainer '
            },
    ],

    init : function(application) {
	    this.control({
	        "itemsview grid#iconview" : {
	            selectionchange : this.onFolderviewSelectionChange,
	            itemcontextmenu : this.onIconviewContextMenu
	        },
	        "iconviewcontextmenu menuitem#icdeletefile" : {
		        click : this.onDeleteFileClick
	        },
	        "iconviewcontextmenu menuitem#icEditFile" : {
		        click : this.onEditFileClick
	        },
	    });
    },

    onFolderviewSelectionChange : function(component, selected, event) {
	    console.log('folderviewselectionchange');
	    var record = selected[0];
	    var fdetails = this.getFdetailspanel();
	    var fileprop = this.getFileproperties();
	    var fileusage = this.getFileusagepanel();
	    var filespecificprop = this.getFilespecificpropertiespanel();

	    fdetails.setTitle(record.data.name);

	    if (record.data.filetype == 'folder') {
		    fileusage.collapse(true);
		    filespecificprop.collapse(true);
	    }
	    else {
		    console.log('else here');
		    fileusage.expand(true);
		    filespecificprop.expand(true);
		    var fileitemstore = Ext.StoreManager.get('cms.files.FileItem');
		    fileitemstore.load({
		        id : record.data.indice, // set the id here
		        scope : this,
		        callback : function(records, operation, success) {
			        if (success) {
				        var fileitem = fileitemstore.first();
				        // console.log(fileitem);
				        fileusage.bindStore(fileitem.fileusageStore);
				        filespecificprop.bindStore(fileitem.filepropertiesStore);
				        fileprop.update(fileitem);
			        }
		        }
		    });

	    }

    },
    onIconviewContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
	    if (!this.filemenu) {
		    this.filemenu = Ext.create('widget.iconviewcontextmenu');
	    }
	    this.filemenu.showAt(e.getXY());
    },

    onDeleteFileClick : function(component, event) {
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var store = Ext.StoreManager.get('cms.files.File');
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the ' + currentNode.data.filetype + ' '
	            + currentNode.data.name + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    Ext.Ajax.request({
			        url : 'http://roda.apiary.io/admin/cms/filedrop',
			        method : "POST",
			        params : {
				        fileid : currentNode.data.indice
			        },
			        success : function() {
				        RODAdmin.util.Alert.msg('Success!', 'File deleted.');
				        store.load;
			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
	    event.stopEvent();
    },

    onEditFileClick : function(component, record, item, index, e) {
	    console.log('onEditFileClick');
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    var win = Ext.create('RODAdmin.view.cms.files.EditFileWindow');

	    win.setTitle('Edit File "' + currentNode.data.name + '" (id: ' + currentNode.data.id + ')');
	    win.setIconCls('folder_add');
	    var wtree = win.down('treepanel');
	    win.show();

	    var fileitemstore = Ext.create('RODAdmin.store.cms.files.FileItem');

	    fileitemstore.load({
	        // id: id, //set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var fileitem = fileitemstore.first();
			        win.down('form').down('fieldset').query('displayfield')[0].setValue(fileitem.data.filename);
			        win.down('form').down('fieldset').query('displayfield')[1].setValue(fileitem.data.fileurl);
			        win.down('form').down('fieldset').query('textfield')[0].setValue(fileitem.data.alias);
			        win.down('form').down('fieldset').query('hiddenfield')[0].setValue(fileitem.data.id);
			        win.down('form').down('fieldset').query('hiddenfield')[1].setValue(fileitem.data.id);
		        }
	        }
	    });
    },

});