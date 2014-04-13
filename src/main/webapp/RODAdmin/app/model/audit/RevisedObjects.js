/**
 * 
 */
Ext.define('RODAdmin.model.audit.RevisedObjects', {
			extend : 'Ext.data.Model',
			fields : [ {
						name : 'object',
						type : 'string'
					},
					 {
						name : 'lastrevision',
						type : 'string'
					},
					 {
						name : 'nrrev',
						type : 'integer'
					},
			
			]
		});
