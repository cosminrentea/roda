/**
 * 
 */
Ext.define('RODAdmin.controller.cms.Pages', {
    extend: 'Ext.app.Controller',

    stores : [
              'cms.pages.PageTree'
      ],    
    
    views: [
        'RODAdmin.view.cms.page.Pages',
        'RODAdmin.view.cms.page.PagesItemsview',
        'RODAdmin.view.cms.page.PageDetails'
    ],

    refs : [
        {
            ref : 'pageproperties',
            selector : 'pageproperties'
        }

        ],

	init : function(application) {
		this.control({
        "pagedetails toolbar#pgproptoolbar button#editpage" : {
            /**
			 * @listener pagedetails-toolbar-pgproptoolbar-button-editpage-click triggered-by:
			 *           {@link RODAdmin.view.cms.page.PageDetails PageDetails}
			 *           toolbar#pgproptoolbar button#editpage 
			 *           {@link #onpagetoolbarEditClick}
			 */		        	
	        click : this.onpagetoolbarEditClick
        },
        "pagedetails toolbar#pgproptoolbar button#deletepage" : {
            /**
			 * @listener pagedetails-toolbar-pgproptoolbar-button-deletepage-click triggered-by:
			 *           {@link RODAdmin.view.cms.page.PageDetails PageDetails}
			 *           toolbar#pgproptoolbar button#deletepage 
			 *           {@link #onpagetoolbarDeleteClick}
			 */		        	
	        click : this.onpagetoolbarDeleteClick
        },
        "pagedetails toolbar#pgproptoolbar button#getpageaudit" : {
            /**
			 * @listener pagedetails-toolbar-pgproptoolbar-button-getpageaudit-click triggered-by:
			 *           {@link RODAdmin.view.cms.page.PageDetails PageDetails}
			 *           toolbar#pgproptoolbar button#getpageaudit 
			 *           {@link #onpagetoolbarAuditClick}
			 */		        	
	        click : this.onpagetoolbarAuditClick
        },
    });
	},
    /**
	 * @method
	 */
    onpagetoolbarEditClick : function(button, e, options) {
	    var fp = this.getPageproperties().data;
	    var win = Ext.create('RODAdmin.view.cms.page.EditPageWindow');
	    win.setTitle('Edit Page "' + fp.data.title + '" (id: ' + fp.data.id + ')');
	    win.show();
	    win.down('form').getForm().loadRecord(fp);
    },
    /**
	 * @method
	 */
    onpagetoolbarDeleteClick : function(button, e, options) {
    	console.log('delete page here');
    },
    /**
	 * @method
	 */
    onpagetoolbarAuditClick : function(button, e, options) {
    	console.log('audit page here');
    },
});