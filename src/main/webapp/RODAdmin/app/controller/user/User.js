/**
 * 
 */
Ext.define('RODAdmin.controller.user.User', {
    extend : 'Ext.app.Controller',
    id : 'User',
    views : [
            'RODAdmin.view.user.User', 'RODAdmin.view.user.Admin', 'RODAdmin.view.user.AdminItemsview',
            'RODAdmin.view.user.User', 'RODAdmin.view.user.UserItemsview', 'RODAdmin.view.user.UserDetails',
            'RODAdmin.view.user.GroupDetails', 'RODAdmin.view.user.ProfileEdit', 'RODAdmin.view.user.UserEdit',
            'RODAdmin.view.user.UserMessage',
    ],
    stores : [
            'user.User', 'user.Group'
    ],

    refs : [
            {
                ref : 'useritemsview',
                selector : 'useritemsview'
            }, {
                ref : 'usersgrid',
                selector : 'useritemsview grid#usersgrid'
            }, {
                ref : "groupsgrid",
                selector : "useritemsview grid#usergroups"
            }, {
                ref : 'detailscontainer',
                selector : 'users panel#udetailscontainer'
            }, {
                ref : 'usersview',
                selector : 'useritemsview grid#usersgrid'
            }, {
                ref : 'usersbutton',
                selector : 'users button#users'
            }, {
                ref : 'itemsview',
                selector : 'useritemsview grid#usersgrid'
            }, {
                ref : 'groupdetailsgrid',
                selector : 'groupdetails grid#groupusers'
            },

    ],
    init : function(application) {

	    this.control({

	        "users toolbar button#users" : {
		        /**
				 * @listener usersmain-toolbar-button-users-click triggered-by:
				 *           {@link RODAdmin.view.user.User User} toolbar
				 *           button#users {@link #onUsersClick}
				 */
		        click : this.onUsersClick
	        },
	        "users toolbar button#groups" : {
		        /**
				 * @listener usersmain-toolbar-button-groups-click triggered-by:
				 *           {@link RODAdmin.view.user.User User} toolbar
				 *           button#groups {@link #onGroupsClick}
				 */
		        click : this.onGroupsClick
	        },
	        "usercontextmenu menuitem#deleteuser" : {
		        click : this.onUserDeleteClick
	        },
	        "usercontextmenu menuitem#edituser" : {
		        click : this.onUserEditClick
	        },
	        "usercontextmenu menuitem#usermessage" : {
		        click : this.onUserMessageClick
	        },
	        "userdetails panel#userinfo toolbar button#useredit" : {
		        click : this.onUserEditClick
	        },
	        "userdetails panel#userinfo toolbar button#useradd" : {
		        click : this.onUserAddClick
	        },
	        "userdetails panel#userprofile toolbar button#editprofile" : {
		        click : this.onProfileEditClick
	        },
	        "useredit button#save" : {
		        /**
				 * @listener sngroupadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.GroupWindow GroupWindow}
				 *           button#save {@link #onGroupSaveClick}
				 */
		        click : this.onUserSaveClick
	        },
	        "useradd button#save" : {
		        /**
				 * @listener sngroupadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.GroupWindow GroupWindow}
				 *           button#save {@link #onGroupSaveClick}
				 */
		        click : this.onUserSaveClick
	        },

	        "usermessage button#save" : {
		        /**
				 * @listener sngroupadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.GroupWindow GroupWindow}
				 *           button#save {@link #onGroupSaveClick}
				 */
		        click : this.onUserSendMessageClick
	        },

	        "profileedit button#save" : {
		        /**
				 * @listener sngroupadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.GroupWindow GroupWindow}
				 *           button#save {@link #onGroupSaveClick}
				 */
		        click : this.onProfileSaveClick
	        },
	        "useritemsview grid#usersgrid checkcolumn" : {
		        checkchange : this.onUserCheckChange
	        },

	        "groupdetails grid#groupusers checkcolumn" : {
		        checkchange : this.onUserCheckChange
	        },
	        "useritemsview grid#usergroups checkcolumn" : {
		        checkchange : this.onGroupCheckChange
	        },
	        "useritemsview button#refreshusers" : {
		        click : this.onRefreshUsers
	        },
	        "useritemsview button#refreshgroups" : {
		        click : this.onRefreshGroups
	        },
	        "groupdetails button#refreshgrid" : {
		        click : this.onRefreshGroupDetails
	        },

	    });
	    this.listen({
		    controller : {
			    '*' : {
				    controllerUsersInitView : this.initView
			    }
		    }
	    });

    },

    /**
	 * @method
	 */
    initView : function() {
	    console.log('Init View, baby');
	    Ext.History.add('menu-users');
	    this.getUsersgrid().store.load();
	    this.getGroupsgrid().store.load();
	    this.getUsersbutton().toggle();
    },

    onUserMessageClick : function() {
	    var currentNode = this.getUsersgrid().getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    win = Ext.WindowMgr.get('useredit');
	    console.log(win);
	    if (!win) {
		    win = Ext.create('RODAdmin.view.user.UserMessage');
	    }
	    win.setTitle('Send a message to ' + currentNode.data.username);
	    win.down('form').getForm().loadRecord(currentNode);
	    win.show();
    },

    onUserSendMessageClick : function(button) {
	    console.log('usersave');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : RODAdmin.util.Globals.baseurl + 'adminjson/usermessage',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Message sent.');
				        win.close();
				        console.log(me.getUseritemsview());
				        var active = me.getUseritemsview().layout.getActiveItem();
				        if (active.itemId == 'usersgrid') {
					        me.onRefreshUsers();
				        }
				        else if (active.itemId == 'usergrups') {
					        me.onRefreshGroups();
				        }
			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        console.log(action.failureType);
			        console.log(action);
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
				        break;

			        case Ext.form.action.Action.CONNECT_FAILURE:

				        // Ext.Msg.alert('Failure', action.result.msg);
				        Ext.Msg.alert('Failure', 'doesn\'t work');
				        break;

			        case Ext.form.action.Action.SERVER_INVALID:
				        Ext.Msg.alert('Failure', action.result.message);
				        break;
			        }
		        }
		    });
	    }
    },

    onRefreshGroups : function(button, e, options) {
	    var iconview = this.getGroupsgrid();
	    var currentNode = iconview.getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var mmstore = iconview.store;
	    var me = this;
	    mmstore.reload({
		    callback : function(records, operation, success) {
			    var mrr = mmstore.find('indice', currentNode.data.indice);
			    iconview.getSelectionModel().select(mrr);
		    }
	    });
    },

    onRefreshGroupDetails : function(button, e, options) {
	    var iconview = this.getGroupdetailsgrid();
	    var currentNode = iconview.getSelectionModel().getLastSelected();
	    var mmstore = iconview.store;
	    var me = this;
	    mmstore.reload({
		    callback : function(records, operation, success) {
			    if (currentNode) {
				    var mrr = mmstore.find('indice', currentNode.data.indice);
				    iconview.getSelectionModel().select(mrr);
			    }
		    }
	    });
    },

    onRefreshUsers : function(button, e, options) {
	    var iconview = this.getUsersgrid();
	    var currentNode = iconview.getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var mmstore = iconview.store;
	    var me = this;
	    mmstore.reload({
		    callback : function(records, operation, success) {
			    if (currentNode) {
				    var mrr = mmstore.find('indice', currentNode.data.indice);
				    iconview.getSelectionModel().select(mrr);
			    }
		    }
	    });
    },

    onUserDeleteClick : function(one, two) {
	    var currentNode = this.getUsersgrid().getSelectionModel().getLastSelected();
	    var me = this;
	    console.log(currentNode);
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the user ' + currentNode.data.username
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    Ext.Ajax.request({
			        url : RODAdmin.util.Globals.baseurl + 'adminjson/userdrop',
			        method : "POST",
			        params : {
				        userid : currentNode.data.id
			        },
			        success : function(response, request) {
				        var resp = Ext.JSON.decode(response.responseText);
				        if (resp.success) {
					        RODAdmin.util.Alert.msg('Success!', 'User deleted.');
					        me.getUsersgrid().store.load();
				        }
				        else {
					        Ext.Msg.alert('Failure', resp.message);
				        }
			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
	    event.stopEvent();

    },

    onUserCheckChange : function(column, rowIndex, checked) {

	    if (checked == false) {
		    ajurl = RODAdmin.util.Globals.baseurl + 'adminjson/userdisable';
	    }
	    else if (checked == true) {
		    ajurl = RODAdmin.util.Globals.baseurl + 'adminjson/userenable';
	    }
	    Ext.Ajax.request({
	        url : ajurl,
	        method : "POST",
	        params : {
		        // navigable : checked,
		        userid : rowIndex
	        },
	        failure : function(response, opts) {
		        Ext.Msg.alert('Failure', response);

	        }
	    });

    },

    onGroupCheckChange : function(column, rowIndex, checked) {
	    console.log('checkchange in controller' + checked);

	    // if (checked == false) {
	    // ajurl = RODAdmin.util.Globals.baseurl + 'adminjson/userdisable';
	    // } else if (checked == true) {
	    // ajurl = RODAdmin.util.Globals.baseurl + 'adminjson/userenable';
	    // }
	    // Ext.Ajax.request({
	    // url : ajurl,
	    // method : "POST",
	    // params : {
	    // // navigable : checked,
	    // userid : rowIndex
	    // },
	    // failure : function(response, opts) {
	    // Ext.Msg.alert('Failure', response);
	    //
	    // }
	    // });

    },

    onProfileSaveClick : function(button, e, options) {
	    console.log('profilesave');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var currentNode = this.getUsersview().getSelectionModel().getLastSelected();
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : RODAdmin.util.Globals.baseurl + 'adminjson/usersave',

		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'User profile saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'usersgrid') {
					        me.getController('RODAdmin.controller.cms.user.UsersList').onReloadGridClick();
				        }
				        else if (active.itemId == 'usergrups') {
					        me.getController('RODAdmin.controller.cms.user.GroupList').onReloadGridClick();
				        }
			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        console.log(action.failureType);
			        console.log(action);
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
				        break;

			        case Ext.form.action.Action.CONNECT_FAILURE:

				        // Ext.Msg.alert('Failure', action.result.msg);
				        Ext.Msg.alert('Failure', 'doesn\'t work');
				        break;

			        case Ext.form.action.Action.SERVER.INVALID:
				        Ext.Msg.alert('Failure', action.result.msg);
				        break;
			        }
		        }
		    });
	    }
    },

    onUserSaveClick : function(button, e, options) {
	    console.log('usersave');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    // var currentNode =
		// this.getUsersview().getSelectionModel().getLastSelected();
	    console.log(RODAdmin.util.Globals.baseurl + 'adminjson/usersave');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : RODAdmin.util.Globals.baseurl + 'adminjson/usercreate',

		        success : function(form, action) {

			        console.log('wtf???');
			        console.log(action);
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'User saved.');
				        win.close();
				        console.log(me.getUseritemsview());
				        var active = me.getUseritemsview().layout.getActiveItem();
				        if (active.itemId == 'usersgrid') {
					        me.onRefreshUsers();
				        }
				        else if (active.itemId == 'usergrups') {
					        me.onRefreshGroups();
				        }
			        }
			        else {
				        RODAdmin.util.Util.showErrorMsg(result.msg);
			        }
		        },
		        failure : function(form, action) {
			        console.log(action.failureType);
			        console.log(action);
			        switch (action.failureType) {
			        case Ext.form.action.Action.CLIENT_INVALID:
				        Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
				        break;

			        case Ext.form.action.Action.CONNECT_FAILURE:

				        // Ext.Msg.alert('Failure', action.result.msg);
				        Ext.Msg.alert('Failure', 'doesn\'t work');
				        break;

			        case Ext.form.action.Action.SERVER_INVALID:
				        Ext.Msg.alert('Failure', action.result.msg);
				        break;
			        }
		        }
		    });
	    }
    },
    /**
	 * @method
	 */
    onUsersClick : function(button, e, options) {

	    console.log('users click');
	    this.getUseritemsview().layout.setActiveItem('usersgrid');
	    this.getDetailscontainer().layout.setActiveItem(0);
	    // var store = Ext.StoreManager.get('cms.layout.Layout');
	    // store.load();
    },

    onUserAddClick : function(button, e, options) {
	    console.log('user add');
	    win = Ext.WindowMgr.get('useradd');
	    console.log(win);
	    if (!win) {
		    win = Ext.create('RODAdmin.view.user.UserAdd');
	    }
	    win.setTitle('Add User');
	    win.show();
    },

    onUserEditClick : function(button, e, options) {
	    console.log('user edit');
	    var currentNode = this.getUsersgrid().getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    win = Ext.WindowMgr.get('useredit');
	    console.log(win);
	    if (!win) {
		    win = Ext.create('RODAdmin.view.user.UserEdit');
	    }
	    win.setTitle('Edit User ' + currentNode.data.username);
	    win.down('form').getForm().loadRecord(currentNode);
	    win.show();
    },
    /**
	 * @method
	 */

    onProfileEditClick : function(button, e, options) {
	    console.log('profile edit');
	    var currentNode = this.getUsersgrid().getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    win = Ext.WindowMgr.get('profileedit');
	    console.log(win);
	    if (!win) {
		    win = Ext.create('RODAdmin.view.user.ProfileEdit');
	    }
	    win.setTitle('Edit User Profile');
	    console.log(currentNode);

	    var userstore = Ext.StoreManager.get('user.UserInfo');
	    userstore.load({
	        id : currentNode.data.id, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var useritem = userstore.first();
			        var profile = useritem.profileStore.getAt(0);
			        win.down('form').getForm().loadRecord(profile);
			        win.down('form hiddenfield#idfield').setValue(currentNode.data.id);
		        }
	        }
	    });
	    win.show();
    },

    /**
	 * @method
	 */
    onGroupsClick : function(button, e, options) {
	    console.log('groups click');
	    this.getUseritemsview().layout.setActiveItem('usergroups');
	    this.getDetailscontainer().layout.setActiveItem(1);
    },
    /**
	 * @method
	 */
    onListSelectionChange : function(component, selected, event) {
	    console.log('selectionchange');

    }
});