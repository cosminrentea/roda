/**
 * 
 */
Ext.define('RODAdmin.controller.cms.Cmspages', {
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
        },
        {	
        	ref : 'pagetree',
        	selector: 'pagesitemsview treepanel#pgfolderview'
        	
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
   	this.listen({
          controller: {
              '*': {
                   controllerCmspagesInitView: this.initView
              }
          }
  	 });
	},

    /**
	 * @method
	 */
    initView : function() {
    	console.log('InitView, baby');	
    	Ext.History.add('menu-cmspages');    	
  //  	 this.getPagetree().store.load();
    },
	
	/**
	 * @method
	 */
    onpagetoolbarEditClick : function(button, e, options) {
    	console.log('see ma famly again onpagetoolbarEditClick');
	    var fp = this.getPageproperties().data;
	    console.log(fp);
	    var win = Ext.WindowMgr.get('pageedit');
	    if (!win) {
		    win = Ext.create('RODAdmin.view.cms.page.EditPageWindow');
 	   	}	    
	    win.setTitle('Edit Page "' + fp.data.title + '" (id: ' + fp.data.indice + ')');
	    win.down('form').getForm().loadRecord(fp);
	    console.log(fp.data);
	    win.down('form').down('hiddenfield[name=id]').setValue(fp.data.indice);	    
	    win.down('form').down('combobox[name=layout]').store.load();
	    win.down('form').down('combobox[name=layout]').setValue(fp.data.layoutid);
	    win.down('form').down('combobox[name=published]').store.load();
	    win.down('form').down('combobox[name=published]').setValue(fp.data.published);
	    win.down('form').down('combobox[name=pagetype]').store.load();
	    win.down('form').down('combobox[name=pagetype]').setValue(fp.data.pagetypeid);
	    win.show();
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