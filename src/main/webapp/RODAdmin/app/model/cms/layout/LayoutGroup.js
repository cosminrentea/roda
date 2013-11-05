Ext.define('RODAdmin.model.cms.layout.LayoutGroup', {
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
	},	{
		name : 'layoutsnumber',
		type : 'integer'
	}]
});
