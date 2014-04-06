/**
 * 
 */
Ext.define('RODAdmin.controller.user.GroupList', {
    extend : 'Ext.app.Controller',

    stores : [
	    'user.Group',
	    'user.GroupInfo',
	    'user.UsersbyGroup'
    ],

    views : [
         'RODAdmin.view.user.UserItemsview'
             ],
    
    refs : [
	    {
	        ref : 'groupview',
	        selector : 'useritemsview grid#usergroups'
	    },
	    {
	    	ref: 'detailscontainer',
	    	selector: 'users panel#udetailscontainer'
	    },
	    {
            ref : 'groupinfo',
            selector : 'groupdetails panel#groupinfo'
        },
        {
            ref : 'groupuser',
            selector : 'groupdetails grid#groupusers'
        },
        {
            ref : 'groupsgrid',
            selector : 'useritemsview grid#usergroups'
        }
    ],

    /**
	 * @method
	 */

    init : function(application) {
	    this.control({
		    "useritemsview grid#usergroups toolbar button#refreshgrid" : {
	            /**
				 * @listener useritemsview-grid-usergroups-toolbar-button-refreshgrid-click triggered-by:
				 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
				 *           grid#usergroups toolbar button#refreshgrid
				 *           {@link #onReloadGridClick}
				 */		        	
			    click : this.onReloadGridClick
		    },
			 "useritemsview grid#usergroups" : {
		            /**
					 * @listener useritemsview-grid-usergrid-selectionchange triggered-by:
					 *           {@link RODAdmin.view.user.UserItemsview UserItemsview}
					 *           grid#usergrid
					 *           {@link #onListSelectionChange}
					 */		        	
			        selectionchange : this.onListSelectionChange,
		        // itemcontextmenu : this.onItemContextMenu
		        },
		        "groupdetails panel#groupinfo toolbar button#groupedit" :{
		        	click : this.onGroupEditClick
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
			        
			        
//			        console.log(groupitem);
////			        var profile = useritem.profileStore.getAt(0);
			        groupinfo.update(groupitem);
//			        // var rec = useritem.profile().getAt(0);
////			        userprofile.update(profile);
		        }
	        }
	    });
     
        
    }

});