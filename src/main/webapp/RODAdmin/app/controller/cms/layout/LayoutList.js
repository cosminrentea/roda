/**
 * Layoutlist - controller care se ocupa de lista de layouturi din
 * {@link RODAdmin.view.cms.layout.LayoutItemsview LayoutItemsview}
 */
Ext.define('RODAdmin.controller.cms.layout.LayoutList', {
    extend : 'Ext.app.Controller',

  
    stores : [
              'cms.layout.LayoutItem', 
              'cms.layout.Layout', 
              'common.Audit'],

    
    views : [
            'RODAdmin.view.cms.layout.LayoutItemsview', 
            'RODAdmin.view.cms.layout.LayoutDetails',
            'RODAdmin.view.cms.layout.details.LayoutProperties', 
            "RODAdmin.view.cms.layout.EditLayoutWindow",
            'RODAdmin.view.cms.layout.LayoutItemviewContextMenu', 
            'RODAdmin.view.cms.layout.details.LayoutUsage'
    ],
    /**
	 * @cfg
	 */
    refs : [
            {
                ref : 'iconview',
                selector : 'layoutitemsview grid#lyiconview'
            }, {
                ref : 'layoutproperties',
                selector : 'layoutproperties panel#lydata'
            }, {
                ref : 'lydetailspanel',
                selector : 'cmslayouts panel#lydetailscontainer '
            }, {
                ref : 'lyusagepanel',
                selector : 'layoutusage'
            },
            {
                ref : 'lyenvelope',
                 selector : 'layoutproperties panel#lyenvelope'
            },
            {
                ref : 'lycontent',
                selector : 'layoutproperties panel#lyenvelope codemirror#lycontent'
            }            

    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
	        "layoutitemsview grid#lyiconview" : {
	            /**
				 * @listener layoutitemsview-selectionchabge triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemsview LayoutItemsview}
				 *           grid#lyiconview executes
				 *           {@link #onDeleteLayoutClick}
				 */
	            selectionchange : this.onIconViewSelectionChange,
	            /**
				 * @listener icdeletelayout-itemcontextmenu triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemsview LayoutItemsview}
				 *           grid#lyiconview executes {@link #onItemContextMenu}
				 */
	            itemcontextmenu : this.onItemContextMenu
	        },
	        "layoutitemviewcontextmenu menuitem#icdeletelayout" : {
		        /**
				 * @listener icdeletelayout-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemviewContextMenu LayoutItemsviewContextMenu}
				 *           menuitem#icdeletelayout executes
				 *           {@link #onDeleteLayoutClick}
				 */
		        click : this.onDeleteLayoutClick
	        },
	        "layoutitemviewcontextmenu menuitem#iceditlayout" : {
		        /**
				 * @listener iceditlayout-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemviewContextMenu LayoutItemsviewContextMenu}
				 *           menuitem#iceditlayout executes
				 *           {@link #onEditLayoutClick}
				 * 
				 */
		        click : this.onEditLayoutClick
	        },
	        "layoutitemsview grid#lyiconview toolbar button#reloadgrid" : {
	            /**
				 * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-reloadtree triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
				 *           treepanel#lyfolderview toolbar button#reloadtree
				 *           {@link #onReloadTreeClick}
				 */
		        click : this.onReloadGridClick
	        }, 
	    });
    },
    /**
	 * @method
	 */
    onEditLayoutClick : function(component, record, item, index, e) {
	    console.log('onEditLayoutClick');
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    win = Ext.WindowMgr.get('layoutedit');
	    console.log(win);
	    if (!win) {
	    	win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
	    }	    
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
		        }
	        }
	    });
	    win.show();
    },
    /**
	 * @method
	 */
    onDeleteLayoutClick : function(component, event) {
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    var store = Ext.StoreManager.get('cms.layout.Layout');
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the layout ' + currentNode.data.name
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    Ext.Ajax.request({
			        url : '/roda/admin/layoutdrop',
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
    /**
	 * @method
	 */
    onIconViewSelectionChange : function(component, selected, event) {
	    console.log('folderviewselectionchange');
	    var record = selected[0];
	    if (record != null) {
	    console.log(record);
	    var lyprop = this.getLayoutproperties();
	    var lydetails = this.getLydetailspanel();
	    var lyusage = this.getLyusagepanel();
	    var lycontent = this.getLycontent();
	    var lyenvelope = this.getLyenvelope();	    
	    lydetails.setTitle(record.data.name);

	    lyusage.expand();
	    lyenvelope.expand();  
	    var lyitemstore = Ext.StoreManager.get('cms.layout.LayoutItem');
	    lyitemstore.load({
	        id : record.data.indice, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var lyitem = lyitemstore.first();
			        lycontent.setValue(lyitem.data.content);
			        lyprop.update(lyitem);
			        if (typeof lyitem.usageStore === 'object') {
						   lyusage.bindStore(lyitem.usage());
					   }
		        }
	        }
	    });
	    }
    },
    /**
	 * @method
	 */
    onItemContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
	    if (!this.contextmenu) {
		    this.contextmenu = Ext.create('widget.layoutitemviewcontextmenu');
	    }
	    this.contextmenu.showAt(e.getXY());
    },
    /**
	 * @method
	 */
    onReloadGridClick : function(button, e, options) {
	    console.log('reloading grid');
	    var iconview = this.getIconview();
	    var currentNode = iconview.getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var mmstore = this.getIconview().store;
	    var me = this;
	    mmstore.reload({
	        callback : function(records, operation, success) {
//		        var root = me.getFolderview().store.getRootNode();
//		        var myid = root.findChild('indice', currentNode.data.indice, true);
//			    if (myid != null) {
	        	console.log(currentNode);
	        	var mrr = mmstore.find('indice', currentNode.data.indice);
	        	console.log('selecting current node');
		    	iconview.getSelectionModel().select(mrr);
// }
	        }
	    });
    },
});