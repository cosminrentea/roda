/**
 * 
 */
Ext.define('RODAdmin.controller.cms.page.PageTree', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.pages.PageTree',
            'cms.pages.PageItem',
            'common.Audit'
    ],

    views : [
            'RODAdmin.view.cms.page.Pages',
            'RODAdmin.view.cms.page.PagesItemsview',
            'RODAdmin.view.cms.page.PageDetails',
            'RODAdmin.view.cms.page.details.PageProperties', 
            'RODAdmin.view.cms.page.PageContextMenu',
            'RODAdmin.view.cms.page.PageContextMenuDisabled',            
    ],

    refs : [
            {
                ref : 'pgdetailspanel',
                selector : 'cmspages panel#pgdetailscontainer '
            }, 
            {
                ref : 'pageproperties',
                selector : 'pageproperties'
            },
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
	            itemcontextmenu : this.onTreeContextMenu
	        },
	        "pagecontextmenu menuitem#deletepage" : {
	            /**
				 * @listener layoutcontextmenu-menuitem-deletepage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#deletepage
				 *           {@link #onDeletePageClick}
				 */	        	
		        click : this.onDeletePageClick
	        },	 
	        "pagecontextmenu menuitem#editpage" : {
	            /**
				 * @listener layoutcontextmenu-menuitem-editpage triggered-by:
				 *           {@link RODAdmin.view.cms.page.PageContextMenu PageContextMenu}
				 *           menuitem#editpage
				 *           {@link #onEditPageClick}
				 */	        	
		        click : this.onEditPageClick
	        },	

	    });
    },
    /**
	 * @method
	 */
    onPageviewSelectionChange : function(component, selected, event) {
	    console.log('pageviewselectionchange');
	    var record = selected[0];
	    var pageprop = this.getPageproperties();
	    console.log(pageprop);
	    var pgdetails = this.getPgdetailspanel();
	    pgdetails.setTitle(record.data.title);

	    var pgitemstore = Ext.StoreManager.get('cms.pages.PageItem');
		    pgitemstore.load({
		    	id : record.data.indice, 
		        scope : this,
		        callback : function(records, operation, success) {
			        if (success) {
				        var pgitem = pgitemstore.first();
				        pageprop.update(pgitem);
			        }
		        }
		    });
    },
    /**
	 * @method
	 */
    onTreeContextMenu : function(component, record, item, index, e) {
	    e.stopEvent();
		    if (this.itemmenu) {
		    	this.itemmenu.destroy();
		    }
		    	//aici sa vedem daca pagina are pagini subordonate
//		    	console.log(record.childNodes.length);
		    	

			    var subordinates = record.childNodes.length;
//			    this.itemmenu.items.each(function(item){
//			    	if (item.itemId = 'deletepage') {
					    if (subordinates > 0) {
					    	console.log('has shit under');
						    this.itemmenu = Ext.create('widget.pagecontextmenudisabled');
					    } else if (subordinates == 0) {
						    this.itemmenu = Ext.create('widget.pagecontextmenu');					    	
					    }
//			    	}
//			    });
		    this.itemmenu.showAt(e.getXY());
    },
    /**
	 * @method
	 */
    onDeletePageClick : function(component, event) {
	    var currentNode = this.getFolderview().getSelectionModel().getLastSelected();
	    var store = Ext.StoreManager.get('cms.page.Page');
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the page ' + currentNode.data.title
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    Ext.Ajax.request({
			        url : 'http://localhost:8080/roda/admin/pagedrop',
			        method : "POST",
			        params : {
				        id : currentNode.data.indice
			        },
			        success : function() {
				        RODAdmin.util.Alert.msg('Success!', 'Layout deleted.');
				        store.load;
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
    onEditPageClick : function(button, e, options) {
	    var fp = this.getPageproperties().data;
	    var win = Ext.create('RODAdmin.view.cms.page.EditPageWindow');
	    win.setTitle('Edit Page "' + fp.data.title + '" (id: ' + fp.data.id + ')');
	    win.show();
	    win.down('form').getForm().loadRecord(fp);
    },
});
    