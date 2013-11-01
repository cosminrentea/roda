Ext.define('RODAdmin.model.cms.FileItem', {
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
		model : 'RODAdmin.model.cms.FileProperties',
		name : 'fileproperties',
		associationKey : 'fileproperties'
	}, {
		model : 'RODAdmin.model.cms.FileUsage',
		name : 'fileusage',
		associationKey : 'fileusage'
	} ]
});
