/**
 * 
 */
Ext.define('RODAdmin.model.cms.Collateral', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'type',
		type : 'string'
	}, {
		name : 'description',
		type : 'string'
	} ]
});
