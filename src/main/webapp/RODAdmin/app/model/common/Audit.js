/**
 * 
 */
Ext.define('RODAdmin.model.common.Audit', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'timestamp',
						useNull : true,
						type : 'date',
						dateFormat : 'Y-m-d H:i:s'
					}, {
						name : 'version',
						type : 'string'
					}, {
						name : 'username',
						type : 'string'
					}, {
						name : 'userid',
						type : 'integer'
					}, {
						name : 'modtype',
						type : 'string'
					}, {
						name : 'nrfields',
						type : 'integer'
					}],
			hasMany : [{
						model : 'RODAdmin.model.common.AuditField',
						name : 'auditfields',
						associationKey : 'auditfields'
					}]
		});
