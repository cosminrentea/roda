Ext.define('RODAdmin.controller.cms.Files', {
	extend : 'Ext.app.Controller',

	stores : ['cms.files.FileTree', 'cms.files.FileItem', 'cms.files.File',
			'cms.files.FolderTree','common.Audit'],

	views : ['cms.files.Files', 'cms.files.Itemsview', 'cms.files.FileDetails',
			'cms.files.filedetails.FileProperties',
			'cms.files.filedetails.SpecificProperties',
			'cms.files.FileContextMenu', 'cms.files.FolderContextMenu',
			'cms.files.IconviewContextMenu', 'cms.files.FileWindow',
			'cms.files.EditFileWindow', 'cms.files.FolderWindow',
			'common.AuditWindow',
			'cms.files.filedetails.FileUsage'],

	refs : [{
				ref : 'fdetailspanel',
				selector : 'cmsfiles panel#fdetailscontainer '
			}, {
				ref : 'fileusagepanel',
				selector : 'fileusage'
			}, {
				ref : 'filespecificpropertiespanel',
				selector : 'filespecificproperties'
			}, {
				ref : 'filetree',
				selector : 'itemsview treepanel#folderview'
			}, {
				ref : 'fileproperties',
				selector : 'fileproperties'
			}, {
				ref : 'itemsview',
				selector : 'itemsview'
			}, {
				ref : 'iconview',
				selector : 'itemsview grid#iconview'
			}, {
				ref : 'folderselect',
				selector : 'fileedit treepanel#folderselect'
			},{
				ref: 'folderview',
				selector : "itemsview treepanel#folderview"
			}
			
			],

	init : function(application) {
		this.control({
					"itemsview treepanel#folderview" : {
						selectionchange: this.onFolderviewSelectionChange,
						itemcontextmenu : this.onContextMenu
					},
					"itemsview treepanel#folderview > treeview" : {
						drop: this.onTreeDrop
					},
					"itemsview grid#iconview" : {
						selectionchange : this.onFolderviewSelectionChange,
						itemcontextmenu : this.onIconviewContextMenu
					},
        					"itemsview grid#iconview toolbar button#showfilterdata" : {
						click : this.onShowFilterDataClick
					},
					"itemsview grid#iconview toolbar button#clearfilterdata" : {
						click : this.onClearFilterDataClick
					},
					"itemsview treepanel#folderview toolbar button#showfilterdata" : {
						click : this.onShowTreeFilterDataClick
					},
					"itemsview treepanel#folderview toolbar button#clearfilterdata" : {
						click : this.onClearTreeFilterDataClick
					},
					"itemsview treepanel#folderview toolbar button#reloadtree" : {
						click : this.onReloadTreeClick
					},
					"itemsview treepanel#folderview toolbar button#collapsetree" : {
						click : this.onCollapseTreeClick
					},
					"itemsview treepanel#folderview toolbar button#expandtree" : {
						click : this.onExpandTreeClick
					},
					"cmsfiles toolbar button#icon-view" : {
						click : this.onIconViewClick
					},
					"cmsfiles toolbar button#tree-view" : {
						click : this.onTreeViewClick
					},
					"filecontextmenu menuitem#deletefile" : {
						click : this.onDeleteFileClick
					},
					"filecontextmenu menuitem#EditFile" : {
						click : this.onEditFileClick
					},
					"foldercontextmenu menuitem#NewFolder" : {
						click : this.onNewFolderClick
					},
					"foldercontextmenu menuitem#AddFile" : {
						click : this.onAddFileClick
					},
					"foldercontextmenu menuitem#EmptyFolder" : {
						click : this.onEmptyFolderClick
					},
					"foldercontextmenu menuitem#DeleteFolder" : {
						click : this.onDeleteFolderClick
					},
					"iconviewcontextmenu menuitem#icdeletefile" : {
						click : this.onIcDeleteFileClick
					},
					"iconviewcontextmenu menuitem#icEditFile" : {
						click : this.onIcEditFileClick
					},
					"fileadd button#save" : {
						click : this.onButtonClickFileSave
					},
					"folderadd button#save" : {
						click : this.onButtonClickFolderSave
					},
					"fileedit treepanel#folderselect" : {
						load : this.folderLoad, // this is the only event fired
						// after loading the store in a
						// tree view, apparently. This
						// is REALLY stupid because it
						// is probabily fired multimple
						// times.
						cellclick : this.onFolderselectCellClick
					},
					"fileproperties toolbar#fileproptoolbar button#editfile" : {
						click: this.onfptoolbarEditClick
					}, 
					"fileproperties toolbar#fileproptoolbar button#deletefile" : {
						click: this.onfptoolbarDeleteClick
					}, 
					"fileproperties toolbar#fileproptoolbar button#getfileaudit" : {
						click: this.onfptoolbarAuditClick
					},
					"fileedit cancelsave button#save" : {
						click: this.fileEditSave					
					}
				});
	},


	onTreeDrop : function (node, data, overModel, dropPosition, eOpts) {
	 	console.log('first, were here');
		console.log(node);
		console.log(data);
		console.log(overModel);
		console.log(dropPosition);
		//din toate astea scoatem date pentru un url de tip changeparent
	},

	
	onfptoolbarEditClick : function(button, e, options) {
		var fp = this.getFileproperties().data;

		var win = Ext.create('RODAdmin.view.cms.files.EditFileWindow');
		win.setTitle('Edit File "' + fp.data.name + '" (id: '	+ fp.data.id + ')');
		var wtree = win.down('treepanel');
		win.show();
		win.down('form').down('fieldset').query('displayfield')[0].setValue(fp.data.filename);
		win.down('form').down('fieldset').query('displayfield')[1].setValue(fp.data.fileurl);
		win.down('form').down('fieldset').query('textfield')[0].setValue(fp.data.alias);
		win.down('form').down('fieldset').query('hiddenfield')[0].setValue(fp.data.id);
		win.down('form').down('fieldset').query('hiddenfield')[1].setValue(fp.data.id);
	},

	onfptoolbarDeleteClick : function(button, e, options) {
		console.log('editfile clicked');	
	},
	
	onfptoolbarAuditClick : function(button, e, options) {
		console.log('auditfile clicked');	
		var fp = this.getFileproperties().data;	
		
		var win = Ext.create('RODAdmin.view.common.AuditWindow');
		win.setTitle('Audit data for "' + fp.data.filename + '" (id: '	+ fp.data.id + ')');
		win.show();		
		var auditgrid = win.down('grid[itemId=auditgrid]');
		auditgrid.store.load();
		console.log(auditgrid.store);
		},

	
	onFolderselectCellClick : function(component, td, cellIndex, record, tr,
			rowIndex, e, eOpts) {
		component.up('fileedit').down('form').down('fieldset')
				.query('displayfield')[0].setValue(record.data.name);
		component.up('fileedit').down('fieldset').query('hiddenfield')[0]
				.setValue(record.data.id);
	},

//this is the fuckin place. Problema e asa: ne trebuie pnode
//	phode se obtine din componentul de unde s-a clickuit
//	acum insa nu stim care e ala
//	deci trebuie sa trimitem id-ul din functia de click
// pentru asta trebuie sa invatam cum sa definim proprietati in extjs si sa le setam la runtime	
	
	folderLoad : function(component, options) {
		var pnode = this.getIconview().getSelectionModel().getLastSelected();

		
		//		var pnode = this.getCurrentNode();	
//		var gwin =Ext.ComponentQuery.query('fileedit')		
//		var gwin = Ext.getCmp('fileedit');
//		console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');
//		console.log(gwin);
//		console.log(gwin.cnode);
//		console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');
//	var pnode = gwin.getCnode();
//		console.log(pnode);
//		console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');
		
		var rnode = this.getFolderselect().getRootNode();
		var cnode = rnode.findChild('id', pnode.data.folderid, true);
		if (cnode != null) {
			this.getFolderselect().getSelectionModel().select(cnode);
		}
	},

	onCollapseTreeClick : function(button, e, options) {
		console.log('onCollapseTreeClick');
		this.getFiletree().collapseAll();
	},

	onExpandTreeClick : function(button, e, options) {
		console.log('onExpandTreeClick');
		this.getFiletree().expandAll();
	},

	onReloadTreeClick : function(button, e, options) {
//		console.log('onReloadTreeClick');
		var currentNode = this.getFiletree().getSelectionModel()
				.getLastSelected();
		this.getFiletree().store.reload();
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
		} else {
			console.log('else here');
			fileusage.expand(true);
			filespecificprop.expand(true);
			var fileitemstore = Ext.StoreManager.get('cms.files.FileItem');
			fileitemstore.load({
						id: record.data.indice, //set the id here
						scope : this,
						callback : function(records, operation, success) {
							if (success) {
								var fileitem = fileitemstore.first();
//								console.log(fileitem);
								fileusage.bindStore(fileitem.fileusageStore);
								filespecificprop.bindStore(fileitem.filepropertiesStore);
								fileprop.update(fileitem);
							}
						}
					});

		}

	},

	onShowFilterDataClick : function(button, e, options) {
		console.log('onShowFilterDataClick');
		var grid = this.getIconview();
		var data = Ext.encode(grid.filters.getFilterData());
		Ext.Msg.alert('All Filter Data', data);
	},
	onClearFilterDataClick : function(button, e, options) {
		console.log('onClearFilterDataClick');
		var grid = this.getIconview();
		grid.filters.clearFilters();
	},

	onShowTreeFilterDataClick : function(button, e, options) {
		var tree = this.getFolderview();
		var data = Ext.encode(tree.filters.getFilterData());
		Ext.Msg.alert('All Filter Data', data);
	},
	onClearTreeFilterDataClick : function(button, e, options) {
		var tree = this.getFolderview();
		console.log(tree);
		tree.clearFilter();
	},
	
	onIconViewClick : function(button, e, options) {
		this.getItemsview().layout.setActiveItem('iconview');
		var store = Ext.StoreManager.get('cms.files.File');
		store.load();
	},

	onTreeViewClick : function(button, e, options) {
		this.getItemsview().layout.setActiveItem('folderview');
	},

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
									url : 'http://roda.apiary.io/admin/cms/filedrop',
									method : "POST",
									params : {
										fileid : currentNode.data.indice
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
		event.stopEvent();
	},

	onEditFileClick : function(component, record, item, index, e) {
		console.log('onEditFileClick');
		var currentNode = this.getFiletree().getSelectionModel().getLastSelected();
		
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

	onIcDeleteFileClick : function(component, event) {
		var currentNode = this.getIconview().getSelectionModel()
				.getLastSelected();
		console.log(currentNode);
		var store = Ext.StoreManager.get('cms.files.File');
		Ext.Msg.confirm('Delete Requirement',
				'Are you sure you want to delete the '
						+ currentNode.data.filetype + ' '
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

	onIcEditFileClick : function(component, record, item, index, e) {
		console.log('onEditFileClick');
		var currentNode = this.getIconview().getSelectionModel()
				.getLastSelected();
		var win = Ext.create('RODAdmin.view.cms.files.EditFileWindow');

		win.setTitle('Edit File "' + currentNode.data.name + '" (id: '
				+ currentNode.data.id + ')');
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

	onNewFolderClick : function(component, event) {
		var currentNode = this.getFiletree().getSelectionModel()
				.getLastSelected();
		var win = Ext.create('RODAdmin.view.cms.files.FolderWindow');
		win.setTitle('Add a new subfolder to "' + currentNode.data.name
				+ '" (id: ' + currentNode.data.id + ')');
		win.setIconCls('folder_add');
		win.down('form').down('fieldset').down('hiddenfield')
				.setValue(currentNode.data.id);
		// letse.setValue(currentNode.data.id);
		win.show();
	},

	onAddFileClick : function(component, event) {
		var currentNode = this.getFiletree().getSelectionModel()
				.getLastSelected();
		var win = Ext.create('RODAdmin.view.cms.files.FileWindow');
		win.setTitle('Add a new file to "' + currentNode.data.name + '" (id: '
				+ currentNode.data.id + ')');
		win.setIconCls('file_add');
		win.down('form').down('fieldset').down('hiddenfield')
				.setValue(currentNode.data.id);
		win.show();
	},

	onEmptyFolderClick : function(component, event) {
		console.log('onEmptyFolderClick');
		var currentNode = this.getFiletree().getSelectionModel()
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
	onDeleteFolderClick : function(component, record, item, index, e) {
		var currentNode = this.getFiletree().getSelectionModel()
				.getLastSelected();
		var store = Ext.StoreManager.get('cms.files.FileTree');
		Ext.Msg.confirm('Delete Folder',
				'Are you sure you want to delete the folder '
						+ currentNode.data.name + '?', function(id, value) {
					if (id === 'yes') {
						Ext.Ajax.request({
									url : 'http://roda.apiary.io/admin/cms/folderdrop',
									method : "POST",
									params : {
										folderid : currentNode.data.id
									},
									success : function() {
										RODAdmin.util.Alert.msg('Success!', 'Folder deleted.');
//										console.log("ok");
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

	onIconviewContextMenu : function(component, record, item, index, e) {
		e.stopEvent();
		if (!this.filemenu) {
			this.filemenu = Ext.create('widget.iconviewcontextmenu');
		}
		this.filemenu.showAt(e.getXY());
	},

	onFolderviewClick : function(component, td, cellIndex, record, tr,
			rowIndex, e, eOpts) {
		var fdetails = this.getFdetailspanel();
		var fileprop = this.getFileproperties();
		var fileusage = this.getFileusagepanel();
		var filespecificprop = this.getFilespecificpropertiespanel();

		fdetails.setTitle(record.data.name);

		if (record.data.filetype == 'folder') {
			fileusage.collapse(true);
			filespecificprop.collapse(true);
		} else {
			console.log('else here');
			fileusage.expand(true);
			filespecificprop.expand(true);
			var fileitemstore = Ext.StoreManager.get('cms.files.FileItem');
			fileitemstore.load({
						// id: id, //set the id here
						scope : this,
						callback : function(records, operation, success) {
							if (success) {
								var fileitem = fileitemstore.first();
								console.log(fileitem);
								fileusage.bindStore(fileitem.fileusageStore);
								filespecificprop.bindStore(fileitem.filepropertiesStore);
								fileprop.update(fileitem);
							}
						}
					});

		}
	},

	onButtonClickFileSave : function(button, e, options) {
		console.log('');
		var win = button.up('fileadd');
		form = win.down('form');
		var store = Ext.StoreManager.get('cms.files.FileTree');

		if (form.getForm().isValid()) {

			form.getForm().submit({
				clientValidation : true,
//				method: 'POST',
//				url : 'http://roda.apiary.io/admin/cms/filesave',
				url : 'http://localhost/RODAdmin/php/saveFile.php',
				success : function(form, action) {

					var result = action.result;
					if (result.success) {

						RODAdmin.util.Alert.msg('Success!', 'File saved.');
						store.load();
						win.close();

					} else {
						RODAdmin.util.Util.showErrorMsg(result.msg);
					}
				},
				failure : function(form, action) {
					switch (action.failureType) {
						case Ext.form.action.Action.CLIENT_INVALID :
							Ext.Msg
									.alert('Failure',
											'Form fields may not be submitted with invalid values');
							break;
						case Ext.form.action.Action.CONNECT_FAILURE :
							Ext.Msg.alert('Failure',
									'Ajax communication failed');
							break;
						case Ext.form.action.Action.SERVER_INVALID :
							Ext.Msg.alert('Failure', action.result.msg);
					}
				}
			});
		} else {

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
//				method: 'POST',
//				url : 'http://roda.apiary.io/admin/cms/filesave',
				url : 'http://localhost/RODAdmin/php/saveFile.php',
				success : function(form, action) {

					var result = action.result;
					if (result.success) {

						RODAdmin.util.Alert.msg('Success!', 'File saved.');
						store.load();
						win.close();

					} else {
						RODAdmin.util.Util.showErrorMsg(result.msg);
					}
				},
				failure : function(form, action) {
					switch (action.failureType) {
						case Ext.form.action.Action.CLIENT_INVALID :
							Ext.Msg
									.alert('Failure',
											'Form fields may not be submitted with invalid values');
							break;
						case Ext.form.action.Action.CONNECT_FAILURE :
							Ext.Msg.alert('Failure',
									'Ajax communication failed');
							break;
						case Ext.form.action.Action.SERVER_INVALID :
							Ext.Msg.alert('Failure', action.result.msg);
					}
				}
			});
		} else {

		}
	},
	
	
	onButtonClickFolderSave : function(button, e, options) {
		var win = button.up('folderadd');
		form = win.down('form');
		var store = Ext.StoreManager.get('cms.files.FileTree');
		if (form.getForm().isValid()) {

			form.getForm().submit({
				clientValidation : true,
//				method: 'POST',
				url : 'http://roda.apiary.io/admin/cms/foldersave',
				success : function(form, action) {

					var result = action.result;
					if (result.success) {

						RODAdmin.util.Alert.msg('Success!', 'Folder saved.');
						store.load();
						win.close();

					} else {
						RODAdmin.util.Util.showErrorMsg(result.msg);
					}
				},
				failure : function(form, action) {
					switch (action.failureType) {
						case Ext.form.action.Action.CLIENT_INVALID :
							Ext.Msg
									.alert('Failure',
											'Form fields may not be submitted with invalid values');
							break;
						case Ext.form.action.Action.CONNECT_FAILURE :
							Ext.Msg.alert('Failure',
									'Ajax communication failed');
							break;
						case Ext.form.action.Action.SERVER_INVALID :
							Ext.Msg.alert('Failure', action.result.msg);
					}
				}
			});
		} else {

		}
	}
});