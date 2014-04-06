/**
 * 
 */
Ext.define('RODAdmin.model.audit.RevisionsforObject', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'timestamp',
						useNull : true,
						type : 'date',
						dateFormat : 'd/m/Y H:i:s'
					}, {
						name : 'revision',
						type : 'integer'
					}, {
						name : 'username',
						type : 'string'
					}, {
						name : 'userid',
						type : 'integer'
					}, {
						name : 'nrobjects',
						type : 'integer'
					}],
			hasMany : [{
						model : 'RODAdmin.model.audit.RevisionRow',
						name : 'rows',
						associationKey : 'rows'
					}]
		});
