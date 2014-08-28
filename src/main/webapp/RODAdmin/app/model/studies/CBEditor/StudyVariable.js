Ext.define('RODAdmin.model.studies.CBEditor.StudyVariable', {
    extend: 'Ext.data.Model',
    fields: [
    {	name: 'id',
   		type : 'string'
	},
	{
	name: 'name',
	type: 'string'
	},
	{
	name: 'label',
	type: 'string'
	},
	{
	name: 'respdomain',
	type: 'string'
	},
    {	name: 'oqid',
   		type : 'string'
	},
	{
	name: 'oqtext',
	type: 'string'
	}]
});