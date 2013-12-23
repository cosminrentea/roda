/**
 * 
 */
Ext.define('RODAdmin.model.common.AuditField', {
	extend : 'Ext.data.Model',
	fields : [ 
			{
        		name: 'auditfield', 
        		type: 'string'
    		},	{
				name : 'auditvalue',
				type : 'string'
			}]
});