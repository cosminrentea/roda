/**
 * 
 */
Ext.define('RODAdmin.model.audit.RevisionField', {
	extend : 'Ext.data.Model',
	fields : [ 
			{	
    			name: 'auditfield',
    			type: 'string'
    		},
    		{	
    			name: 'auditvalue',
    			type: 'string'
    		}
    		]
});