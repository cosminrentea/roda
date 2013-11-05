Ext.define('RODAdmin.model.cms.layout.LayoutItem', {
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
		model : 'RODAdmin.model.cms.layout.LayoutUsage',
		name : 'layoutusage',
		associationKey : 'layoutusage'
	}]
});
