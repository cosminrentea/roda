/**
 * 
 */
Ext.define('RODAdmin.controller.user.GroupList', {
    extend : 'Ext.app.Controller',

    stores : [
	    'user.Group'
    ],

    views : [
         'RODAdmin.view.user.UserItemsview'
             ],
    
    refs : [
	    {
	        ref : 'groupview',
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
		    }
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

});