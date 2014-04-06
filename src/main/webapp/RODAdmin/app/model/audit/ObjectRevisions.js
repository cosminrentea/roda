/**
 * 
 */
Ext.define('RODAdmin.model.audit.ObjectRevisions', {
	extend : 'Ext.data.Model',
	fields : [ 
    		{	
    			name: 'nrrows',
    			type: 'integer'
    		},
    		{	
    			name: 'object',
    			type: 'string'
    		},
    		{	
    			name: 'lastrevision',
    			type : 'date',
				dateFormat : 'd/m/Y H:i:s'
    		},
    		],
   	hasMany : [{
				model : 'RODAdmin.model.audit.RevisionsforObject',
				name : 'revisions',
				associationKey : 'revisions'
			}]
});