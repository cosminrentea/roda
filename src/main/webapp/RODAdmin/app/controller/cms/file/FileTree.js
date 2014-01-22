/**
 * 
 */
Ext.define('RODAdmin.controller.cms.file.FileTree', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.files.FileTree',
            'cms.files.FileItem',
            'cms.files.File',
            'cms.files.Folder',
            'common.Audit'
    ],

	views : ['RODAdmin.view.cms.files.Files',
	         'RODAdmin.view.cms.files.Itemsview',
	         'RODAdmin.view.cms.files.FileDetails',
  			 'RODAdmin.view.cms.files.filedetails.FileProperties',
			 'RODAdmin.view.cms.files.filedetails.SpecificProperties',
			 'RODAdmin.view.cms.files.FileContextMenu',
			 'RODAdmin.view.cms.files.FolderContextMenu',
			 'RODAdmin.view.cms.files.IconviewContextMenu',
			 'RODAdmin.view.cms.files.FileWindow',
			 'RODAdmin.view.cms.files.EditFileWindow',
			 'RODAdmin.view.cms.files.FolderWindow',
			 'RODAdmin.view.common.AuditWindow',
			 'RODAdmin.view.cms.files.filedetails.FileUsage'],    
    

	refs : [{
		ref : 'fdetailspanel',
		selector : 'cmsfiles panel#fdetailscontainer '
	}, {
		ref : 'fileusagepanel',
		selector : 'fileusage'
	}, {
		ref : 'fileproperties',
		selector : 'fileproperties'
	}, {
        ref : 'filespecificpropertiespanel',
        selector : 'filespecificproperties'
    },{
		ref : 'folderselect',
		selector : 'fileedit treepanel#folderselect'
	},{
		ref: 'folderview',
		selector : "itemsview treepanel#folderview"
	}
	
	],    
