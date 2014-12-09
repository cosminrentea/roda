/**
 * 
 */
Ext.define('RODAdmin.controller.user.GroupList', {
    extend : 'Ext.app.Controller',

    stores : [
            'user.Group', 'user.GroupInfo', 'user.UsersbyGroup'
    ],

    views : [
            'RODAdmin.view.user.UserItemsview', 'RODAdmin.view.user.GroupContextMenu',
    ],

    refs : [
            {
                ref : 'groupview',
                selector : 'useritemsview grid#usergroups'
            }, {
                ref : 'detailscontainer',
                selector : 'users panel#udetailscontainer'
            }, {
                ref : 'groupinfo',
                selector : 'groupdetails panel#groupinfo'
            }, {
                ref : 'groupuser',
                selector : 'groupdetails grid#groupusers'
            }, {
                ref : 'groupsgrid',
                selector : 'useritemsview grid#usergroups'
            }, {
            	ref: 'useritemsview',
            	selector : 'useritemsview'
            }
    ],

    /**
	 * @method
	 */

    init : function(application) {
	    this.control({
	        "useritemsview grid#usergroups toolbar button#refreshgrid" : {
		        /**
				 * @listener useritemsview-grid-usergroups-toolbar-button-refreshgrid-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
				 *           grid#usergroups toolbar button#refreshgrid
				 *           {@link #onReloadGridClick}
				 */
		        click : this.onReloadGridClick
	        },
	        "useritemsview grid#usergroups" : {
	            /**
				 * @listener useritemsview-grid-usergrid-selectionchange
				 *           triggered-by:
				 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
				 *           grid#usergrid {@link #onListSelectionChange}
				 */
	            selectionchange : this.onListSelectionChange,
	            itemcontextmenu : this.onGroupContextMenu
	        },
	        "groupdetails panel#groupinfo toolbar button#groupedit" : {
		        click : this.onGroupEditClick
	        },
	        "groupcontextmenu menuitem#deletegroup" : {
		        click : this.onGroupDeleteClick
	        },
	        "groupcontextmenu menuitem#editgroup" : {
		        click : this.onGroupEditClick
	        },
	        "groupcontextmenu menuitem#adduser" : {
		        click : this.onGroupAddUserClick
	        },
	        "groupedit button#save" : {
		        /**
				 * @listener sngroupadd-button-save triggered-by:
				 *           {@link RODAdmin.view.cms.snippet.GroupWindow GroupWindow}
				 *           button#save {@link #onGroupSaveClick}
				 */
		        click : this.onGroupSaveClick
	        },

	    });
    },

    /**
	 * @method
	 */
    onReloadGridClick : function(button, e, options) {
	    var iconview = this.getGroupview();
	    // var currentNode = folderview.getSelectionModel().getLastSelected();
	    // console.log(currentNode);
	    this.getGroupview().store.reload({
	        scope : this,
	        callback : function(records, operation, success) {
		        console.log('callback executed');
		        // console.log(currentNode.idField.originalIndex);
		        // folderview.getSelectionModel().select(currentNode);
	        }
	    });
    },

    onGroupSaveClick : function(button, e, options) {

	    console.log('group');
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : RODAdmin.util.Globals.baseurl + '/adminjson/groupsave',
		        success : function(form, action) {
			        console.log('wtf???');
			        console.log(action);
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Group saved.');
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

			        case Ext.form.action.Action.SERVER.INVALID:
				        Ext.Msg.alert('Failure', action.result.msg);
				        break;
			        }
		        }
		    });
	    }
    },
    
    
    
    onGroupDeleteClick : function(button, e, options) {
 	   var currentNode = this.getGroupview().getSelectionModel().getLastSelected();
	   var me = this;
	   Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the group ' + currentNode.data.name
	                   + '?', function(id, value) {
		   					if (id === 'yes') {
		   							console.log('we will delete');
		   							Ext.Ajax.request({
		   								url : RODAdmin.util.Globals.baseurl + '/adminjson/groupdrop',
				   method : "POST",
				   params : {
					   layoutid : currentNode.data.indice
				   },
				   success : function() {
					   RODAdmin.util.Alert.msg('Success!', 'Group deleted.');
					   me.getFolderview().store.load();
//					   store.load;
				   },
				   failure : function(response, opts) {
					   Ext.Msg.alert('Failure', response);

				   }
			   });
		   }
	   }, this);
	   event.stopEvent();
    },

    onGroupAddUserClick : function(button, e, options) {
	    console.log('group add user');
    },

    onGroupContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
	    if (this.groupmenu) {
		    this.groupmenu.destroy();
	    }
	    // aici sa vedem daca pagina are pagini subordonate
	    // console.log(record.childNodes.length);

	    this.groupmenu = Ext.create('widget.groupcontextmenu');
	    this.groupmenu.showAt(e.getXY());
    },

    onGroupEditClick : function(button, e, options) {
	    console.log('group edit');
	    var currentNode = this.getGroupsgrid().getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    win = Ext.WindowMgr.get('groupedit');
	    console.log(win);
	    if (!win) {
		    win = Ext.create('RODAdmin.view.user.GroupEdit');
	    }
	    win.setTitle('Edit Group ' + currentNode.data.name);
	    win.down('form').getForm().loadRecord(currentNode);
	    win.show();
    },

    onListSelectionChange : function(component, selected, event) {
	    console.log('selectionchange');
	    this.getDetailscontainer().layout.setActiveItem(1);
	    var record = selected[0];
	    console.log(record);
	    var groupinfo = this.getGroupinfo();
	    var groupuser = this.getGroupuser();
	    var groupstore = Ext.StoreManager.get('user.GroupInfo');
	    console.log(groupstore);
	    console.log(record.data.id);
	    groupstore.load({
	        id : record.data.id, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var groupitem = groupstore.first();
			        var usersstore = Ext.StoreManager.get('user.UsersbyGroup');
			        usersstore.load({
			            id : record.data.id,
			            callback : function(records, operation, success) {
				            if (success) {
					            console.log(groupuser);
					            groupuser.bindStore(usersstore);
				            }
			            }
			        });

			        // console.log(groupitem);
			        // // var profile = useritem.profileStore.getAt(0);
			        groupinfo.update(groupitem);
			        // // var rec = useritem.profile().getAt(0);
			        // // userprofile.update(profile);
		        }
	        }
	    });

    }

});