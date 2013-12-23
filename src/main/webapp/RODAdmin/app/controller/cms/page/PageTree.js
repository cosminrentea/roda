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
//	            itemcontextmenu : this.onTreeContextMenu
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
    }
});
    