/**
 * 
 */
Ext.define('RODAdmin.model.audit.RevisionObject', {
	extend : 'Ext.data.Model',
	fields : [ 
			{
        		name: 'objname', 
        		type: 'string'
    		},
    		{	
    			name: 'nrrows',
    			type: 'integer'
    		}],
   	hasMany : [{
				model : 'RODAdmin.model.audit.RevisionRow',
				name : 'rows',
				associationKey : 'rows'
			}]
});