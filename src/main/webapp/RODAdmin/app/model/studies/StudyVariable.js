/**
 * 
 */
Ext.define('RODAdmin.model.studies.StudyVariable', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'name',
        type: 'string'
    },{
    	name: 'label',
    	type: 'string'
    },{
    	name: 'indice',
    	type: 'integer'
    },{
    	name: 'respdomain',
    	type: 'string'
    }
    ],
    hasMany: [
          	{
          		model: 'RODAdmin.model.studies.variable.response.Category',
          		name: 'catresponses',
          		associationKey: 'catresponses'
         		}, 
          	{
          		model: 'RODAdmin.model.studies.variable.response.Code',
          		name: 'coderesponses',
          		associationKey: 'coderesponses'
         		}, 
          	{
          		model: 'RODAdmin.model.studies.variable.response.Numeric',
          		name: 'numericresponse',
          		associationKey: 'numericresponse'
         		},
          	{
          		model: 'RODAdmin.model.studies.variable.Missing',
          		name: 'missing',
          		associationKey: 'missing'
         		}	
         		]
});



