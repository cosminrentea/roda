Ext.define('RODAdmin.model.studies.CBEditor.File', {
    extend: 'Ext.data.Model',
    fields: [
    {	name: 'id',
   		type : 'string'
	},
	{
	name: 'fname',
	type: 'string'
	},
	{
	name: 'uri',
	type: 'string'
	},
	{
	name: 'uptype',
	type: 'string'
	},
	{
	name: 'uploadid',
	type: 'string'
	},
	{
	name: 'ftype',
	type: 'string'
	},
	{
	name: 'ftypeid',
	type: 'string'
	},
    {	name: 'cases',
   		type : 'integer'
	},
	{
	name: 'reccount',
	type: 'integer'
	}]
});