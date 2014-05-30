/**
 * 
 */
Ext.define('RODAdmin.model.studies.Catalog', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'description',
		type : 'string'
	}, {
		name : 'itemtype',
		type : 'string'
	}]
});
