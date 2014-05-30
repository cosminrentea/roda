/**
 * 
 */
Ext.define('RODAdmin.model.studies.StudyItem', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'groupid',
		type : 'integer'
	}, {
		name : 'description',
		type : 'string'
	}, {
		name : 'itemtype',
		type : 'string'
	}],
	hasMany : [ {
		model : 'RODAdmin.model.studies.StudyVariable',
		name : 'variables',
		associationKey : 'variables'
	}]
});
