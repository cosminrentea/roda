Ext.define('anax.model.ItemsModel', {
	extend : 'Ext.data.Model',
//	idProperty: 'id',

fields : [ 
	{
		name : 'gid',
		type : 'integer'
	},
	{
		name : 'geodatatype',
		type : 'integer'
	},
	{
		name : 'geographyid',
		type : 'integer'
	},
	{
		name : 'text',
		type : 'string'
	},{
		name: "type",
		type: "string"
	},{
        name: 'startdate',
        type: 'date',
		 dateFormat: 'Y-m-d'
    },{
        name: 'enddate',
        type: 'date',
        dateFormat: 'Y-m-d'
    }

		
	]
});
