/**
 * 
 */
Ext.define('RODAdmin.model.audit.UserRevisions', {
	extend : 'Ext.data.Model',
	fields : [ 
    		{	
    			name: 'nrrev',
    			type: 'integer'
    		},
    		{	
    			name: 'username',
    			type: 'string'
    		},
    		{	
    			name: 'lastrevision',
    			type : 'date',
				dateFormat : 'd/m/Y H:i:s'
    		},
    		],
   	hasMany : [{
				model : 'RODAdmin.model.audit.RevisionsforUser',
				name : 'revisions',
				associationKey : 'revisions'
			}]
});