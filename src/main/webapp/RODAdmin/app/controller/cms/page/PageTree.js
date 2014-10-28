/**
 * 
 */
Ext.define('RODAdmin.controller.cms.page.PageTree', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.pages.PageTree', 'cms.pages.PageItem', 'common.Audit', 'local.PagePublished'
    ],

    views : [
            'RODAdmin.view.cms.page.Pages', 'RODAdmin.view.cms.page.PagesItemsview',
            'RODAdmin.view.cms.page.PageDetails', 'RODAdmin.view.cms.page.details.PageProperties',
            'RODAdmin.view.cms.page.details.PageCode', 'RODAdmin.view.cms.page.PageContextMenu',
            'RODAdmin.view.cms.page.PageContextMenuDisabled',
    ],

    refs : [
            {
                ref : 'pgdetailspanel',
                selector : 'cmspages panel#pgdetailscontainer '
            }, {
                ref : 'pageproperties',
                selector : 'pageproperties'
            }, {
                ref : 'pagecode',
                selector : 'pagecode codemirror#pagecontent'
            }, {
                ref : "folderview",
                selector : "pagesitemsview treepanel#pgfolderview"
            }

    ],
    /**
	 * @method
	 */
    init : function(application) {
	    this.control({
	        "pagesitemsview treepanel#pgfolderview" : {
	            /**
				 * @listener pagesitemsview-treepanel-pgfolderview triggered-by:
				 *           {@link RODAdmin.view.cms.page.PagesItemsview PagesItemsview}
				 *           treepanel#pgfolderview
				 *           {@link #onPageviewSelectionChange}
				 */
	            selectionchange : this.onPageviewSelectionChange,
	            itemcontextmenu : this.onTreeContextMenu,
	        },
	        "pagecontextmenu menuitem#deletepage" : {
		        /**
				 * @listener layoutcontextmenu-menuitem-deletepage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#deletepage {@link #onDeletePageClick}
				 */
		        click : this.onDeletePageClick
	        },
	        "pagecontextmenu menuitem#editpage" : {
		        /**
				 * @listener layoutcontextmenu-menuitem-editpage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#editpage {@link #onEditPageClick}
				 */
		        click : this.onEditPageClick
	        },
	        "pagecontextmenudisabled menuitem#editpage" : {
		        /**
				 * @listener layoutcontextmenu-menuitem-editpage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#editpage {@link #onEditPageClick}
				 */
		        click : this.onEditPageClick
	        },
	        
	        "pagecontextmenu menuitem#addpage" : {
		        /**
				 * @listener layoutcontextmenu-menuitem-editpage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#editpage {@link #onEditPageClick}
				 */
		        click : this.onAddPageClick
	        },
	        "pagecontextmenudisabled menuitem#addpage" : {
		        /**
				 * @listener layoutcontextmenu-menuitem-editpage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#editpage {@link #onEditPageClick}
				 */
		        click : this.onAddPageClick
	        },

	        "pagecontextmenu menuitem#clearcache" : {
		        /**
				 * @listener layoutcontextmenu-menuitem-editpage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#editpage {@link #onEditPageClick}
				 */
		        click : this.onClearCacheClick
	        },
	        "pagecontextmenudisabled menuitem#clearcache" : {
		        /**
				 * @listener layoutcontextmenu-menuitem-editpage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#editpage {@link #onEditPageClick}
				 */
		        click : this.onClearCacheClick
	        },	        
	        "pagesitemsview treepanel#pgfolderview toolbar button#reloadtree" : {
		        /**
				 * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-reloadtree
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
				 *           treepanel#lyfolderview toolbar button#reloadtree
				 *           {@link #onReloadTreeClick}
				 */
		        click : this.onReloadTreeClick
	        },
	        "pagesitemsview treepanel#pgfolderview toolbar button#collapsetree" : {
		        /**
				 * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-collapsetree
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
				 *           treepanel#lyfolderview toolbar button#collapsetree
				 *           {@link #onCollapseTreeClick}
				 */
		        click : this.onCollapseTreeClick
	        },
	        "pagesitemsview treepanel#pgfolderview toolbar button#expandtree" : {
		        /**
				 * @listener layoutitemsview-treepanel-lyfolderview-toolbar-button-expandtree
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.layout.LayoutItemview LayoutItemsview}
				 *           treepanel#lyfolderview toolbar button#expandtree
				 *           {@link #onExpandTreeClick}
				 */
		        click : this.onExpandTreeClick
	        },
	        "pagesitemsview treepanel#pgfolderview > treeview" : {
				drop: this.onTreeMDrop
			},
  		    "pagesitemsview treepanel#pgfolderview checkcolumn" : {
				checkchange: this.onCheckChange
			},

	    });
    },
    
    onCheckChange : function(column, rowIndex, checked){ 
    	console.log('checkchange in controller');
    	
	    Ext.Ajax.request({
	        url : RODAdmin.util.Globals.baseurl + '/adminjson/cmspagenavigable',
	        method : "POST",
	        params : {
	        	navigable : checked,
		        id : rowIndex
	        },
	        failure : function(response, opts) {
		        Ext.Msg.alert('Failure', response);

	        }
	    });

    },
    
    /**
	 * @method 
	 * 
	 * drop event for page tree
	 */
    onClearCacheClick : function(button, e, options) {
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var pgtitle = currentNode.data.title;
	    var store = Ext.StoreManager.get('cms.pages.PageTree');
	    Ext.Msg.confirm('Clear Cache', 'Are you sure you want to clear the cache for ' + pgtitle
	            + '?', function(id, value) {
		    if (id === 'yes') {
		    	console.log('what?' + currentNode.data.indice);
			    Ext.Ajax.request({
			        url : RODAdmin.util.Globals.baseurl + '/adminjson/cache/evict-page-id/'+currentNode.data.indice,
			        method : "POST",
			        success : function(response,request) {
				           var responseJson = Ext.decode(response.responseText);
				            if (responseJson.success === true) {
				            	RODAdmin.util.Alert.msg('Success!', 'Cache for '+ pgtitle +' has been purged.');
								store.load();
				            } else {
				            	RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);

				            }
			        },
			        failure : function(response, opts) {
				        Ext.Msg.alert('Failure', response);

			        }
			    });
		    }
	    }, this);
