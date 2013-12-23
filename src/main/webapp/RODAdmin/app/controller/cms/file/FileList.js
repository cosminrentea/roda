/**
 * 
 */
Ext.define('RODAdmin.controller.cms.file.FileList', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.files.FileTree',
            'cms.files.FileItem',
            'cms.files.File',
            'common.Audit'
    ],

    views : [
            'RODAdmin.view.cms.files.Files',
            'RODAdmin.view.cms.files.Itemsview',
            'RODAdmin.view.cms.files.FileDetails',
            'RODAdmin.view.cms.files.filedetails.FileProperties',
            'RODAdmin.view.cms.files.filedetails.SpecificProperties',
//            'RODAdmin.view.cms.files.FileContextMenu',
//            'RODAdmin.view.cms.files.FolderContextMenu',
            'RODAdmin.view.cms.files.IconviewContextMenu',
            'RODAdmin.view.cms.files.FileWindow',
            'RODAdmin.view.cms.files.EditFileWindow',
            'RODAdmin.view.cms.files.FolderWindow',
            'RODAdmin.view.common.AuditWindow',
            'RODAdmin.view.cms.files.filedetails.FileUsage'
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
/**
 * @method
 */
    init : function(application) {
	    this.control({
	        "itemsview grid#iconview" : {
	            /**
				 * @listener itemsview-grid-iconview-selectionchange triggered-by:
				 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
				 *           grid#iconview
				 *           {@link #onFolderviewSelectionChange}
				 */	
	            selectionchange : this.onFolderviewSelectionChange,
	            /**
				 * @listener itemsview-grid-iconview-itemscontextmenu triggered-by:
				 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
				 *           grid#iconview
				 *           {@link #onIconviewContextMenu}
				 */	
	            itemcontextmenu : this.onIconviewContextMenu
	        },
	        "iconviewcontextmenu menuitem#icdeletefile" : {
	            /**
				 * @listener iconviewcontextmenu-menuitem-icdeletefile-click triggered-by:
				 *           {@link RODAdmin.view.cms.files.IconviewContextMenu IconviewContextMenu}
				 *           menuitem#icdeletefile
				 *           {@link #onDeleteFileClick}
				 */	
		        click : this.onDeleteFileClick
	        },
	        "iconviewcontextmenu menuitem#icEditFile" : {
	            /**
				 * @listener iconviewcontextmenu-menuitem-icEditFile-click triggered-by:
				 *           {@link RODAdmin.view.cms.files.IconviewContextMenu IconviewContextMenu}
				 *           menuitem#icEditFile
				 *           {@link #onDeleteFileClick}
				 */	
		        click : this.onDeleteFileClick
	        },
	    });
    },
    /**
	 * @method
	 */
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
    /**
	 * @method
	 */
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
    /**
	 * @method
	 */
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