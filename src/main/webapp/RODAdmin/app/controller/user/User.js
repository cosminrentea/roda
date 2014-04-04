/**
 * 
 */
Ext.define('RODAdmin.controller.user.User', {
    extend : 'Ext.app.Controller',
    id: 'User',
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
	       
	        "users toolbar button#users" : {
	            /**
				 * @listener usersmain-toolbar-button-users-click triggered-by:
				 *           {@link RODAdmin.view.user.User User}
				 *           toolbar button#users
				 *           {@link #onUsersClick}
				 */		        	
		        click : this.onUsersClick
	        },
	        "users toolbar button#groups" : {
	            /**
				 * @listener usersmain-toolbar-button-groups-click triggered-by:
				 *           {@link RODAdmin.view.user.User User}
				 *           toolbar button#groups
				 *           {@link #onGroupsClick}
				 */		        	
		        click : this.onGroupsClick
	        },
	    });
    	this.listen({
            controller: {
                '*': {
                    controllerUsersInitView: this.initView
                }
            }
    	 });

	    
	    
    },
    
    /**
	 * @method
	 */
    initView : function() {
    	console.log('Init View, baby');	
    	 this.getUsersgrid().store.load();
    	 this.getGroupsgrid().store.load();
    },
    /**
	 * @method
	 */
    onUsersClick : function(button, e, options) {
    	
    	console.log('users click');
	    this.getUseritemsview().layout.setActiveItem('usergrid');
	    // var store = Ext.StoreManager.get('cms.layout.Layout');
	    // store.load();
    },
    /**
	 * @method
	 */
    onGroupsClick : function(button, e, options) {
    	console.log('groups click');
    	this.getUseritemsview().layout.setActiveItem('usergroups');
    },
    /**
	 * @method
	 */
    onListSelectionChange : function(component, selected, event) {
	    console.log('selectionchange');

    }
});