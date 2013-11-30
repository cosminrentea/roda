Ext.define('RODAdmin.controller.cms.layout.LayoutTree', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.layout.LayoutTree', 'cms.layout.LayoutItem', 'cms.layout.LayoutGroup', 'cms.layout.Layout',
            'common.Audit'
    ],

    views : [
            'cms.layout.Layouts', 'cms.layout.LayoutItemsview', 'cms.layout.LayoutDetails',
            'cms.layout.details.LayoutProperties', "cms.layout.EditLayoutWindow", 'cms.layout.LayoutContextMenu',
            'cms.layout.LayoutGroupContextMenu', 'cms.layout.AddLayoutToGroupWindow',
            'cms.layout.LayoutItemviewContextMenu', 'cms.layout.details.LayoutUsage'
    ],

    refs : [
            {
                ref : 'lydetailspanel',
                selector : 'cmslayouts panel#lydetailscontainer '
            }, {
                ref : 'lyusagepanel',
                selector : 'layoutusage'
            }, {
                ref : 'layoutproperties',
                selector : 'layoutproperties'
            }, {
                ref : 'itemsview',
                selector : 'layoutitemsview'
            }, {
                ref : "folderview",
                selector : "layoutitemsview treepanel#lyfolderview"
            }, {
                ref : 'folderselect',
                selector : 'layoutedit treepanel#groupselect'
            }
    ],

    init : function(application) {
	    this.control({
	        "layoutitemsview treepanel#lyfolderview" : {
	            selectionchange : this.onFolderviewSelectionChange,
	            itemcontextmenu : this.onTreeContextMenu
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
	        "layoutcontextmenu menuitem#deletelayout" : {
		        click : this.onDeleteLayoutClick
	        },
	        "layoutcontextmenu menuitem#editlayout" : {
		        click : this.onEditLayoutClick
	        },
	        "layoutgroupcontextmenu menuitem#addlayout" : {
		        click : this.onAddLayoutClick
	        },
	        "layoutgroupcontextmenu menuitem#newgroup" : {
		        click : this.onNewGroupClick
	        },

	    });
    },

    onGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    console.log('were here');
	    component.up('layoutedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name);
	    component.up('layoutedit').down('fieldset').query('hiddenfield')[0].setValue(record.data.id);
    },

    onNewGroupClick : function(component, event) {
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();

	    console.log(currentNode);

	    var win = Ext.create('RODAdmin.view.cms.layout.GroupWindow');
	    win.setTitle('Add a new subgroup to "' + currentNode.data.name + '" (id: ' + currentNode.data.indice + ')');
	    win.setIconCls('group_add');
	    win.down('form').down('fieldset').down('hiddenfield').setValue(currentNode.data.indice);
	    console.log(currentNode.data);
	    win.show();
    },

    onAddLayoutClick : function(component, event) {
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var win = Ext.create('RODAdmin.view.cms.layout.AddLayoutToGroupWindow');
	    win.setTitle('Add a new layout to "' + currentNode.data.name + '" (id: ' + currentNode.data.indice + ')');
	    win.setIconCls('file_add');
	    win.down('form').down('fieldset').down('hiddenfield').setValue(currentNode.data.indice);
	    win.down('form').down('fieldset').down('displayfield').setValue(currentNode.data.directory);
	    win.show();
    },

    onDeleteLayoutClick : function(component, event) {
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var store = Ext.StoreManager.get('cms.layout.Layout');
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the layout ' + currentNode.data.name
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    Ext.Ajax.request({
			        url : 'http://localhost:8080/roda/admin/layoutdrop',
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
	    var win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
	    win.setTitle('Edit Layout');
	    var wtree = win.down('treepanel');
	    var layoutitemstore = Ext.create('RODAdmin.store.cms.layout.LayoutItem');
	    layoutitemstore.load({
	        id : currentNode.data.indice, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var layoutitem = layoutitemstore.first();
			        win.down('form').getForm().loadRecord(layoutitem);
			        win.down('form').down('hiddenfield#groupid').setValue(layoutitem.data.groupid);
		        }
	        }
	    });
	    win.show();
    },

    onTreeContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
	    if (record.data.itemtype == 'layoutgroup') {
		    if (!this.foldermenu) {
			    this.groupmenu = Ext.create('widget.layoutgroupcontextmenu');
		    }
		    this.groupmenu.showAt(e.getXY());
	    }
	    else {
		    if (!this.itemmenu) {
			    this.itemmenu = Ext.create('widget.layoutcontextmenu');
		    }
		    this.itemmenu.showAt(e.getXY());
	    }
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
	    var folderview = this.getFolderview();
	    var currentNode = folderview.getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    this.getFolderview().store.reload({
	        scope : this,
	        callback : function(records, operation, success) {
		        console.log('callback executed');
		        console.log(currentNode.idField.originalIndex);
		        folderview.getSelectionModel().select(currentNode);
	        }
	    });
    },

    onFolderselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
	    component.up('layoutedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name);
	    component.up('layoutedit').down('fieldset').query('hiddenfield')[0].setValue(record.data.id);
    },

    onFolderviewSelectionChange : function(component, selected, event) {
	    console.log('folderviewselectionchange');
	    var record = selected[0];
	    var lyprop = this.getLayoutproperties();
	    var lydetails = this.getLydetailspanel();
	    var lyusage = this.getLyusagepanel();
	    lydetails.setTitle(record.data.name);

	    if (record.data.itemtype == 'layoutgroup') {
		    lyusage.collapse(true);
		    console.log('layoutgroup');
		    var lygroupstore = Ext.StoreManager.get('cms.layout.LayoutGroup');

		    lygroupstore.load({
		        scope : this,
		        id : record.data.indice, 
		        callback : function(records, operation, success) {
			        if (success) {
				        var lyitem = lygroupstore.first();
				        lyprop.update(lyitem);
			        }
		        }
		    });

	    }
	    else {
		    lyusage.expand(true);
		    var lyitemstore = Ext.StoreManager.get('cms.layout.LayoutItem');
		    lyitemstore.load({
		        id : record.data.indice, 
		        scope : this,
		        callback : function(records, operation, success) {
			        if (success) {
				        var lyitem = lyitemstore.first();
				        lyprop.update(lyitem);
				        if (!typeof lyitem.layoutusageStore === 'undefined') {
					        lyusage.bindStore(lyitem.layoutusageStore);
				        }
			        }
		        }
		    });
	    }
    }
});