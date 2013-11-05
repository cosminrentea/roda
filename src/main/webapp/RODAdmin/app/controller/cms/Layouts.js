Ext.define('RODAdmin.controller.cms.Layouts', {
	extend : 'Ext.app.Controller',

	stores : ['cms.layout.LayoutTree', 'cms.layout.LayoutItem',
			'cms.layout.LayoutGroup', 'cms.layout.Layout',
			// 'cms.layout.GroupTree',
			'common.Audit'],

	views : ['cms.layout.Layouts', 'cms.layout.LayoutItemsview',
			'cms.layout.LayoutDetails', 'cms.layout.details.LayoutProperties',
			"cms.layout.EditLayoutWindow",
			// 'cms.files.filedetails.SpecificProperties',
			 'cms.layout.LayoutContextMenu',
			 'cms.layout.LayoutGroupContextMenu',
			 'cms.layout.LayoutItemviewContextMenu',
			// 'cms.files.FileWindow',
			// 'cms.files.EditFileWindow',
			// 'cms.files.FolderWindow',
			// 'common.AuditWindow',
			'cms.layout.details.LayoutUsage'],

	refs : [{
				ref : 'lydetailspanel',
				selector : 'cmslayouts panel#lydetailscontainer '
			}, {
				ref : 'lyusagepanel',
				selector : 'layoutusage'
			},
			// {
			// ref : 'filespecificpropertiespanel',
			// selector : 'filespecificproperties'
			// }, {
			// ref : 'filetree',
			// selector : 'itemsview treepanel#folderview'
			// },
			{
				ref : 'layoutproperties',
				selector : 'layoutproperties'
			}, {
				ref : 'itemsview',
				selector : 'layoutitemsview'
			}, {
				ref : 'iconview',
				selector : 'layoutitemsview grid#lyiconview'
			},{
				ref : 'folderselect',
				selector : 'layoutedit treepanel#lyfolderview'
			}, {
				ref: "folderview",
				selector: "layoutitemsview treepanel#lyfolderview"
			}, {
				ref : 'folderselect',
				selector : 'layoutedit treepanel#groupselect'
			}

	// {
	// ref : 'itemsview',
	// selector : 'itemsview'
	// }, {
	// ref : 'iconview',
	// selector : 'itemsview grid#iconview'
	// }, {
	// ref : 'folderselect',
	// selector : 'fileedit treepanel#folderselect'
	// },{
	// ref: 'folderview',
	// selector : "itemsview treepanel#folderview"
	// }
	],

	init : function(application) {
		this.control({
					"layoutitemsview treepanel#lyfolderview" : {
						selectionchange : this.onLyFolderviewSelectionChange,
						itemcontextmenu : this.onTreeContextMenu
					},
					// "itemsview treepanel#folderview > treeview" : {
					// drop: this.onTreeDrop
					// },
					"layoutitemsview grid#lyiconview" : {
						selectionchange : this.onLyFolderviewSelectionChange,
						itemcontextmenu : this.onItemContextMenu
					},
					"cmslayouts toolbar button#icon-view" : {
						click : this.onIconViewClick
					},
					"cmslayouts toolbar button#tree-view" : {
						click : this.onTreeViewClick
					},
					"layoutproperties toolbar#lyproptoolbar button#editlayout" : {
						click : this.onlytoolbarEditClick
					},
					"layoutproperties toolbar#lyproptoolbar button#deletelayout" : {
						click : this.onlytoolbarDeleteClick
					},
					"layoutproperties toolbar#lyproptoolbar button#getlayoutaudit" : {
						click : this.onlytoolbarAuditClick
					},
					"layoutedit treepanel#folderselect" : {
						load : this.folderLoad, // this is the only event fired
						// after loading the store in a
						// tree view, apparently. This
						// is REALLY stupid because it
						// is probabily fired multiple
						// times.
						cellclick : this.onFolderselectCellClick
					},
					"layoutitemsview treepanel#lyfolderview toolbar button#reloadtree" : {
						click : this.onReloadTreeClick
					},
					"layoutitemsview treepanel#lyfolderview toolbar button#collapsetree" : {
						click : this.onCollapseTreeClick
					},
					"layoutitemsview treepanel#lyfolderview toolbar button#expandtree" : {
						click : this.onExpandTreeClick
					},
////////--------------------
					"layoutcontextmenu menuitem#deletelayout" : {
						click : this.onDeleteLayoutClick
					},
					"layoutcontextmenu menuitem#editlayout" : {
						click : this.onEditLayoutClick
					},
//					"layoutgroupcontextmenu menuitem#newgroup" : {
//						click : this.onNewGroupClick
//					},
//					"layoutgroupcontextmenu menuitem#addlayout" : {
//						click : this.onAddLayoutClick
//					},
//					"layoutgroupcontextmenu menuitem#emptygroup" : {
//						click : this.onEmptyGroupClick
//					},
//					"layoutgroupcontextmenu menuitem#deletegroup" : {
//						click : this.onDeleteGroupClick
//					},
					"layoutitemviewcontextmenu menuitem#icdeletelayout" : {
						click : this.onIcDeleteLayoutClick
					},
					"layoutitemviewcontextmenu menuitem#iceditlayout" : {
						click : this.onIcEditLayoutClick
					},					
///////--------------------					

				});
	},

	onIcEditLayoutClick : function(component, record, item, index, e) {
		console.log('onIcEditLayoutClick');
//		console.log(component);
		var currentNode = this.getIconview().getSelectionModel().getLastSelected();
		
//		console.log(currentNode);
		
		var win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
		win.setTitle('Edit Layout');
		var wtree = win.down('treepanel');

		var layoutitemstore = Ext.create('RODAdmin.store.cms.layout.LayoutItem');

	//		aici trebuie sa vedem daca putem sa legam formularul cu un store		
		layoutitemstore.load({
					// id: id, //set the id here
					scope : this,
					callback : function(records, operation, success) {
						if (success) {
//							console.log(records);
//							var fileitem = layoutitemstore.first();
							win.down('form').getForm().loadRecord(records.first());
						}
					}
		});	
		win.show();
//		win.down('form').getForm().loadRecord(fp);
	},	
	
	onIcDeleteLayoutClick : function(component, event) {
		var currentNode = this.getIconview().getSelectionModel().getLastSelected();
		var store = Ext.StoreManager.get('cms.layout.Layout');
		Ext.Msg.confirm('Delete Requirement',
				'Are you sure you want to delete the layout '
						+ currentNode.data.name + '?', function(id, value) {
					if (id === 'yes') {
						console.log('we will delete');
						Ext.Ajax.request({
									url : 'http://roda.apiary.io/admin/cms/layoutdrop',
									method : "POST",
									params : {
										layoutid : currentNode.data.indice
									},
									success : function() {
										RODAdmin.util.Alert.msg('Success!', 'Layout deleted.');										
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
	
	onDeleteLayoutClick : function(component, event) {
		var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
		var store = Ext.StoreManager.get('cms.layout.Layout');
		Ext.Msg.confirm('Delete Requirement',
				'Are you sure you want to delete the layout '
						+ currentNode.data.name + '?', function(id, value) {
					if (id === 'yes') {
						console.log('we will delete');
						Ext.Ajax.request({
									url : 'http://roda.apiary.io/admin/cms/layoutdrop',
									method : "POST",
									params : {
										layoutid : currentNode.data.indice
									},
									success : function() {
										RODAdmin.util.Alert.msg('Success!', 'Layout deleted.');										
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

	onEditLayoutClick : function(component, record, item, index, e) {
		console.log('onEditLayoutClick');
		var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
		
		console.log(currentNode);
		
		var win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
		win.setTitle('Edit Layout');
		var wtree = win.down('treepanel');

		var layoutitemstore = Ext.create('RODAdmin.store.cms.layout.LayoutItem');

	//		aici trebuie sa vedem daca putem sa legam formularul cu un store		
		layoutitemstore.load({
					// id: id, //set the id here
					scope : this,
					callback : function(records, operation, success) {
						if (success) {
//							var fileitem = fileitemstore.first();
							win.down('form').getForm().loadRecord(records);
						}
					}
		});	
		win.show();
//		win.down('form').getForm().loadRecord(fp);
	},
	
	
	
	onTreeContextMenu : function(component, record, item, index, e) {
		e.stopEvent();
		if (record.data.itemtype == 'layoutgroup') {
			if (!this.foldermenu) {
				this.groupmenu = Ext.create('widget.layoutgroupcontextmenu');
			}
			this.groupmenu.showAt(e.getXY());
		} else {
			if (!this.itemmenu) {
				this.itemmenu = Ext.create('widget.layoutcontextmenu');
			}
			this.itemmenu.showAt(e.getXY());
		}
	},
	
	onItemContextMenu : function(component, record, item, index, e) {
		e.stopEvent();
		if (!this.filemenu) {
			this.filemenu = Ext.create('widget.layoutitemviewcontextmenu');
		}
		this.filemenu.showAt(e.getXY());
	},

	
	onCollapseTreeClick : function(button, e, options) {
		console.log('onCollapseTreeClick');
		this.getFolderview().collapseAll();
	},

	onExpandTreeClick : function(button, e, options) {
		console.log('onExpandTreeClick');
		this.getFolderview().expandAll();
	},

	onReloadTreeClick : function(button, e, options) {
		console.log('onReloadTreeClick');
		var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
//		var currentNode = this.getFolderview().getSelectionModel().getSelectedIndex();
		console.log(currentNode);
		this.getFolderview().store.reload(
				{
					  scope:this,
					  callback:function(records, operation, success){
						  console.log('callback executed');
						  console.log(currentNode.idField.originalIndex);
//						  console.log(this.getFolderview().getSelectionModel());
						  this.getSelectionModel().select(currentNode);
					  }
					}		
		);
//		this.getFolderview().getSelectionModel().select(currentNode);
	},
	
	onFolderselectCellClick : function(component, td, cellIndex, record, tr,
			rowIndex, e, eOpts) {
		component.up('layoutedit').down('form').down('fieldset')
				.query('displayfield')[0].setValue(record.data.name);
		component.up('layoutedit').down('fieldset').query('hiddenfield')[0]
				.setValue(record.data.id);
	},
	
	
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
	
	
	onlytoolbarEditClick : function(button, e, options) {
		var fp = this.getLayoutproperties().data;
		var win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
		win.setTitle('Edit File "' + fp.data.name + '" (id: ' + fp.data.id
				+ ')');
		var wtree = win.down('treepanel');
		win.show();
		win.down('form').getForm().loadRecord(fp);
//		win.down('form').down('fieldset').query('displayfield')[0]
//				.setValue(fp.data.filename);
//		win.down('form').down('fieldset').query('displayfield')[1]
//				.setValue(fp.data.fileurl);
//		win.down('form').down('fieldset').query('textfield')[0]
//				.setValue(fp.data.alias);
//		win.down('form').down('fieldset').query('hiddenfield')[0]
//				.setValue(fp.data.id);
//		win.down('form').down('fieldset').query('hiddenfield')[1]
//				.setValue(fp.data.id);
	},

	onlytoolbarDeleteClick : function(button, e, options) {
		console.log('editfile clicked');
		var fp = this.getLayoutproperties().data;
		// var store = button.up('toolbar').up('layoutproperties').getStore()
		// var store = button.up('toolbar').up('layoutproperties');
		console.log(fp);

		Ext.Msg.confirm('Delete Requirement',
				'Are you sure you want to delete the ' + fp.data.itemtype + ' '
						+ fp.data.name + '?', function(id, value) {
					if (id === 'yes') {
						console.log('we will delete');
						var url = '';
						if (fp.data.itemtype == 'layoutgroup') {
							url = 'http://roda.apiary.io/admin/layout/groupdrop';
						} else {
							url = 'http://roda.apiary.io/admin/layout/layoutdrop';
						}

						Ext.Ajax.request({
							url : url,
							method : "POST",
							params : {
								fileid : fp.data.id
							},
							success : function() {
								RODAdmin.util.Alert.msg('Success!', 'Deleted.');
								Ext.StoreManager.get('cms.layout.Layout')
										.load();
								Ext.StoreManager.get('cms.layout.LayoutTree')
										.load();
								// store.load;
							},
							failure : function(response, opts) {
								Ext.Msg.alert('Failure', response);

							}
						});
					}
				}, this);
		// event.stopEvent();
	},

	onlytoolbarAuditClick : function(button, e, options) {
		console.log('auditfile clicked');
		var fp = this.getLayoutproperties().data;

		var win = Ext.create('RODAdmin.view.common.AuditWindow');
		win.setTitle('Audit data for "' + fp.data.name + '" (id: ' + fp.data.id
				+ ')');
		win.show();
		var auditgrid = win.down('grid[itemId=auditgrid]');
		auditgrid.store.load();
		console.log(auditgrid.store);
	},

	onIconViewClick : function(button, e, options) {
		console.log('onIconviewClick');
		this.getItemsview().layout.setActiveItem('lyiconview');
		var store = Ext.StoreManager.get('cms.layout.Layout');
		store.load();
	},

	onTreeViewClick : function(button, e, options) {
		console.log('onfolderviewClick');
		this.getItemsview().layout.setActiveItem('lyfolderview');
		var store = Ext.StoreManager.get('cms.layout.LayoutTree');
		store.load();
	},

	onLyFolderviewSelectionChange : function(component, selected, event) {
		console.log('folderviewselectionchange');
		var record = selected[0];
		var lyprop = this.getLayoutproperties();
		var lydetails = this.getLydetailspanel();
		var lyusage = this.getLyusagepanel();

		console.log(record.data.indice);

		lydetails.setTitle(record.data.name);

		if (record.data.itemtype == 'layoutgroup') {
			lyusage.collapse(true);
			console.log('layoutgroup');
			var lygroupstore = Ext.StoreManager.get('cms.layout.LayoutGroup');

			lygroupstore.load({
						scope : this,
						id: record.data.indice, //set the id here
						callback : function(records, operation, success) {
							if (success) {
								var lyitem = lygroupstore.first();
								lyprop.update(lyitem);
							}
						}
					});

		} else {
			lyusage.expand(true);
			var lyitemstore = Ext.StoreManager.get('cms.layout.LayoutItem');
			lyitemstore.load({
						id: record.data.indice, //set the id here
						scope : this,
						callback : function(records, operation, success) {
							if (success) {
								var lyitem = lyitemstore.first();
								lyprop.update(lyitem);
							//	console.log(lyitem.layoutusageStore);
								if (!typeof lyitem.layoutusageStore === 'undefined') {
									lyusage.bindStore(lyitem.layoutusageStore);
								}
							}
						}
					});
		}
	}
});