Ext.define('RODAdmin.controller.cms.Pages', {
    extend: 'Ext.app.Controller',

    stores : [
              'cms.pages.PageTree'
      ],    
    
    views: [
        'cms.page.Pages','cms.page.PagesItemsview'
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
	        click : this.onpagetoolbarEditClick
        },
        "pagedetails toolbar#pgproptoolbar button#deletepage" : {
	        click : this.onpagetoolbarDeleteClick
        },
        "pagedetails toolbar#pgproptoolbar button#getpageaudit" : {
	        click : this.onpagetoolbarAuditClick
        },
    });
	},

    onpagetoolbarEditClick : function(button, e, options) {
	    var fp = this.getPageproperties().data;
	    var win = Ext.create('RODAdmin.view.cms.page.EditPageWindow');
	    win.setTitle('Edit Page "' + fp.data.name + '" (id: ' + fp.data.id + ')');
//	    var wtree = win.down('treepanel');
	    win.show();
	    win.down('form').getForm().loadRecord(fp);
    },

    onpagetoolbarDeleteClick : function(button, e, options) {
    	console.log('delete page here');
    },

    onpagetoolbarAuditClick : function(button, e, options) {
    	console.log('audit page here');
    },

    

});