Ext.define('RODAdmin.controller.cms.snippet.SnippetList', {
	extend : 'Ext.app.Controller',

    stores : [
              'cms.snippet.SnippetTree', 'cms.snippet.SnippetItem', 'cms.snippet.SnippetGroup', 'cms.snippet.Snippet',
              'common.Audit'
	      ],			

     views : [
//          'cms.snippet.Snippets', 'cms.snippet.SnippetItemsview', 'cms.snippet.SnippetDetails',
//          'cms.snippet.details.SnippetProperties', "cms.snippet.EditSnippetWindow", 'cms.snippet.SnippetContextMenu',
//          'cms.snippet.SnippetGroupContextMenu', 'cms.snippet.AddSnippetToGroupWindow',
          'cms.snippet.SnippetItemviewContextMenu',
//          'cms.snippet.details.SnippetUsage'
	       ],
	      
			
	refs : [ 
	        {
	    		ref : 'iconview',
	    		selector : 'snippetitemsview grid#sniconview'
	    	},	         
	    	 {
                ref : 'sndetailspanel',
                selector : 'cmssnippets panel#sndetailscontainer '
            }, {
                ref : 'snusagepanel',
                selector : 'snippetusage'
            }, {
                ref : 'snippetproperties',
                selector : 'snippetproperties'
            }

            ],
   	init : function(application) {
        		this.control({
        			"snippetitemsview grid#sniconview" : {
        				selectionchange : this.onIconViewSelectionChange,
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

	onIconViewSelectionChange : function(component, selected, event) {
		var record = selected[0];
		var snprop = this.getSnippetproperties();
		var sndetails = this.getSndetailspanel();
		var snusage = this.getSnusagepanel();
		sndetails.setTitle(record.data.name);
		snusage.expand(true);
		var snitemstore = this.getCmsSnippetSnippetItemStore();
		
		snitemstore.load({
			id : record.data.indice, //set the id here
			scope : this,
			callback : function(records, operation, success) {
				if (success) {
					var snitem = snitemstore.first();
					snprop.update(snitem);
					if (!typeof snitem.snippetusageStore === 'undefined') {
						snusage.bindStore(snitem.snippetusageStore);
					}
				}
			}
		});
	},

	onItemContextMenu : function(component, record, item, index, e) {
		e.stopEvent();
		if (!this.contextmenu) {
			this.contextmenu = Ext.create('widget.snippetitemviewcontextmenu');
		}
		this.contextmenu.showAt(e.getXY());
	},
	
	
	
});