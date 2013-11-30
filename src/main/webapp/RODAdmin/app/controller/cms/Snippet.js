Ext.define('RODAdmin.controller.cms.Snippet', {
    extend : 'Ext.app.Controller',

    
    views : [
             'cms.snippet.Snippets', 'cms.snippet.SnippetItemsview', 'cms.snippet.SnippetDetails',
             'cms.snippet.details.SnippetProperties',
//             "cms.snippet.EditSnippetWindow",
             'cms.snippet.SnippetContextMenu',
             'cms.snippet.SnippetGroupContextMenu',
//             'cms.snippet.AddSnippetToGroupWindow',
//             'cms.snippet.SnippetItemviewContextMenu',
             'cms.snippet.details.SnippetUsage'
     ],
    
    refs : [
            {
                ref : 'itemsview',
                selector : 'snippetitemsview'
            }, {
                ref : "folderview",
                selector : "snippetitemsview treepanel#snfolderview"
            }, {
                ref : 'folderselect',
                selector : 'snippetedit treepanel#folderselect'
            }, {
                ref : 'snippetproperties',
                selector : 'snippetproperties'
            }

    ],

    init : function(application) {
	    this.control({
	        "cmssnippets toolbar button#icon-view" : {
		        click : this.onIconViewClick
	        },
	        "cmssnippets toolbar button#tree-view" : {
		        click : this.onTreeViewClick
	        },
	        "snippetproperties toolbar#snproptoolbar button#editsnippet" : {
		        click : this.onsntoolbarEditClick
	        },
	        "snippetproperties toolbar#snproptoolbar button#deletesnippet" : {
		        click : this.onsntoolbarDeleteClick
	        },
	        "layoutproperties toolbar#lyproptoolbar button#getsnippetaudit" : {
		        click : this.onsntoolbarAuditClick
	        },
	    });
    },

    init : function(application) {
	    this.control({
	        "cmssnippets toolbar button#icon-view" : {
		        click : this.onIconViewClick
	        },
	        "cmssnippets toolbar button#tree-view" : {
		        click : this.onTreeViewClick
	        },
	        "snippetproperties toolbar#snproptoolbar button#editsnippet" : {
		        click : this.onsntoolbarEditClick
	        },
	        "snippetproperties toolbar#snproptoolbar button#deletesnippet" : {
		        click : this.onsntoolbarDeleteClick
	        },
	        "snippetproperties toolbar#snproptoolbar button#getsnippetaudit" : {
		        click : this.onsntoolbarAuditClick
	        },

	    });
    },

    onIconViewClick : function(button, e, options) {
	    console.log('onIconviewClick');
	    this.getItemsview().layout.setActiveItem('sniconview');
	    var store = Ext.StoreManager.get('cms.snippet.Snippet');
	    store.load();
    },

    onTreeViewClick : function(button, e, options) {
	    console.log('onfolderviewClick');
	    this.getItemsview().layout.setActiveItem('snfolderview');
	    var store = Ext.StoreManager.get('cms.snippet.SnippetTree');
	    store.load();
    },
    onsntoolbarEditClick : function(button, e, options) {
	    var fp = this.getSnippetproperties().data;
	    var win = Ext.create('RODAdmin.view.cms.snippet.EditSnippetWindow');
	    win.setTitle('Edit File "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    var wtree = win.down('treepanel');
	    win.show();
	    win.down('form').getForm().loadRecord(fp);
    },

    onsntoolbarDeleteClick : function(button, e, options) {
	    var fp = this.getSnippetproperties().data;

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
				        Ext.StoreManager.get('cms.snippet.Snippet').load();
				        Ext.StoreManager.get('cms.snippet.SnippetTree').load();
				        //imediat ce aflam cum dracu se selecteaza un nod, trebuie sa invatam sa selectam nodul superior celui pe care l-am sters				        

			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
	    // event.stopEvent();
    },

    onsntoolbarAuditClick : function(button, e, options) {
	    console.log('auditfile clicked, cool stuff ahead');
	    var fp = this.getSnippetproperties().data;
	    var win = Ext.create('RODAdmin.view.common.AuditWindow');
	    win.setTitle('Audit data for "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    win.show();
	    var auditgrid = win.down('grid[itemId=auditgrid]');
	    auditgrid.store.load();
    },


//
});