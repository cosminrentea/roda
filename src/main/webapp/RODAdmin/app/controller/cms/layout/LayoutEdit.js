Ext.define('RODAdmin.controller.cms.layout.LayoutEdit', {
    extend : 'Ext.app.Controller',

    views : [
	    "cms.layout.EditLayoutWindow"
    ],

    refs : [
            {
                ref : 'itemsview',
                selector : 'layoutitemsview'
            }, {
                ref : "folderview",
                selector : "layoutitemsview treepanel#lyfolderview"
            }, {
                ref : 'folderselect',
                selector : 'layoutedit treepanel#groupselect'
            }
    ],

    init : function(application) {
	    this.control({
	        "layoutedit treepanel#groupselect" : {
	            load : this.folderLoad, // this is the only event fired
	            // after loading the store in a
	            // tree view, apparently. This
	            // is REALLY stupid because it
	            // is probabily fired multiple
	            // times.
	            cellclick : this.onGroupselectCellClick
	        },
	        "layoutedit button#save" : {
		        click : this.onLayoutEditSaveClick
	        },
	        "layoutgadd button#save" : {
		        click : this.onLayoutAddGroupSaveClick
	        },
	        "lygroupadd button#save" : {
		        click : this.onGroupSaveClick

	        }
	    });
    },

    onGroupSaveClick : function(button, e, options) {
	    var win = button.up('window');
	    var formPanel = win.down('form');
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : 'http://localhost:8080/roda/admin/layoutgroupsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Layout group saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'lyfolderview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'lyiconview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutList').onReloadTreeClick();
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

    onLayoutEditSaveClick : function(button, e, options) {
	    // ok, now we're here. Let's save the little fucker.
	    // first, we need to find the window
	    var win = button.up('window');
	    // then, the form
	    var formPanel = win.down('form');
	    // we will need to reload the store, this is tricky since we need to
		// determine which view is active. But we'll leave this for later
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var folderview = this.getFolderview()
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : 'http://localhost:8080/roda/admin/layoutsave',

		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Layout saved.');
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'lyfolderview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'lyiconview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutList').onReloadTreeClick();
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

    onLayoutAddGroupSaveClick : function(button, e, options) {
	    console.log('small step for man...');
	    // ok, now we're here. Let's save the little fucker.
	    // first, we need to find the window
	    var win = button.up('window');
	    // then, the form
	    var formPanel = win.down('form');
	    // we will need to reload the store, this is tricky since we need to
		// determine which view is active. But we'll leave this for later
	    var me = this;
	    if (formPanel.getForm().isValid()) {
		    formPanel.getForm().submit({
		        clientValidation : true,
		        url : 'http://localhost:8080/roda/admin/layoutsave',
		        success : function(form, action) {
			        var result = action.result;
			        if (result.success) {
				        RODAdmin.util.Alert.msg('Success!', 'Layout saved.');
				        // store.load();
				        win.close();
				        var active = me.getItemsview().layout.getActiveItem();
				        if (active.itemId == 'lyfolderview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutTree').onReloadTreeClick();
				        }
				        else if (active.itemId == 'lyiconview') {
					        me.getController('RODAdmin.controller.cms.layout.LayoutList').onReloadTreeClick();
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

    folderLoad : function(component, options) {

	    console.log('folderload inside edit window');

	    var active = this.getItemsview().layout.getActiveItem();

	    console.log(active);

	    console.log(active.itemId);

	    // ?? :)
	    var pnode = active.getSelectionModel().getLastSelected();

	    // var pnode = this.getCurrentNode();
	    // var gwin =Ext.ComponentQuery.query('fileedit')
	    // var gwin = Ext.getCmp('fileedit');
	    // console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');
	    // console.log(gwin);
	    // console.log(gwin.cnode);
	    // console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');
	    // var pnode = gwin.getCnode();
	    // console.log(pnode);
	    // console.log('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++');

	    var rnode = this.getFolderselect().getRootNode();
	    var cnode = rnode.findChild('id', pnode.data.folderid, true);
	    if (cnode != null) {
		    this.getFolderselect().getSelectionModel().select(cnode);
	    }
    },

    onGroupselectCellClick : function(component, td, cellIndex, record, tr, rowIndex, e, eOpts) {

	    component.up('layoutedit').down('form').down('fieldset').query('displayfield')[0].setValue(record.data.name + '('+record.data.indice+')');
	    component.up('layoutedit').down('hiddenfield#groupid').setValue(record.data.indice);
//	    component.up('layoutedit').down('fieldset').query('hiddenfield#groupid')[0].setValue(record.data.id);
    
    },

});