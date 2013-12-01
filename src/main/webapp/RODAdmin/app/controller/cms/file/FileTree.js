Ext.define('RODAdmin.controller.cms.file.FileTree', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.files.FileTree', 'cms.files.FileItem', 'cms.files.File', 'cms.files.Folder',
            'common.Audit'
    ],

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
    
	init : function(application) {
		this.control({
					"itemsview treepanel#folderview" : {
						selectionchange: this.onFolderviewSelectionChange,
						itemcontextmenu : this.onContextMenu
					},
					"itemsview treepanel#folderview > treeview" : {
						drop: this.onTreeDrop
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
	onReloadTreeClick : function(button, e, options) {
//		console.log('onReloadTreeClick');
		var currentNode = this.getFiletree().getSelectionModel()
				.getLastSelected();
		this.getFiletree().store.reload();
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
	
	
});
    