Ext.define('RODAdmin.model.studies.CBEditor.StudyQuestion', {
    extend: 'Ext.data.Model',
    fields: [
    {	name: 'id',
   		type : 'string'
	},
	{
	name: 'text',
	type: 'string'
	},
	{
	name: 'lang',
	type: 'string'
	},
	{
	name: 'respdomain',
	type: 'string'
	},
	{
	name: 'concept_id',
	type: 'integer'
	},
	{
	name: 'conceptname',
	type: 'string'
	}
	],
    hasMany: [
    	{
    		model: 'RODAdmin.model.studies.CBEditor.question.response.Category',
    		name: 'catresponses',
    		associationKey: 'catresponses'
   		}, 
    	{
    		model: 'RODAdmin.model.studies.CBEditor.question.response.Code',
    		name: 'coderesponses',
    		associationKey: 'coderesponses'
   		}, 
    	{
    		model: 'RODAdmin.model.studies.CBEditor.question.response.Numeric',
    		name: 'numericresponse',
    		associationKey: 'numericresponse'
   		},
    	{
    		model: 'RODAdmin.model.studies.CBEditor.question.Missing',
    		name: 'missing',
    		associationKey: 'missing'
   		}	
   		]
});