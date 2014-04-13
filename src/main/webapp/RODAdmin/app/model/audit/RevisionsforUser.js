/**
 * 
 */
Ext.define('RODAdmin.model.audit.RevisionsforUser', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'timestamp',
//						useNull : true,
						type : 'string',
//						dateFormat : 'd/m/Y H:i:s'
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
						name : 'nrrows',
						type : 'integer'
					}],
			hasMany : [{
						model : 'RODAdmin.model.audit.RevisionObject',
						name : 'objects',
						associationKey : 'objects'
					}]
		});
