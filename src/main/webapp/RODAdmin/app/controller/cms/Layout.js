Ext.define('RODAdmin.controller.cms.Layout', {
    extend : 'Ext.app.Controller',

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
            }, {
                ref : 'layoutproperties',
                selector : 'layoutproperties'
            }

    ],

    init : function(application) {
	    this.control({
	        "cmslayouts toolbar button#icon-view" : {
		        click : this.onIconViewClick
	        },
	        "cmslayouts toolbar button#tree-view" : {
		        click : this.onTreeViewClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#editlayout" : {
		        click : this.onlytoolbarEditClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#deletelayout" : {
		        click : this.onlytoolbarDeleteClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#getlayoutaudit" : {
		        click : this.onlytoolbarAuditClick
	        },
	    });
    },

    onIconViewClick : function(button, e, options) {
	    console.log('onIconviewClick new controller');
	    this.getItemsview().layout.setActiveItem('lyiconview');
	    var store = Ext.StoreManager.get('cms.layout.Layout');
	    store.load();
    },

    onTreeViewClick : function(button, e, options) {
	    console.log('onfolderviewClick new controller');
	    this.getItemsview().layout.setActiveItem('lyfolderview');
	    var store = Ext.StoreManager.get('cms.layout.LayoutTree');
	    store.load();
    },

    onlytoolbarEditClick : function(button, e, options) {
	    var fp = this.getLayoutproperties().data;
	    var win = Ext.create('RODAdmin.view.cms.layout.EditLayoutWindow');
	    win.setTitle('Edit File "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    var wtree = win.down('treepanel');
	    win.show();
	    win.down('form').getForm().loadRecord(fp);
    },

    onlytoolbarDeleteClick : function(button, e, options) {
	    console.log('editfile clicked');
	    var fp = this.getLayoutproperties().data;

	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the ' + fp.data.itemtype + ' '
	            + fp.data.name + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    var url = '';
			    if (fp.data.itemtype == 'layoutgroup') {
				    url = 'http://localhost:8080/roda/admin/layoutgroupdrop';
				    parms = {'groupid' : fp.data.id };
			    }
			    else {
				    url = 'http://localhost:8080/roda/admin/layoutdrop';
				    parms = {'layoutid' : fp.data.id };
			    }

			    Ext.Ajax.request({
			        url : url,
			        method : "POST",
			        params : parms,	
			        success : function() {
				        RODAdmin.util.Alert.msg('Success!', 'Deleted.');
				        Ext.StoreManager.get('cms.layout.Layout').load();
				        Ext.StoreManager.get('cms.layout.LayoutTree').load();
			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
    },

    onlytoolbarAuditClick : function(button, e, options) {
	    console.log('auditfile clicked, cool stuff ahead');
	    var fp = this.getLayoutproperties().data;
	    var win = Ext.create('RODAdmin.view.common.AuditWindow');
	    win.setTitle('Audit data for "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    win.show();
	    var auditgrid = win.down('grid[itemId=auditgrid]');
	    auditgrid.store.load();
	    console.log(auditgrid.store);
    },

});