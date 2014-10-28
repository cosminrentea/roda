Ext.define('RODAdmin.controller.metadata.mcomponents.Prefix', {
    extend : 'Ext.app.Controller',
     refs : [
             {	
             	ref : 'ourgrid',
             	selector: 'mcprefixes'
             }
      
     ],
     init : function(application) {
 	    this.control({
 	    	 "mcprefixes toolbar button#add" : {
 		        click : this.onAddClick
 	        },
			'mcprefixes' : {
				deleteRecord : this.onDeleteClick,
				editRecord : this.onEditClick
			},
	        "editprefix button#save" : {
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
    	 console.log('Add prefix');
    	 win = Ext.WindowMgr.get('editprefix');
    	 if (!win) {
    		 win = Ext.create('RODAdmin.view.metadata.mcomponents.AddPrefix');
   	     }
    	 win.setTitle('Add a new prefix');
    	 win.setIconCls('file_add');
    	 win.show();    	 
     },

     onEditClick : function(grid, record, rowIndex,row) {
    	 win = Ext.WindowMgr.get('editprefix');
    	 if (!win) {
    		 win = Ext.create('RODAdmin.view.metadata.mcomponents.AddPrefix');
   	     }
    	 win.setTitle('Edit prefix');
    	 win.setEditId(record.data.id);
    	 win.down('form').getForm().loadRecord(record);
    	 win.show();   
     },

     onDeleteClick : function(grid, record, rowIndex,row) {
    	 console.log('delete prefix');
    	    var me = this;
    	    var myid = record.data.id;
    	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the prefix ' + record.data.name
    	            + '?', function(id, value) {
    	            if (id === 'yes') {
    	                    console.log('we will delete');
    	                    Ext.Ajax.request({
    	                        url : RODAdmin.util.Globals.baseurl + '/adminjson/dropprefix',
    	                        method : "POST",
    	                        params : {
    	                                prefixid : record.data.indice
    	                        },
    	                        success : function() {
    	                                me.getFolderview().store.load();
    	                                RODAdmin.util.Alert.msg('Success!', 'Prefix deleted.');

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
    		 var purl = RODAdmin.util.Globals.baseurl + '/adminjson/prefixsave/' + editId
    	 } else {
    		 var purl = RODAdmin.util.Globals.baseurl + '/adminjson/prefixsave'
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
                                     RODAdmin.util.Alert.msg('Success!', 'Prefix saved.');
                                     win.close();
                                     var active = me.getItemsview().layout.getActiveItem();
                                     if (active.itemId == 'snfolderview') {
                                             me.onReloadGridClick();
                                     }
                                     else if (active.itemId == 'sniconview') {
                                             me.onReloadGridClick();
                                     }
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
     
     