/**
 * 
 */
Ext.define('RODAdmin.controller.cms.Cmsfiles', {
    extend : 'Ext.app.Controller',

    // stores : ['cms.files.FileTree', 'cms.files.FileItem', 'cms.files.File',
    // 'cms.files.FolderTree','common.Audit'],

    views : [
            'RODAdmin.view.cms.files.Files', 'RODAdmin.view.cms.files.Itemsview',
            'RODAdmin.view.cms.files.FileDetails', 'RODAdmin.view.cms.files.filedetails.FileProperties',
            'RODAdmin.view.cms.files.filedetails.SpecificProperties', 'RODAdmin.view.cms.files.FileContextMenu',
            'RODAdmin.view.cms.files.FolderContextMenu', 'RODAdmin.view.cms.files.IconviewContextMenu',
            'RODAdmin.view.cms.files.FileWindow', 'RODAdmin.view.cms.files.EditFileWindow',
            'RODAdmin.view.cms.files.FolderWindow', 'RODAdmin.view.common.AuditWindow',
            'RODAdmin.view.cms.files.filedetails.FileUsage'
    ],

    refs : [
            {
                ref : 'fdetailspanel',
                selector : 'cmsfiles panel#fdetailscontainer '
            }, {
                ref : 'fileusagepanel',
                selector : 'fileusage'
            }, {
                ref : 'filespecificpropertiespanel',
                selector : 'filespecificproperties'
            }, {
                ref : 'folderview',
                selector : "itemsview treepanel#folderview"
            }, {
                ref : 'itemsview',
                selector : 'itemsview'
            }, {
                ref : 'fileproperties',
                selector : 'fileproperties'
            }

    ],

    /**
	 * @method
	 */

    init : function(application) {
	    this.control({
	        "cmsfiles toolbar button#icon-view" : {
		        /**
				 * @listener cmsfiles-toolbar-button-icon-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.files.Files Files} toolbar
				 *           button#icon-view {@link #onIconViewClick}
				 */
		        click : this.onIconViewClick
	        },
	        "cmsfiles toolbar button#tree-view" : {
		        /**
				 * @listener cmsfiles-toolbar-button-tree-view-click
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.files.Files Files} toolbar
				 *           button#tree-view {@link #onTreeViewClick}
				 */
		        click : this.onTreeViewClick
	        },

	        "fileproperties toolbar#fileproptoolbar button#editfile" : {
		        /**
				 * @listener fileproperties-toolbar-fileproptoolbar-button-editfile
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.files.filedetails.FileProperties FileProperties}
				 *           toolbar#fileproptoolbar button#editfile
				 *           {@link #onfptoolbarEditClick}
				 */
		        click : this.onfptoolbarEditClick
	        },
	        "fileproperties toolbar#fileproptoolbar button#deletefile" : {
		        /**
				 * @listener fileproperties-toolbar-fileproptoolbar-button-deletefile
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.files.filedetails.FileProperties FileProperties}
				 *           toolbar#fileproptoolbar button#deletefile
				 *           {@link #onfptoolbarDeleteClick}
				 */
		        click : this.onfptoolbarDeleteClick
	        },
	        "fileproperties toolbar#fileproptoolbar button#getfileaudit" : {
		        /**
				 * @listener fileproperties-toolbar-fileproptoolbar-button-fileaudit
				 *           triggered-by:
				 *           {@link RODAdmin.view.cms.files.filedetails.FileProperties FileProperties}
				 *           toolbar#fileproptoolbar button#getfileaudit
				 *           {@link #onfptoolbarAuditClick}
				 */
		        click : this.onfptoolbarAuditClick
	        },

	    });
    	this.listen({
            controller: {
                '*': {
                    controllerCmsfilesInitView: this.initView
                }
            }
    	 });
    },
    /**
	 * @method
	 */
    initView : function() {
    	console.log('CMSFiles InitView');	
    },
    /**
	 * @method
	 */

    onfptoolbarEditClick : function(button, e, options) {
	    var fp = this.getFileproperties().data;

	    var win = Ext.create('RODAdmin.view.cms.files.EditFileWindow');
	    win.setTitle('Edit File "' + fp.data.name + '" (id: ' + fp.data.id + ')');
	    var wtree = win.down('treepanel');
	    win.show();
	    win.down('form').down('fieldset').query('displayfield')[0].setValue(fp.data.filename);
	    win.down('form').down('fieldset').query('displayfield')[1].setValue(fp.data.fileurl);
	    win.down('form').down('fieldset').query('textfield')[0].setValue(fp.data.alias);
	    win.down('form').down('fieldset').query('hiddenfield')[0].setValue(fp.data.id);
	    win.down('form').down('fieldset').query('hiddenfield')[1].setValue(fp.data.id);
    },
    /**
	 * @method
	 */
    onfptoolbarDeleteClick : function(button, e, options) {
	    console.log('editfile clicked');
    },
    /**
	 * @method
	 */
    onfptoolbarAuditClick : function(button, e, options) {
	    console.log('auditfile clicked');
	    var fp = this.getFileproperties().data;

	    var win = Ext.create('RODAdmin.view.common.AuditWindow');
	    win.setTitle('Audit data for "' + fp.data.filename + '" (id: ' + fp.data.id + ')');
	    win.show();
	    var auditgrid = win.down('grid[itemId=auditgrid]');
	    auditgrid.store.load();
	    console.log(auditgrid.store);
    },
    /**
	 * @method
	 */
    onIconViewClick : function(button, e, options) {
	    this.getItemsview().layout.setActiveItem('iconview');
	    var store = Ext.StoreManager.get('cms.files.File');
	    store.load();
    },
    /**
	 * @method
	 */
    onTreeViewClick : function(button, e, options) {
	    this.getItemsview().layout.setActiveItem('folderview');
    },

});