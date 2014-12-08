Ext.define('anax.model.Years', {
    extend: 'Ext.data.Model',
//	idProperty: 'year',
	idProperty: 'id',

fields : [ {
	name : 'name',
	type : 'string'
}, {
	name : 'year',
	type : 'integer'
}, {
	name : 'indice',
	type : 'integer'
},
{
	name : 'children'
},
{
	name : 'an',
	type : 'int'
},{
	name : 'type',
	type : 'string'
}, {
	name: 'text',
	type: 'string'
}, {
	name : 'mapid',
	type : 'integer'
}
]

});