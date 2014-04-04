/**
 * 
 */
Ext.define('RODAdmin.model.cms.files.FileItem', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'filename',
		type : 'string'
	}, {
		name : 'filetype',
		type : 'string'
	}, {
		name : 'filesize',
		type : 'float'
	}, {
		name : 'filepath',
		type : 'string'
	}, {
		name : 'alias',
		type : 'string'
	}, {
		name : 'fileurl',
		type : 'string'
	} ],
	hasMany : [ {
		model : 'RODAdmin.model.cms.files.FileProperties',
		name : 'fileproperties',
		associationKey : 'filepropertiesset'
	}, {
		model : 'RODAdmin.model.cms.files.FileUsage',
		name : 'fileusage',
		associationKey : 'fileusage'
	} ]
});
