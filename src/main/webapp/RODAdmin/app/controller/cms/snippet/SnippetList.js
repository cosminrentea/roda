/**
Controller care se ocupa de lista de snippeturi {@link RODAdmin.view.cms.snippet.SnippetItemsview SnippetItemsview}
 */
Ext.define('RODAdmin.controller.cms.snippet.SnippetList', {
	extend : 'Ext.app.Controller',

    stores : [
              'cms.snippet.SnippetTree',
              'cms.snippet.SnippetItem',
              'cms.snippet.SnippetGroup',
              'cms.snippet.Snippet',
              'common.Audit'
	      ],			

     views : [
          'RODAdmin.view.cms.snippet.SnippetItemviewContextMenu',
	       ],
			
	refs : [ 
	        {
	    		ref : 'iconview',
	    		selector : 'snippetitemsview grid#sniconview'
	    	},	         
	    	 {
                ref : 'sndetailspanel',
                selector : 'cmssnippets panel#sndetailscontainer '
            },{
                ref : 'snusagepanel',
                selector : 'snippetusage'
            },{
                ref : 'snippetproperties',
                selector : 'snippetproperties panel#sndata '
            },{
                ref : 'sncontent',
                 selector : 'snippetproperties panel#snenvelope codemirror#sncontent'
            },{
                ref : 'snenvelope',
                 selector : 'snippetproperties panel#snenvelope'
            }
            ],
   	init : function(application) {
        		this.control({
        			"snippetitemsview grid#sniconview" : {
        	            /**
        				 * @listener snippetitemsview-grid-sniconview-selectionchange triggered-by:
        				 *           {@link RODAdmin.view.cms.snippet.SnippetItem SnippetItem}
        				 *           grid#sniconview
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
//        			"snippetitemviewcontextmenu menuitem#icdeletesnippet" : {
//        				click : this.onDeleteSnippetClick
//        			},
//        			"snippetitemviewcontextmenu menuitem#iceditsnippet" : {
//        				click : this.onEditSnippetClick
//        			}
        		});
   	},

   	/**
   	 * @method
   	 * Triggered at grid selection change.
   	 */
	onIconViewSelectionChange : function(component, selected, event) {
		var record = selected[0];
		var snprop = this.getSnippetproperties();
		var sndetails = this.getSndetailspanel();
		var sncontent = this.getSncontent();
	    var snenvelope = this.getSnenvelope();
		var snusage = this.getSnusagepanel();
		sndetails.setTitle(record.data.name);
		snusage.expand();
		snenvelope.expand();
		var snitemstore = this.getCmsSnippetSnippetItemStore();
		
		snitemstore.load({
			id : record.data.indice, //set the id here
			scope : this,
			callback : function(records, operation, success) {
				if (success) {
					var snitem = snitemstore.first();
					snprop.update(snitem);
					sncontent.setValue(snitem.data.content);
			        if (typeof snitem.usageStore === 'object') {
			        	snusage.bindStore(snitem.usageStore);	
			        }					
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
			this.contextmenu = Ext.create('widget.snippetitemviewcontextmenu');
		}
		this.contextmenu.showAt(e.getXY());
	},
	
	
	
});