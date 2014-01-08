/**
 * 
 */
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
		name : 'content',
		type : 'string'
	}, {
		name : 'itemtype',
		type : 'string'
		}
	],
	hasMany : [ {
		model : 'RODAdmin.model.cms.snippet.SnippetUsage',
		name : 'snippetusage',
		associationKey : 'snippetusage'
	}]
});
