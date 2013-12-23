/**
 * 
 */
Ext.define('RODAdmin.controller.cms.snippet.SnippetTree', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.snippet.SnippetTree',
            'cms.snippet.SnippetItem',
            'cms.snippet.SnippetGroup',
            'cms.snippet.Snippet',
            'common.Audit'
    ],

     views : [
     'RODAdmin.view.cms.snippet.SnippetGroupContextMenu',
     'RODAdmin.view.cms.snippet.SnippetItemviewContextMenu',
     'RODAdmin.view.cms.snippet.SnippetContextMenu',
     'RODAdmin.view.cms.snippet.SnippetItemsview'
    ],

    refs : [
            {
                ref : "folderview",
                selector : "snippetitemsview treepanel#snfolderview"
            },
            {
                ref : 'sndetailspanel',
                selector : 'cmssnippets panel#sndetailscontainer '
            }, {
                ref : 'snusagepanel',
                selector : 'snippetusage'
            }, {
                ref : 'snippetproperties',
                selector : 'snippetproperties'
            }, {
                ref : 'itemsview',
                selector : 'snippetitemsview'
            }, 
//            {
//                ref : 'iconview',
//                selector : 'snippetitemsview grid#sniconview'
//            },
            {
                ref : 'folderselect',
                selector : 'snippetedit treepanel#folderselect'
            }
    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
		    "snippetitemsview treepanel#snfolderview" : {
	            /**
				 * @listener snippetitemsview-treepanel-snfolderview-selectionchange triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetItemsview SnippetItemsview}
				 *           treepanel#snfolderview
				 *           {@link #onSnFolderviewSelectionChange}
				 */	
		    	selectionchange : this.onSnFolderviewSelectionChange,
	            /**
				 * @listener snippetitemsview-treepanel-snfolderview-itemcontextmenu triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetItemsview SnippetItemsview}
				 *           treepanel#snfolderview
				 *           {@link #onTreeContextMenu}
				 */	
		        itemcontextmenu : this.onTreeContextMenu
		    },
	        "snippetitemsview treepanel#snfolderview toolbar button#reloadtree" : {
	            /**
				 * @listener snippetitemsview-treepanel-snfolderview-toolbar-button-reloadtree-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetItemsview SnippetItemsview}
				 *           treepanel#snfolderview toolbar button#reloadtree
				 *           {@link #onReloadTreeClick}
				 */	
	        	click : this.onReloadTreeClick
	        },
	        "snippetitemsview treepanel#snfolderview toolbar button#collapsetree" : {
	            /**
				 * @listener snippetitemsview-treepanel-snfolderview-toolbar-button-collapsetree-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetItemsview SnippetItemsview}
				 *           treepanel#snfolderview toolbar button#collapsetree
				 *           {@link #onCollapseTreeClick}
				 */	
		        click : this.onCollapseTreeClick
	        },
	        "snippetitemsview treepanel#snfolderview toolbar button#expandtree" : {
	            /**
				 * @listener snippetitemsview-treepanel-snfolderview-toolbar-button-expandtree-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetItemsview SnippetItemsview}
				 *           treepanel#snfolderview toolbar button#expandtree
				 *           {@link #onExpandTreeClick}
				 */	
		        click : this.onExpandTreeClick
	        },		    
	        "snippetcontextmenu menuitem#deletesnippet" : {
	            /**
				 * @listener snippetcontextmenu-menuitem-deletesnippet-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetContextMenu SnippetContextMenu}
				 *           menuitem#deletesnippet
				 *           {@link #onDeleteSnippetClick}
				 */	
		        click : this.onDeleteSnippetClick
	        },
	        "snippetcontextmenu menuitem#editsnippet" : {
	            /**
				 * @listener snippetcontextmenu-menuitem-editsnippet-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetContextMenu SnippetContextMenu}
				 *           menuitem#editsnippet
				 *           {@link #onEditSnippetClick}
				 */	
	        	click : this.onEditSnippetClick
	        },
	        "snippetgroupcontextmenu menuitem#addsnippet" : {
	            /**
				 * @listener snippetgroupcontextmenu-menuitem-addsnippet-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetGroupContextMenu SnippetGroupContextMenu}
				 *           menuitem#addsnippet
				 *           {@link #onAddSnippetClick}
				 */	
		        click : this.onAddSnippetClick
	        },
	        "snippetgroupcontextmenu menuitem#newgroup" : {
	            /**
				 * @listener snippetgroupcontextmenu-menuitem-newgroup-click triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.SnippetGroupContextMenu SnippetGroupContextMenu}
				 *           menuitem#newgroup
				 *           {@link #onNewGroupClick}
				 */	
	        	click : this.onNewGroupClick
	        },
	        
	        
	    });
    },
    /**
	 * @method
	 */
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
		    var sngroupstore = Ext.StoreManager.get('cms.snippet.SnippetGroup');

		    sngroupstore.load({
		        scope : this,
		        id : record.data.indice,
		        callback : function(records, operation, success) {
			        if (success) {
				        var snitem = sngroupstore.first();
				        snprop.update(snitem);
			        }
		        }
		    });

	    }
	    else {
		    snusage.expand(true);
		    var snitemstore = Ext.StoreManager.get('cms.snippet.SnippetItem');
		    snitemstore.load({
		        id : record.data.indice,
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
    },
    /**
	 * @method
	 */
  onTreeContextMenu : function(component, record, item, index, e) {
    e.stopEvent();
    if (record.data.itemtype == 'snippetgroup') {
	    if (!this.foldermenu) {
		    this.groupmenu = Ext.create('widget.snippetgroupcontextmenu');
	    }
	    this.groupmenu.showAt(e.getXY());
    }
    else {
	    if (!this.itemmenu) {
		    this.itemmenu = Ext.create('widget.snippetcontextmenu');
	    }
	    this.itemmenu.showAt(e.getXY());
    }
},
/**
 * @method
 */
onNewGroupClick : function(component, event) {
    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();

    console.log(currentNode);

    var win = Ext.create('RODAdmin.view.cms.snippet.GroupWindow');
    win.setTitle('Add a new subgroup to "' + currentNode.data.name + '" (id: ' + currentNode.data.indice + ')');
    win.setIconCls('group_add');
    win.down('form').down('fieldset').down('hiddenfield').setValue(currentNode.data.indice);
    console.log(currentNode.data);
    win.show();
},
/**
 * @method
 */
onAddSnippetClick : function(component, event) {
    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
    console.log(currentNode);
    var win = Ext.create('RODAdmin.view.cms.snippet.AddSnippetToGroupWindow');
    win.setTitle('Add a new snippet to "' + currentNode.data.name + '" (id: ' + currentNode.data.indice + ')');
    win.setIconCls('snippet_add');
    win.down('form').down('fieldset').down('hiddenfield').setValue(currentNode.data.indice);
    win.down('form').down('fieldset').down('displayfield').setValue(currentNode.data.directory);
    win.show();
},
/**
 * @method
 */
onDeleteSnippetClick : function(component, event) {
    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
   
    var store = Ext.StoreManager.get('cms.snippet.Snippet');
    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the snippet ' + currentNode.data.name
            + '?', function(id, value) {
	    if (id === 'yes') {
		    console.log('we will delete');
		    Ext.Ajax.request({
		        url : 'http://localhost:8080/roda/admin/snippetdrop',
		        method : "POST",
		        params : {
			        snippetid : currentNode.data.indice
		        },
		        success : function() {
			        RODAdmin.util.Alert.msg('Success!', 'Snippet deleted.');
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
onEditSnippetClick : function(component, record, item, index, e) {

    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
    var win = Ext.create('RODAdmin.view.cms.snippet.EditSnippetWindow');
    win.setTitle('Edit Snippet');
    var wtree = win.down('treepanel');
    var snippetitemstore = Ext.create('RODAdmin.store.cms.snippet.SnippetItem');
    snippetitemstore.load({
        id : currentNode.data.indice, // set the id here
        scope : this,
        callback : function(records, operation, success) {
	        if (success) {
		        var item = snippetitemstore.first();
		        win.down('form').getForm().loadRecord(item);
		        win.down('form').down('hiddenfield#groupid').setValue(item.data.groupid);
	        }
        }
    });
    win.show();
},
/**
 * @method
 */
onCollapseTreeClick : function(button, e, options) {
    console.log('onCollapseTreeClick');
    this.getFolderview().collapseAll();
},
/**
 * @method
 */
onExpandTreeClick : function(button, e, options) {
    console.log('onExpandTreeClick');
    this.getFolderview().expandAll();
},
/**
 * @method
 */
onReloadTreeClick : function(button, e, options) {
    var folderview = this.getFolderview();
    var currentNode = folderview.getSelectionModel().getLastSelected();
    // var currentNode =
    // this.getFolderview().getSelectionModel().getSelectedIndex();
    console.log(currentNode);
    this.getFolderview().store.reload({
        scope : this,
        callback : function(records, operation, success) {
	        console.log('callback executed');
	        console.log(currentNode.idField.originalIndex);
	        // console.log(this.getFolderview().getSelectionModel());
	        folderview.getSelectionModel().select(currentNode);
        }
    });
    // this.getFolderview().getSelectionModel().select(currentNode);
},

});