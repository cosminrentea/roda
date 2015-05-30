/**
Controller care se ocupa de lista de stiri {@link RODAdmin.view.cms.news.NewsItemsview NewsItemsview}
 */
Ext.define('RODAdmin.controller.cms.news.NewsList', {
	extend : 'Ext.app.Controller',

    stores : [
              'cms.news.NewsItem',
              'cms.news.News',
              'common.Audit',

	      ],			

     views : [
          'RODAdmin.view.cms.news.NewsItemviewContextMenu',
          'RODAdmin.view.cms.news.EditNewsWindow'
	       ],
			
	refs : [ 
	        {
	    		ref : 'iconview',
	    		selector : 'newsitemsview grid#newsiconview'
	    	},	         
	    	 {
                ref : 'ndetails',
                selector : 'newsdetails panel#newsdata'
            },{
                ref : 'newscontent',
                 selector : 'newsproperties panel#newsenvelope codemirror#newscontent'
            },{
                ref : 'newsenvelope',
                 selector : 'newsproperties panel#newsenvelope'
            }
            ],
   	init : function(application) {
        		this.control({
        			"newsitemsview grid#newsiconview" : {
        	            /**
        				 * @listener newsitemsview-grid-newsiconview-selectionchange triggered-by:
        				 *           {@link RODAdmin.view.cms.news.NewsItem NewsItem}
        				 *           grid#newsiconview
        				 *           {@link #onIconViewSelectionChange}
        				 */	
        				selectionchange : this.onIconViewSelectionChange,
        	            /**
        				 * @listener snippetitemsview-grid-sniconview-itemcontextmenu triggered-by:
        				 *           {@link RODAdmin.store.cms.snippet.SnippetItem SnippetItem}
        				 *           grid#sniconview
        				 *           {@link #onItemContextMenu}
        				 */	
        				itemcontextmenu : this.onItemContextMenu
        			},
        			"newsitemviewcontextmenu menuitem#icdeletenews" : {
        				click : this.onDeleteNewsClick
        			},
        			"newsitemviewcontextmenu menuitem#iceditnews" : {
        				click : this.onEditNewsClick
        			},
        			"newsdetails toolbar button#editnews" : {
        				click : this.onEditNewsClick
        			},
        			"newsdetails toolbar button#deletenews" : {
        				click : this.onDeleteNewsClick
        			},
        			"newsitemsview grid#newsiconview toolbar button#addnews" : {
        	        	click : this.onAddNewsClick
        	        },
        			"newsitemsview grid#newsiconview toolbar button#reload" : {
        				click : this.onReloadGridClick
        	        },
        	        
        			
        		});
   	},

   	/**
   	 * @method
   	 * Triggered at grid selection change.
   	 */
	onIconViewSelectionChange : function(component, selected, event) {
		var record = selected[0];
		if (!record) {
			component.select(0);
			var record = component.getLastSelected();
		}
		var details = this.getNdetails();

		details.setTitle(record.data.title);
		var newsitemstore = this.getCmsNewsNewsItemStore();
		newsitemstore.load({
			id : record.data.id, //set the id here
			scope : this,
			callback : function(records, operation, success) {
				if (success) {
					var item = newsitemstore.first();
					console.log(item);
					details.update(item);
				}
			}
		});
	},

   	/**
   	 * @method
   	 * Triggered at context menu, loads {@link RODAdmin.view.cms.snippet.SnippetItemviewContextMenu SnippetItemviewContextMenu}
   	 * @param {Ext.Component} component 
   	 * @param {Ext.data.Model} record
   	 * @param {HTMLElement} item
   	 * @param {Number} index
   	 * @param {Ext.EventObject} e
   	 */
	onItemContextMenu : function(component, record, item, index, e) {
		e.stopEvent();
		if (!this.contextmenu) {
			this.contextmenu = Ext.create('widget.newsitemviewcontextmenu');
		}
		this.contextmenu.showAt(e.getXY());
	},
	/**
	 * @method
	 */
	onDeleteNewsClick : function(component, event) {
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    var me = this;
	    Ext.Msg.confirm('Delete Requirement', 'Are you sure you want to delete the news item ' + currentNode.data.title
	            + '?', function(id, value) {
		    if (id === 'yes') {
			    console.log('we will delete');
			    Ext.Ajax.request({
			        url : RODAdmin.util.Globals.baseurl + 'adminjson/newsdrop',
			        method : "POST",
			        params : {
				        newsid : currentNode.data.id
			        },
			        success : function() {
			        	me.getIconview().store.load();
			        	RODAdmin.util.Alert.msg('Success!', 'News item deleted.');

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
	onEditNewsClick : function(component, record, item, index, e) {
		console.log('edit news');
	    var currentNode = this.getIconview().getSelectionModel().getLastSelected();
	    var win = Ext.WindowMgr.get('newsedit');
	    if (!win) {
 		    win = Ext.create('RODAdmin.view.cms.news.EditNewsWindow');
 	   	}
	    win.setTitle('Edit News Item "' + currentNode.data.title + '" (id: ' + currentNode.data.id + ')');
	    win.show();
	    win.down('form').down('combobox[name=langId]').store.load();
	    win.down('form').getForm().loadRecord(currentNode);
	},	
	/**
	 * @method
	 */
	onAddNewsClick : function(component, record, item, index, e) {
	    var win = Ext.WindowMgr.get('newsedit');
	    if (!win) {
 		    win = Ext.create('RODAdmin.view.cms.news.EditNewsWindow');
 	   	}
	    win.setTitle('Add News Item');
	    win.show();
	},	
    /**
	 * @method
	 */
    onReloadGridClick : function(button, e, options) {
	    var iconview = this.getIconview();
	    var currentNode = iconview.getSelectionModel().getLastSelected();
	    console.log(currentNode);
	    var mmstore = this.getIconview().store;
	    var me = this;
	    mmstore.reload({
	        callback : function(records, operation, success) {
	        	console.log(currentNode);
	        	var mrr = mmstore.find('id', currentNode.data.id);
		    	iconview.getSelectionModel().select(mrr);
	        }
	    });
    },
	
});