Ext.define('RODAdmin.controller.metadata.mcomponents.Org', {
    extend : 'Ext.app.Controller',
     refs : [
             {	
             	ref : 'ourgrid',
             	selector: 'mcorgs'
             }
      
     ],
     init : function(application) {
 	    this.control({
 	    	 "mcorgs toolbar button#add" : {
 		        click : this.onAddClick
 	        },
			'mcorgs' : {
				deleteRecord : this.onDeleteClick,
				editRecord : this.onEditClick
			},
	        "editorg button#save" : {
	        	 /**
				 * @listener button-save-click triggered-by:
				 *           {@link RODAdmin.view.cms.layout.EditLayoutWindow EditLayoutWindow} button#save    
				 *           executes {@link #onLayoutEditSaveClick}  
				 */	        	
		        click : this.onSaveClick
	        },
 	    });
     	this.listen({
             controller: {
                 '*': {
                     controllerCmsnewsInitView: this.initView
                 }
             }
     	 });
     },
     
     initView : function() {
     	console.log('InitView, baby');	
     },
     

     onAddClick : function() {
    	 console.log('Add organisation');
    	 win = Ext.WindowMgr.get('editorg');
    	 if (!win) {
    		 win = Ext.create('RODAdmin.view.metadata.mcomponents.AddOrg');
   	     }
    	 win.setTitle('Add a new organisation');
    	 win.show();    	 
     },

     onEditClick : function(grid, record, rowIndex,row) {
    	 win = Ext.WindowMgr.get('editprefix');
    	 if (!win) {
    		 win = Ext.create('RODAdmin.view.metadata.mcomponents.AddOrg');
   	     }
    	 win.setTitle('Edit organisation');
    	 win.setEditId(record.data.id);
    	 win.down('form').getForm().loadRecord(record);
    	 win.show();   
     },

     onDeleteClick : function(grid, record, rowIndex,row) {
    	 console.log('delete organisation');
    	    var me = this;
    	    var myid = record.data.id;
    	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the organisation ' + record.data.name
    	            + '?', function(id, value) {
    	            if (id === 'yes') {
    	                    console.log('we will delete');
    	                    Ext.Ajax.request({
    	                        url : RODAdmin.util.Globals.baseurl + 'adminjson/droporganisation',
    	                        method : "POST",
    	                        params : {
    	                                prefixid : record.data.indice
    	                        },
    	                        success : function() {
    	                                me.getFolderview().store.load();
    	                                RODAdmin.util.Alert.msg('Success!', 'Organisation deleted.');

    	                        },
    	                        failure : function(response, opts) {
    	                             switch (action.failureType) {
    	                             case Ext.form.action.Action.CLIENT_INVALID:
    	                                     Ext.Msg.alert('Failure', 'Form fields may mot be submitted with invalid values');
    	                                     break;

    	                             case Ext.form.action.Action.CONNECT_FAILURE:
    	                                     Ext.Msg.alert('Failure', 'doesn\'t work');
    	                                     break;

    	                             case Ext.form.action.Action.SERVER.INVALID:
    	                                     Ext.Msg.alert('Failure', action.result.msg);
    	                                     break;
    	                             }
    	                        }
    	                    });
    	            }
    	    }, this);
    	    event.stopEvent();
     },
     
     onSaveClick : function(button, e, options) {
    	 var win = button.up('window');
    	 var editId = win.getEditId();
    	 if (editId > 0) {
    		 var purl = RODAdmin.util.Globals.baseurl + 'adminjson/orgsave/' + editId
    	 } else {
    		 var purl = RODAdmin.util.Globals.baseurl + 'adminjson/orgsave'
    	 }
         var formPanel = win.down('form');
         var me = this;
         if (formPanel.getForm().isValid()) {
                 formPanel.getForm().submit({
                     clientValidation : true,
                     url : purl,
                     success : function(form, action) {
                             var result = action.result;
                             if (result.success) {
                                     RODAdmin.util.Alert.msg('Success!', 'Organisation saved.');
                                     win.close();
                                     var active = me.getItemsview().layout.getActiveItem();
                                     me.onReloadGridClick();
                             }
                             else {
                                     RODAdmin.util.Util.showErrorMsg(result.msg);
                             }
                     },
                     failure : function(form, action) {
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
     onReloadGridClick : function(button, e, options) {
         var grid = this.getOurgrid();
         var currentNode = grid.getSelectionModel().getLastSelected();
         var mmstore = this.getFolderview().store;
         var me = this;
         mmstore.reload({
                 callback : function(records, operation, success) {
                         if (currentNode != null) {
                        	 //select 	
                         }
                 }
         });

 },

     
     
});
     
     