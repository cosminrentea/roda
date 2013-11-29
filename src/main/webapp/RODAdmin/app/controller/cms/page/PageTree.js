Ext.define('RODAdmin.controller.cms.page.PageTree', {
    extend : 'Ext.app.Controller',

    stores : [
            'cms.pages.PageTree', 'cms.pages.PageItem', 'common.Audit'
    ],

    views : [
            'cms.page.Pages', 'cms.page.PagesItemsview', 'cms.page.PageDetails',
            'cms.page.details.PageProperties', 
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
    
    init : function(application) {
	    this.control({
	        "pagesitemsview treepanel#pgfolderview" : {
	            selectionchange : this.onPageviewSelectionChange,
//	            itemcontextmenu : this.onTreeContextMenu
	        },

	    });
    },

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
    