/**
 * 
 */
Ext.define('RODAdmin.controller.user.User', {
    extend : 'Ext.app.Controller',

    views : [
            'RODAdmin.view.user.User',
            'RODAdmin.view.user.Admin',
            'RODAdmin.view.user.AdminItemsview',
            'RODAdmin.view.user.User',
            'RODAdmin.view.user.UserItemsview',
            'RODAdmin.view.user.UserDetails'
    ],
    stores : [
            'user.User',
            'user.Group'
    ],

    refs : [
            {
                ref : 'useritemsview',
                selector : 'useritemsview'
            }, {
                ref : 'usersgrid',
                selector : 'useritemsview grid#usergrid'
            }, {
                ref : "groupsgrid",
                selector : "useritemsview grid#usergroups"
            }
    ],
    init : function(application) {
	    this.control({
	        "useritemsview grid#usergrid" : {
	            /**
				 * @listener useritemsview-grid-usergrid-selectionchange triggered-by:
				 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
				 *           grid#usergrid
				 *           {@link #onListSelectionChange}
				 */		        	
		        selectionchange : this.onListSelectionChange,
	        // itemcontextmenu : this.onItemContextMenu
	        },
	        "usersmain toolbar button#users" : {
	            /**
				 * @listener usersmain-toolbar-button-users-click triggered-by:
				 *           {@link RODAdmin.view.user.User User}
				 *           toolbar button#users
				 *           {@link #onUsersClick}
				 */		        	
		        click : this.onUsersClick
	        },
	        "usersmain toolbar button#groups" : {
	            /**
				 * @listener usersmain-toolbar-button-groups-click triggered-by:
				 *           {@link RODAdmin.view.user.User User}
				 *           toolbar button#groups
				 *           {@link #onGroupsClick}
				 */		        	
		        click : this.onGroupsClick
	        },
	    });
    },
    /**
	 * @method
	 */
    onUsersClick : function(button, e, options) {
	    this.getUseritemsview().layout.setActiveItem('usergrid');
	    // var store = Ext.StoreManager.get('cms.layout.Layout');
	    // store.load();
    },
    /**
	 * @method
	 */
    onGroupsClick : function(button, e, options) {
	    this.getUseritemsview().layout.setActiveItem('usergroups');
	    // var store = Ext.StoreManager.get('cms.layout.Layout');
	    // store.load();
    },
    /**
	 * @method
	 */
    onListSelectionChange : function(component, selected, event) {
	    console.log('selectionchange');

    }
});