/**
 * @method
 */    
	init : function(application) {
		this.control({
					"itemsview treepanel#folderview" : {
			            /**
						 * @listener itemsview-treepanel-folderview-selectionchange triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview  
						 *           {@link #onFolderviewSelectionChange}
						 */	        	
						selectionchange: this.onFolderviewSelectionChange,
			            /**
						 * @listener itemsview-treepanel-folderview-itemcontextmenu triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview  
						 *           {@link #onContextMenu}
						 */	        	
						itemcontextmenu : this.onContextMenu
					},
					"itemsview treepanel#folderview > treeview" : {
			            /**
						 * @listener itemsview-treepanel-folderview-treeview-drop triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview > treeview  
						 *           {@link #onTreeDrop}
						 */	        	
						drop: this.onTreeDrop
					},
					"itemsview treepanel#folderview toolbar button#showfilterdata" : {
			            /**
						 * @listener itemsview-treepanel-folderview-toolbar-button-showfilterdata-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview toolbar button#showfilterdata
						 *           {@link #onShowTreeFilterDataClick}
						 */	        	
						click : this.onShowTreeFilterDataClick
					},
					"itemsview treepanel#folderview toolbar button#clearfilterdata" : {
			            /**
						 * @listener itemsview-treepanel-folderview-toolbar-button-clearfilterdata-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview toolbar button#clearfilterdata
						 *           {@link #onClearTreeFilterDataClick}
						 */	        	
						click : this.onClearTreeFilterDataClick
					},
					"itemsview treepanel#folderview toolbar button#reloadtree" : {
			            /**
						 * @listener itemsview-treepanel-folderview-toolbar-button-reloadtree-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview toolbar button#reloadtree
						 *           {@link #onReloadTreeClick}
						 */	        	
						click : this.onReloadTreeClick
					},
					"itemsview treepanel#folderview toolbar button#collapsetree" : {
			            /**
						 * @listener itemsview-treepanel-folderview-toolbar-button-collapsetree-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview toolbar button#collapsetree
						 *           {@link #onCollapseTreeClick}
						 */	        	
						click : this.onCollapseTreeClick
					},
					"itemsview treepanel#folderview toolbar button#expandtree" : {
			            /**
						 * @listener itemsview-treepanel-folderview-toolbar-button-expandtree-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.Itemsview Itemsview}
						 *           treepanel#folderview toolbar button#expandtree
						 *           {@link #onExpandTreeClick}
						 */	        	
						click : this.onExpandTreeClick
					},
					"filecontextmenu menuitem#deletefile" : {
			            /**
						 * @listener filecontextmenu-menuitem-deletefile-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.FileContextMenu FileContextMenu}
						 *           menuitem#deletefile
						 *           {@link #onDeleteFileClick}
						 */	        	
						click : this.onDeleteFileClick
					},
					"filecontextmenu menuitem#EditFile" : {
			            /**
						 * @listener filecontextmenu-menuitem-EditFile-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.FileContextMenu FileContextMenu}
						 *           menuitem#EditFile
						 *           {@link #onEditFileClick}
						 */	        	
						click : this.onEditFileClick
					},
					"foldercontextmenu menuitem#NewFolder" : {
			            /**
						 * @listener foldercontextmenu-menuitem-NewFolder-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.FolderContextMenu FolderContextMenu}
						 *           menuitem#NewFolder
						 *           {@link #onNewFolderClick}
						 */	        	
						click : this.onNewFolderClick
					},
					"foldercontextmenu menuitem#AddFile" : {
			            /**
						 * @listener foldercontextmenu-menuitem-AddFile-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.FolderContextMenu FolderContextMenu}
						 *           menuitem#AddFile
						 *           {@link #onAddFileClick}
						 */	        	
						click : this.onAddFileClick
					},
					"foldercontextmenu menuitem#EmptyFolder" : {
			            /**
						 * @listener foldercontextmenu-menuitem-EmptyFolder-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.FolderContextMenu FolderContextMenu}
						 *           menuitem#EmptyFolder
						 *           {@link #onEmptyFolderClick}
						 */	        	
						click : this.onEmptyFolderClick
					},
					"foldercontextmenu menuitem#DeleteFolder" : {
			            /**
						 * @listener foldercontextmenu-menuitem-DeleteFolder-click triggered-by:
						 *           {@link RODAdmin.view.cms.files.FolderContextMenu FolderContextMenu}
						 *           menuitem#DeleteFolder
						 *           {@link #onDeleteFolderClick}
						 */	        	
						click : this.onDeleteFolderClick
					},
				});
	},
    /**
	 * @method
	 */    
	onTreeDrop : function (node, data, overModel, dropPosition, eOpts) {
	 	console.log('first, were here');
		console.log(node);
		console.log(data);
		console.log(overModel);
		console.log(dropPosition);
		//din toate astea scoatem date pentru un url de tip changeparent
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

		    var folderstore = this.getCmsFilesFolderStore();

		    folderstore.load({
		        scope : this,
		        id : record.data.indice, 
		        callback : function(records, operation, success) {
			        if (success) {
				        var item = folderstore.first();
				        fileprop.update(item);
			        }
		        }
		    });
			
			
			
		} else {
			console.log('else here');
			fileusage.expand(true);
			filespecificprop.expand(true);
			var fileitemstore = Ext.StoreManager.get('cms.files.FileItem');
			fileitemstore.load({
						id: record.data.indice, //set the id here
						scope : this,
						callback : function(records, operation, success) {
							console.log('callback in fileview');
							if (success) {
								var fileitem = fileitemstore.first();
								fileprop.update(fileitem);
								filespecificprop.bindStore(fileitem.filepropertiesStore);
								fileusage.bindStore(fileitem.fileusageStore);

							}
						}
					});

		}

	},
    /**
	 * @method
	 */
	onContextMenu : function(component, record, item, index, e) {
		e.stopEvent();
		if (record.data.filetype == 'folder') {
			if (!this.foldermenu) {
				this.foldermenu = Ext.create('widget.foldercontextmenu');
			}
			this.foldermenu.showAt(e.getXY());
		} else {
			if (!this.filemenu) {
				this.filemenu = Ext.create('widget.filecontextmenu');
			}
			this.filemenu.showAt(e.getXY());
		}
	},	
    /**
	 * @method
	 */
	onShowTreeFilterDataClick : function(button, e, options) {
		var tree = this.getFolderview();
		var data = Ext.encode(tree.filters.getFilterData());
		Ext.Msg.alert('All Filter Data', data);
	},
    /**
	 * @method
	 */
	onClearTreeFilterDataClick : function(button, e, options) {
		var tree = this.getFolderview();
		console.log(tree);
		tree.clearFilter();
	},
    /**
	 * @method
	 */
	onReloadTreeClick : function(button, e, options) {
//		console.log('onReloadTreeClick');
		var currentNode = this.getFiletree().getSelectionModel()
				.getLastSelected();
		this.getFiletree().store.reload();
	},	
    /**
	 * @method
	 */
	onCollapseTreeClick : function(button, e, options) {
		console.log('onCollapseTreeClick');
		this.getFiletree().collapseAll();
	},
    /**
	 * @method
	 */
	onExpandTreeClick : function(button, e, options) {
		console.log('onExpandTreeClick');
		this.getFiletree().expandAll();
	},
    /**
	 * @method
	 */
	onReloadTreeClick : function(button, e, options) {
//		console.log('onReloadTreeClick');
		var currentNode = this.getFiletree().getSelectionModel()
				.getLastSelected();
		this.getFiletree().store.reload();
	},
    /**
	 * @method
	 */
	onDeleteFileClick : function(component, event) {
		var currentNode = this.getFolderview().getSelectionModel()
				.getLastSelected();
		var store = Ext.StoreManager.get('cms.files.File');
		Ext.Msg.confirm('Delete Requirement',
				'Are you sure you want to delete the '
						+ currentNode.data.filetype + ' '
						+ currentNode.data.name + '?', function(id, value) {
					if (id === 'yes') {
						console.log('we will delete');
						Ext.Ajax.request({
									url : '/roda/admin/cmsfiledrop',
									method : "POST",
									params : {
										fileid : currentNode.data.indice
									},
									success : function(response) {
							           var responseJson = Ext.decode(response.responseText);
								            if (responseJson.success === true) {
								                // whatever stuff needs to happen on success
								            	RODAdmin.util.Alert.msg('Success!', 'File dropped.');
												store.load();
								            } else {
								            	RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);

								            }
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
		var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
		
//		console.log(currentNode);
		
		var win = Ext.create('RODAdmin.view.cms.files.EditFileWindow');

//		win.setCnode(currentNode);
//
//		var ss = win.getCnode();
//	
//		console.log('---------------------------------------------------------------------------------------------------------------');
//		console.log(ss);
//		console.log('---------------------------------------------------------------------------------------------------------------');

		
		win.setTitle('Edit File "' + currentNode.data.name + '" (id: '
				+ currentNode.data.id + ')');
		win.setIconCls('folder_add');
		var wtree = win.down('treepanel');
		win.show();
//
//		console.log(win);
		
		var fileitemstore = Ext.create('RODAdmin.store.cms.files.FileItem');

		fileitemstore.load({
					// id: id, //set the id here
					scope : this,
					callback : function(records, operation, success) {
						if (success) {
							var fileitem = fileitemstore.first();
							win.down('form').down('fieldset')
									.query('displayfield')[0]
									.setValue(fileitem.data.filename);
							win.down('form').down('fieldset')
									.query('displayfield')[1]
									.setValue(fileitem.data.fileurl);
							win.down('form').down('fieldset')
									.query('textfield')[0]
									.setValue(fileitem.data.alias);
							win.down('form').down('fieldset')
									.query('hiddenfield')[0]
									.setValue(fileitem.data.id);
							win.down('form').down('fieldset')
									.query('hiddenfield')[1]
									.setValue(fileitem.data.id);
						}
					}
				});		
	},
    /**
	 * @method
	 */
	onNewFolderClick : function(component, event) {
		var currentNode = this.getFolderview().getSelectionModel()
				.getLastSelected();
		var win = Ext.create('RODAdmin.view.cms.files.FolderWindow');
		win.setTitle('Add a new subfolder to "' + currentNode.data.name
				+ '" (id: ' + currentNode.data.indice + ')');
		win.setIconCls('folder_add');
		win.down('form').down('fieldset').down("hiddenfield[name='parent']").setValue(currentNode.data.indice);
		win.show();
	},
    /**
	 * @method
	 */
	onAddFileClick : function(component, event) {
		var currentNode = this.getFolderview().getSelectionModel()
				.getLastSelected();
		var win = Ext.create('RODAdmin.view.cms.files.FileWindow');
		win.setTitle('Add a new file to "' + currentNode.data.name + '" (id: '
				+ currentNode.data.indice + ')');
		win.setIconCls('file_add');
		win.down('form').down('fieldset').down("hiddenfield[name='folderid']").setValue(currentNode.data.indice);
		win.show();
	},
    /**
	 * @method
	 */
	onEmptyFolderClick : function(component, event) {
		console.log('onEmptyFolderClick');
		var currentNode = this.getFolderview().getSelectionModel()
				.getLastSelected();
		console.log(currentNode);		
		var store = Ext.StoreManager.get('cms.files.FileTree');
		Ext.Msg.confirm('Empty Folder',
				'Are you sure you want to empty the folder '
						+ currentNode.data.name + '?', function(id, value) {
					if (id === 'yes') {
						Ext.Ajax.request({
									url : 'http://roda.apiary.io/admin/cms/folderempty',
									method : "POST",
									params : {
										folderid : currentNode.data.indice
									},
									success : function() {
										RODAdmin.util.Alert.msg('Success!', 'Folder emptied.');
										store.load;
									},
									failure : function(response, opts) {
										Ext.Msg.alert('Failure', response);

									}
								});
					}
				}, this);
	//	event.stopEvent();

	},
    /**
	 * @method
	 */
	onDeleteFolderClick : function(component, record, item, index, e) {
		var currentNode = this.getFolderview().getSelectionModel()
				.getLastSelected();
		var store = Ext.StoreManager.get('cms.files.FileTree');
		Ext.Msg.confirm('Delete Folder',
				'Are you sure you want to delete the folder '
						+ currentNode.data.name + '?', function(id, value) {
					if (id === 'yes') {
						Ext.Ajax.request({
									url : '/roda/admin/cmsfolderdrop',
									method : "POST",
									params : {
										folderid : currentNode.data.indice
									},
									success : function() {
										RODAdmin.util.Alert.msg('Success!', 'Folder deleted.');
										console.log("ok");
										var store = Ext.StoreManager.get('cms.files.FileTree');
										console.log(store);
										store.load();
									},
									failure : function(response, opts) {
										Ext.Msg.alert('Failure', response);

									}
								});
					}
				}, this);
	//	event.stopEvent();

	},
	
	
});
    