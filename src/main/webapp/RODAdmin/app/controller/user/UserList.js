/**
 *
 */
Ext.define('RODAdmin.controller.user.UserList', {
    extend: 'Ext.app.Controller',

	stores : [ 'user.User'],

    views : [
//             'RODAdmin.view.user.User',
//             'RODAdmin.view.user.Admin',
//             'RODAdmin.view.user.AdminItemsview',
//             'RODAdmin.view.user.User',
             'RODAdmin.view.user.UserItemsview',
//             'RODAdmin.view.user.UserDetails'
     ],

    refs : [ {
    	ref : 'iconview',
    	selector : 'useritemsview grid#usergrid'
    	}
    ],



init : function(application) {
	this.control({
        "useritemsview grid#usergrid toolbar button#refreshgrid" : {
            /**
			 * @listener useritemsview-grid-usergrid-toolbar-button-refreshgrid-click triggered-by:
			 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
			 *            grid#usergrid toolbar button#refreshgrid
			 *           {@link #onReloadGridClick}
			 */		        	
	        click : this.onReloadGridClick
        },
        "useritemsview grid#usersgrid" : {
            /**
			 * @listener useritemsview-grid-usersgrid-selectionchange triggered-by:
			 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
			 *            grid#usersgrid
			 *           {@link #onUsersViewSelectionChange}
			 */		        	
			selectionchange : this.onUsersViewSelectionChange,
//			itemcontextmenu : this.onItemContextMenu
		},
	});
},
	/**
	 * @method
	 */
onUsersViewSelectionChange : function(component, selected, event) {
	var record = selected[0];
	console.log('selection change')

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

});