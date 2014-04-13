/**
 * 
 */
Ext.define('RODAdmin.model.audit.Revisions', {
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
						name : 'nrobjects',
						type : 'integer'
					}],
			hasMany : [{
						model : 'RODAdmin.model.audit.RevisionObject',
						name : 'mobj',
						associationKey : 'objects'
					}]
		});
