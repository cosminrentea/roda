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
                selector : 'layoutproperties'
            }, {
                ref : 'lydetailspanel',
                selector : 'cmslayouts panel#lydetailscontainer '
            }, {
                ref : 'lyusagepanel',
                selector : 'layoutusage'
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
	        }
	    });
    },
    /**
	 * @method
	 */
    onEditLayoutClick : function(component, record, item, index, e) {
	    console.log('onEditLayoutClick');
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
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
    /**
	 * @method
	 */
    onIconViewSelectionChange : function(component, selected, event) {
	    console.log('folderviewselectionchange');
	    var record = selected[0];
	    var lyprop = this.getLayoutproperties();
	    var lydetails = this.getLydetailspanel();
	    var lyusage = this.getLyusagepanel();

	    console.log(record.data.indice);

	    lydetails.setTitle(record.data.name);

	    lyusage.expand(true);
	    var lyitemstore = Ext.StoreManager.get('cms.layout.LayoutItem');
	    lyitemstore.load({
	        id : record.data.indice, // set the id here
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

});