Ext.define('RODAdmin.controller.cms.Snippets', {
    extend: 'Ext.app.Controller',

	stores : ['cms.snippet.SnippetTree', 'cms.snippet.SnippetItem',
				'cms.snippet.SnippetGroup', 'cms.snippet.Snippet',
				// 'cms.layout.GroupTree',
				'common.Audit'],    
    
    views: [
            'cms.snippet.Snippets', 'cms.snippet.SnippetItemsview',
            'cms.snippet.SnippetDetails', 'cms.snippet.details.SnippetProperties',
//            "cms.layout.EditSnippetWindow",
            'cms.snippet.SnippetGroupContextMenu',
            'cms.snippet.SnippetContextMenu',
// 'cms.files.filedetails.SpecificProperties',
// 'cms.files.FileContextMenu',
// 'cms.files.FolderContextMenu',
// 'cms.files.IconviewContextMenu',
// 'cms.files.FileWindow',
// 'cms.files.EditFileWindow',
// 'cms.files.FolderWindow',
// 'common.AuditWindow',
            'cms.snippet.details.SnippetUsage'
    ],
    
	refs : [{
		ref : 'sndetailspanel',
		selector : 'cmssnippets panel#sndetailscontainer '
	}, {
		ref : 'snusagepanel',
		selector : 'snippetusage'
	},
	// {
	// ref : 'filespecificpropertiespanel',
	// selector : 'filespecificproperties'
	// }, {
	// ref : 'filetree',
	// selector : 'itemsview treepanel#folderview'
	// },
	{
		ref : 'snippetproperties',
		selector : 'snippetproperties'
	}, {
		ref : 'itemsview',
		selector : 'snippetitemsview'
	}, {
		ref : 'iconview',
		selector : 'snippetitemsview grid#sniconview'
	},{
		ref : 'folderselect',
		selector : 'snippetedit treepanel#folderselect'
	}
],
init : function(application) {
	this.control({
				"snippetitemsview treepanel#snfolderview" : {
					selectionchange : this.onSnFolderviewSelectionChange,
					itemcontextmenu : this.onTreeContextMenu
				},
				// "itemsview treepanel#folderview > treeview" : {
				// drop: this.onTreeDrop
				// },
				"snippetitemsview grid#sniconview" : {
					selectionchange : this.onSnFolderviewSelectionChange
					// itemcontextmenu : this.onIconviewContextMenu
				},
				"cmssnippets toolbar button#icon-view" : {
					click : this.onIconViewClick
				},
				"cmssnippets toolbar button#tree-view" : {
					click : this.onTreeViewClick
				},
    
				
	});
},


onIconViewClick : function(button, e, options) {
	console.log('onIconviewClick');
	this.getItemsview().layout.setActiveItem('sniconview');
	var store = Ext.StoreManager.get('cms.snippet.Snippet');
	store.load();
},

onTreeViewClick : function(button, e, options) {
	console.log('onfolderviewClick');
	this.getItemsview().layout.setActiveItem('snfolderview');
	var store = Ext.StoreManager.get('cms.snippet.SnippetTree');
	store.load();
},

onTreeContextMenu : function(component, record, item, index, e) {
	e.stopEvent();
	if (record.data.itemtype == 'snippetgroup') {
		if (!this.foldermenu) {
			this.groupmenu = Ext.create('widget.snippetgroupcontextmenu');
		}
		this.groupmenu.showAt(e.getXY());
	} else {
		if (!this.itemmenu) {
			this.itemmenu = Ext.create('widget.snippetcontextmenu');
		}
		this.itemmenu.showAt(e.getXY());
	}
},

onSnFolderviewSelectionChange : function(component, selected, event) {
	console.log('folderviewselectionchange');
	var record = selected[0];
	console.log(record);
	var snprop = this.getSnippetproperties();
	var sndetails = this.getSndetailspanel();
	var snusage = this.getSnusagepanel();


	sndetails.setTitle(record.data.name);
console.log(record.data.itemtype);
	if (record.data.itemtype == 'snippetgroup') {
		snusage.collapse(true);
//		console.log('snippetgroup');
		var sngroupstore = Ext.StoreManager.get('cms.snippet.SnippetGroup');

		sngroupstore.load({
					scope : this,
					id: record.data.indice, 					
					callback : function(records, operation, success) {
						if (success) {
							var snitem = sngroupstore.first();
							snprop.update(snitem);
						}
					}
				});

	} else {
		snusage.expand(true);
		var snitemstore = Ext.StoreManager.get('cms.snippet.SnippetItem');
		snitemstore.load({
					id: record.data.indice, 
					scope : this,
					callback : function(records, operation, success) {
						if (success) {
							var snitem = snitemstore.first();
							snprop.update(snitem);
							snusage.bindStore(snitem.snippetusageStore);

						}
					}
				});
	}
}





});