//	    event.stopEvent();
    
    },
    
    /**
	 * @method 
	 * 
	 * drop event for page tree
	 */
    onTreeMDrop : function(node,data,overModel,dropPosition) {
    	console.log('id ' + data.records[0].data.indice + ' group ' + overModel.data.indice + ' ' + dropPosition );
    	var pgtitle = data.records[0].data.title;
    	var pgid = 	data.records[0].data.indice;
    	var groupid = overModel.data.indice;
    	var mode = dropPosition;
    	Ext.Ajax.request({
	        url : RODAdmin.util.Globals.baseurl + '/adminjson/cmspagemove/',
	        method : "POST",
	        params : {
	            id : pgid,
	            group: groupid,
	            mode: mode,
	        },
	        success : function(response,request) {
		           var responseJson = Ext.decode(response.responseText);
		            if (responseJson.success === true) {
		                // whatever stuff needs to happen on success
		            	RODAdmin.util.Alert.msg('Success!', 'Page '+ pgtitle +' moved.');
//						store.load();
		            } else {
		            	RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);

		            }
	        },
	        failure : function(response, opts) {
		        Ext.Msg.alert('Failure', response);

	        }
	    });		
    	this.getFolderview().store.load();
    },
    
    
    /**
	 * @method
	 */
    onPageviewSelectionChange : function(component, selected, event) {
	    var pageprop = this.getPageproperties();
	    var pgcode = this.getPagecode();
	    var pgdetails = this.getPgdetailspanel();
	    var record = selected[0];
	    if (record != null) {
		    pgdetails.setTitle(record.data.title);
		    var pgitemstore = Ext.StoreManager.get('cms.pages.PageItem');
		    pgitemstore.load({
		        id : record.data.indice,
		        scope : this,
		        callback : function(records, operation, success) {
			        if (success) {
				        var pgitem = pgitemstore.first();
				        pageprop.update(pgitem);
				        pgcode.setValue(pgitem.data.content);
			        }
		        }
		    });
	    } else {
		    pgdetails.setTitle('');
		    pageprop.update('');
	        pgcode.setValue('');
	    }
    },
    /**
	 * @method
	 */
    onTreeContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
	    if (this.itemmenu) {
		    this.itemmenu.destroy();
	    }
	    // aici sa vedem daca pagina are pagini subordonate
	    // console.log(record.childNodes.length);

	    var subordinates = record.childNodes.length;
	    // this.itemmenu.items.each(function(item){
	    // if (item.itemId = 'deletepage') {
	    if (subordinates > 0) {
		    console.log('has shit under');
		    this.itemmenu = Ext.create('widget.pagecontextmenudisabled');
	    }
	    else if (subordinates == 0) {
		    this.itemmenu = Ext.create('widget.pagecontextmenu');
	    }
	    // }
	    // });
	    this.itemmenu.showAt(e.getXY());
    },
    /**
	 * @method
	 */
    onDeletePageClick : function(component, event) {
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var pgtitle = currentNode.data.title;
	    var store = Ext.StoreManager.get('cms.pages.PageTree');
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the page ' + pgtitle
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    Ext.Ajax.request({
			        url : RODAdmin.util.Globals.baseurl + '/adminjson/cmspagedrop',
			        method : "POST",
			        params : {
				        cmspageid : currentNode.data.indice
			        },
			        success : function(response,request) {
				           var responseJson = Ext.decode(response.responseText);
				            if (responseJson.success === true) {
				                // whatever stuff needs to happen on success
				            	RODAdmin.util.Alert.msg('Success!', 'Page '+ pgtitle +' dropped.');
								store.load();
				            } else {
				            	RODAdmin.util.Alert.msg('Failure!', responseJson.message, true);

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
    /**
	 * @method
	 */
    onAddPageClick : function(button, e, options) {
    	console.log('see ma famly again onpagetoolbarEditClick');
	    var fp = this.getPageproperties().data;
	    var win = Ext.WindowMgr.get('pageadd');
	    if (!win) {
 		    win = Ext.create('RODAdmin.view.cms.page.AddPageWindow');
 	   	}
	    win.setTitle('Add Page');
//set defaults and parent
	    win.down('form').down('hiddenfield[name=parentid]').setValue(fp.data.indice);
	    win.down('form').down('displayfield[name=parent]').setValue(fp.data.menutitle);
	    win.down('form').down('combobox[name=layout]').store.load();
	    win.down('form').down('combobox[name=layout]').setValue(fp.data.layoutid);
	    win.down('form').down('combobox[name=published]').store.load();
	    win.down('form').down('combobox[name=published]').setValue(fp.data.published);
	    win.down('form').down('combobox[name=lang]').setValue(fp.data.lang);
	    win.down('form').down('combobox[name=default]').setValue('No');
	    win.down('form').down('combobox[name=searchable]').setValue('Yes');
	    win.down('form').down('textfield[name=cacheable]').setValue('3600');	    
	    win.down('form').down('combobox[name=target]').setValue('_self');
	    
	    win.down('form').down('combobox[name=pagetype]').store.load();
	    console.log('pagetypeid' + fp.data.pagetypeid);
	    win.down('form').down('combobox[name=pagetype]').setValue(fp.data.pagetypeid);	    
	    win.show();
    },
    /**
	 * @method
	 */
    onEditPageClick : function(button, e, options) {
	    var fp = this.getPageproperties().data;
	    console.log(fp);
	    var win = Ext.WindowMgr.get('pageedit');
	    if (!win) {
		    win = Ext.create('RODAdmin.view.cms.page.EditPageWindow');
 	   	}	    
	    win.setTitle('Edit Page "' + fp.data.title + '" (id: ' + fp.data.id + ')');
	    win.down('form').getForm().loadRecord(fp);
	    console.log(fp.data);
	    win.down('form').down('hiddenfield[name=id]').setValue(fp.data.indice);	    
	    win.down('form').down('combobox[name=layout]').store.load();
	    win.down('form').down('combobox[name=layout]').setValue(fp.data.layoutid);
	    win.down('form').down('combobox[name=published]').store.load();
	    win.down('form').down('combobox[name=published]').setValue(fp.data.published);
	    win.down('form').down('combobox[name=pagetype]').store.load();
	    console.log('pagetypeid' + fp.data.pagetypeid);
	    win.down('form').down('combobox[name=pagetype]').setValue(fp.data.pagetypeid);	    
	    win.show();
    },
    /**
	 * @method
	 */
    onReloadTreeClick : function(button, e, options) {
	    console.log('reloadtree click');
	    var folderview = this.getFolderview();
	    console.log(folderview);
	    var currentNode = folderview.getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var mmstore = this.getFolderview().store;
	    var me = this;
	    mmstore.reload({
		    callback : function(records, operation, success) {
			    if (currentNode != null) {
				    var root = me.getFolderview().store.getRootNode();
				    var myid = root.findChild('indice', currentNode.data.indice, true);
				    if (myid != null) {
					    console.log(myid);
					    folderview.getSelectionModel().select(myid);
				    }
			    }
		    }
	    });

    },
    /**
	 * @method
	 */
    onCollapseTreeClick : function(button, e, options) {
    	this.getFolderview().collapseAll();
    },
    /**
	 * @method
	 */
    onExpandTreeClick : function(button, e, options) {
    	this.getFolderview().expandAll();
    },

});
