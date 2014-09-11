Ext.define('RODAdmin.model.studies.variable.response.Numeric', {
    extend: 'Ext.data.Model',
    fields: [
    {	name: 'type',
   		type : 'string'
	},
	{
	name: 'low',
	type: 'string'
	},
	{
	name: 'high',
	type: 'string'
	}
	]
});