/**
 * 
 */
Ext.define('RODAdmin.controller.cron.ActionList', {
    extend: 'Ext.app.Controller',

	stores : [ 'cron.ActionList',
	           'cron.ExecutionList',
	           'cron.ActionItem'],
    
    views: [
        'RODAdmin.view.cron.action.Actions',
        'RODAdmin.view.cron.action.Cronactionsview',
        'RODAdmin.view.cron.action.CronDetails',
        'RODAdmin.view.cron.action.details.ActionProperties',
        'RODAdmin.view.cron.action.details.ActionRuns',
        'RODAdmin.view.cron.action.CronActionsItemviewContextMenu',
        'RODAdmin.view.cron.action.details.ErrorMessage',
        'RODAdmin.view.cron.action.EditCronActionWindow'
    ],

    refs : [ {
    	ref : 'iconview',
    	selector : 'cronactionsview grid#croniconview'
    }, {
    	ref : 'actionproperties',
    	selector : 'actionproperties'
    }, {
    	ref : 'actiondetailspanel',
    	selector : 'cronactions panel#crondetails '
    }, {
    	ref : 'actionruns',
    	selector : 'actionruns'
    }
    ],

/**
 * @method
 */

init : function(application) {
	this.control({
		"cronactionsview grid#croniconview" : {
            /**
			 * @listener cronactionsview-grid-croniconview-selectionchange triggered-by:
			 *           {@link RODAdmin.view.cron.action.Cronactionsview Cronactionsview}
			 *           grid#croniconview
			 *           {@link #onListSelectionChange}
			 */		        	
			selectionchange : this.onListSelectionChange,
            /**
			 * @listener cronactionsview-grid-croniconview-itemcontextmenu triggered-by:
			 *           {@link RODAdmin.view.cron.action.EditCronActionWindow EditCronActionWindow}
			 *           grid#croniconview
			 *           {@link #onItemContextMenu}
			 */		        	
			itemcontextmenu : this.onItemContextMenu
		},
		"cronactionsitemviewcontextmenu menuitem#deletecronaction" : {
            /**
			 * @listener cronactionsitemviewcontextmenu-menuitem-deletecronaction-click triggered-by:
			 *           {@link RODAdmin.view.cron.action.CronActionsItemviewContextMenu CronActionsItemviewContextMenu}
			 *           menuitem#deletecronaction
			 *           {@link #onDeleteClick}
			 */		        	
			click : this.onDeleteClick
		},
		"cronactionsitemviewcontextmenu menuitem#editcronaction" : {
            /**
			 * @listener cronactionsitemviewcontextmenu-menuitem-editcronaction-click triggered-by:
			 *           {@link RODAdmin.view.cron.action.CronActionsItemviewContextMenu CronActionsItemviewContextMenu}
			 *           menuitem#editcronaction
			 *           {@link #onEditClick}
			 */		        	
			click : this.onEditClick
		},
        "cronactionsview grid#croniconview toolbar button#refreshgrid" : {
            /**
			 * @listener cronactionsview-grid-croniconview-toolbar-button-refreshgrid-click triggered-by:
			 *           {@link RODAdmin.view.cron.action.Cronactionsview Cronactionsview}
			 *           grid#croniconview toolbar button#refreshgrid
			 *           {@link #onReloadGridClick}
			 */		        	
	        click : this.onReloadGridClick
        },
        "actionruns" : {
            /**
			 * @listener actionruns-cellclick triggered-by:
			 *           {@link RODAdmin.view.cron.action.details.ActionRuns ActionRuns}
			 *           {@link #onCellclick}
			 */		        	
        	cellclick : this.onCellclick
        }
	});
},

/**
 * @method
 */
onCellclick : function(gridView,htmlElement,columnIndex,dataRecord,htmlRow, rowIndex, e, eOpts) {
	if (columnIndex == 4) {
		 var win = Ext.create('RODAdmin.view.cron.action.details.ErrorMessage');
		 win.show();
	}
},
/**
 * @method
 */
onReloadGridClick : function(button, e, options) { 
    var iconview = this.getIconview();
//    var currentNode = folderview.getSelectionModel().getLastSelected();
//    console.log(currentNode);
    this.getIconview().store.reload({
        scope : this,
        callback : function(records, operation, success) {
	        console.log('callback executed');
//	        console.log(currentNode.idField.originalIndex);
//	        folderview.getSelectionModel().select(currentNode);
        }
    });
},
/**
 * @method
 */
onEditClick : function(component, record, item, index, e) {

	var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	var win = Ext.create('RODAdmin.view.cron.action.EditCronActionWindow');
	win.setTitle('Edit Cron Action');
	var cronactionstore = this.getCronActionItemStore();
	cronactionstore.load({
				id: currentNode.data.id, //set the id here
				scope : this,
				callback : function(records, operation, success) {
					if (success) {
						var item = cronactionstore.first();
						win.down('form').getForm().loadRecord(item);
					}
				}
	});	
	win.show();
},
/**
 * @method
 */
onDeleteClick : function(component, event) {
	var currentNode = this.getIconview().getSelectionModel().getLastSelected();
    /**
     * @todo StoreCall 
     * Apelul catre store trebuie sa fie local
     * @todo Store 
     * Trebuie convertit la acces catre store, nu cu post ajax cum e acum.
     */	    
	var store = Ext.StoreManager.get('cms.layout.Layout');
	Ext.Msg.confirm('Delete Requirement',
			'Are you sure you want to delete the layout '
					+ currentNode.data.name + '?', function(id, value) {
				if (id === 'yes') {
					console.log('we will delete');
					Ext.Ajax.request({
								url : RODAdmin.util.Globals.baseurl + 'adminjson/crondrop',
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
onListSelectionChange : function(component, selected, event) {
	var record = selected[0];
	var prop = this.getActionproperties();
	var details = this.getActiondetailspanel();
	var runs = this.getActionruns();



	details.setTitle(record.data.name);

	var itemstore = this.getCronActionListStore();
	itemstore.load({
		id : record.data.indice, //set the id here
		scope : this,
		callback : function(records, operation, success) {
			if (success) {
				var item = itemstore.first();
				prop.update(item);
				var execstore = runs.store;
			    execstore.load({
			        id : item.data.id, // set the id here
			    });
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
		this.contextmenu = Ext.create('widget.cronactionsitemviewcontextmenu');
	}
	this.contextmenu.showAt(e.getXY());
},



});