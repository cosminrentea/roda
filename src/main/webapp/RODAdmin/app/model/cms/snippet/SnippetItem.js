Ext.define('RODAdmin.model.cms.snippet.SnippetItem', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'groupId',
		type : 'integer'
	}, {
		name : 'directory',
		type : 'string'
	}, {
		name : 'description',
		type : 'string'
	}, {
		name : 'itemtype',
		type : 'string'
		},
	{
		name : 'pagesnumber',
		type : 'integer'
	}],
	hasMany : [ {
		model : 'RODAdmin.model.cms.snippet.SnippetUsage',
		name : 'snippetusage',
		associationKey : 'snippetusage'
	}]
});
