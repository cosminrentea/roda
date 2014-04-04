/**
 * 
 */
Ext.define('RODAdmin.model.audit.RevisionRow', {
	extend : 'Ext.data.Model',
	fields : [ 
			{
        		name: 'id', 
        		type: 'integer'
    		},
    		{	
    			name: 'modtype',
    			type: 'string'
    		},
    		{	
    			name: 'nrfields',
    			type: 'integer'
    		}
    		],
  	hasMany : [{
		model : 'RODAdmin.model.audit.RevisionField',
		name : 'auditfields',
		associationKey : 'auditfields'
	}]
});