/**
 * 
 */
Ext.define('RODAdmin.controller.user.UserList', {
    extend : 'Ext.app.Controller',

    stores : [
            'user.User', 'user.UserInfo'
    ],

    views : [
            // 'RODAdmin.view.user.User',
            // 'RODAdmin.view.user.Admin',
            // 'RODAdmin.view.user.AdminItemsview',
            // 'RODAdmin.view.user.User',
            'RODAdmin.view.user.UserItemsview',
            'RODAdmin.view.user.UserContextMenu',
            
    // 'RODAdmin.view.user.UserDetails'
    ],

    refs : [
            {
                ref : 'iconview',
                selector : 'useritemsview grid#usergrid'
            }, {
                ref : 'userinfo',
                selector : 'userdetails panel#userinfo'
            }, {
                ref : 'userprofile',
                selector : 'userdetails panel#userprofile'
            }, {
                ref : 'detailscontainer',
                selector : 'users panel#udetailscontainer'
            }

    ],

    init : function(application) {
	    this.control({
	        "useritemsview grid#usergrid toolbar button#refreshgrid" : {
		        /**
				 * @listener useritemsview-grid-usergrid-toolbar-button-refreshgrid-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
				 *           grid#usergrid toolbar button#refreshgrid
				 *           {@link #onReloadGridClick}
				 */
		        click : this.onReloadGridClick
	        },
	        "useritemsview grid#usersgrid" : {
		        /**
				 * @listener useritemsview-grid-usersgrid-selectionchange
				 *           triggered-by:
				 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
				 *           grid#usersgrid {@link #onUsersViewSelectionChange}
				 */
		        selectionchange : this.onUsersViewSelectionChange,
	            itemcontextmenu : this.onUserContextMenu,
	        },
	        "groupdetails grid#groupusers" : {
		        /**
				 * @listener useritemsview-grid-usersgrid-selectionchange
				 *           triggered-by:
				 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
				 *           grid#usersgrid {@link #onUsersViewSelectionChange}
				 */
	            itemcontextmenu : this.onUserContextMenu,
	        },

	        
	        
	    });
    },
    
    
    
    onUserContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
	    if (this.usermenu) {
		    this.usermenu.destroy();
	    }
	    // aici sa vedem daca pagina are pagini subordonate
	    // console.log(record.childNodes.length);

	    this.usermenu = Ext.create('widget.usercontextmenu');
	    this.usermenu.showAt(e.getXY());
    },
    
    
    /**
	 * @method
	 */
    onUsersViewSelectionChange : function(component, selected, event) {
	    console.log('selection change');
	    this.getDetailscontainer().layout.setActiveItem(0);
	    var record = selected[0];
	    if (record) { 
	    console.log('else here');
	    var userinfo = this.getUserinfo();
	    var userprofile = this.getUserprofile();
	    var userstore = Ext.StoreManager.get('user.UserInfo');
	    userstore.load({
	        id : record.data.id, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var useritem = userstore.first();
			        console.log(useritem);
			        console.log(useritem.profile);
			        var profile = useritem.profileStore.getAt(0);
			        userinfo.update(useritem);
			        userprofile.update(profile);
		        }
	        }
	    });
	    }
    },

    /**
	 * @method
	 */
    onReloadGridClick : function(button, e, options) {
	    var iconview = this.getIconview();
	    // var currentNode = folderview.getSelectionModel().getLastSelected();
	    // console.log(currentNode);
	    this.getIconview().store.reload({
	        scope : this,
	        callback : function(records, operation, success) {
		        console.log('callback executed');
		        // console.log(currentNode.idField.originalIndex);
		        // folderview.getSelectionModel().select(currentNode);
	        }
	    });
    },

});