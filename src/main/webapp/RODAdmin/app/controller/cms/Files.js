Ext.define('RODAdmin.controller.cms.Files', {
	extend : 'Ext.app.Controller',

//	stores : ['cms.files.FileTree', 'cms.files.FileItem', 'cms.files.File',
//			'cms.files.FolderTree','common.Audit'],

	views : ['cms.files.Files', 'cms.files.Itemsview', 'cms.files.FileDetails',
			'cms.files.filedetails.FileProperties',
			'cms.files.filedetails.SpecificProperties',
			'cms.files.FileContextMenu', 'cms.files.FolderContextMenu',
			'cms.files.IconviewContextMenu', 'cms.files.FileWindow',
			'cms.files.EditFileWindow', 'cms.files.FolderWindow',
			'common.AuditWindow',
			'cms.files.filedetails.FileUsage'],

	refs : [{
				ref : 'fdetailspanel',
				selector : 'cmsfiles panel#fdetailscontainer '
			}, {
				ref : 'fileusagepanel',
				selector : 'fileusage'
			}, {
				ref : 'filespecificpropertiespanel',
				selector : 'filespecificproperties'
			}, {
				ref: 'folderview',
				selector : "itemsview treepanel#folderview"
			},{
				ref : 'itemsview',
				selector : 'itemsview'
			}, {
				ref : 'fileproperties',
				selector : 'fileproperties'
			}
			
			],

	init : function(application) {
		this.control({
					"cmsfiles toolbar button#icon-view" : {
						click : this.onIconViewClick
					},
					"cmsfiles toolbar button#tree-view" : {
						click : this.onTreeViewClick
					},

					"fileproperties toolbar#fileproptoolbar button#editfile" : {
						click: this.onfptoolbarEditClick
					}, 
					"fileproperties toolbar#fileproptoolbar button#deletefile" : {
						click: this.onfptoolbarDeleteClick
					}, 
					"fileproperties toolbar#fileproptoolbar button#getfileaudit" : {
						click: this.onfptoolbarAuditClick
					},

				});
	},




	
	onfptoolbarEditClick : function(button, e, options) {
		var fp = this.getFileproperties().data;

		var win = Ext.create('RODAdmin.view.cms.files.EditFileWindow');
		win.setTitle('Edit File "' + fp.data.name + '" (id: '	+ fp.data.id + ')');
		var wtree = win.down('treepanel');
		win.show();
		win.down('form').down('fieldset').query('displayfield')[0].setValue(fp.data.filename);
		win.down('form').down('fieldset').query('displayfield')[1].setValue(fp.data.fileurl);
		win.down('form').down('fieldset').query('textfield')[0].setValue(fp.data.alias);
		win.down('form').down('fieldset').query('hiddenfield')[0].setValue(fp.data.id);
		win.down('form').down('fieldset').query('hiddenfield')[1].setValue(fp.data.id);
	},

	onfptoolbarDeleteClick : function(button, e, options) {
		console.log('editfile clicked');	
	},
	
	onfptoolbarAuditClick : function(button, e, options) {
		console.log('auditfile clicked');	
		var fp = this.getFileproperties().data;	
		
		var win = Ext.create('RODAdmin.view.common.AuditWindow');
		win.setTitle('Audit data for "' + fp.data.filename + '" (id: '	+ fp.data.id + ')');
		win.show();		
		var auditgrid = win.down('grid[itemId=auditgrid]');
		auditgrid.store.load();
		console.log(auditgrid.store);
		},

	onIconViewClick : function(button, e, options) {
		this.getItemsview().layout.setActiveItem('iconview');
		var store = Ext.StoreManager.get('cms.files.File');
		store.load();
	},

	onTreeViewClick : function(button, e, options) {
		this.getItemsview().layout.setActiveItem('folderview');
	},
	

});