
Ext.define('databrowser.model.YearsTreeModel', {
    extend: 'Ext.data.Model',


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
	name : 'text',
	type : 'string',
	convert : function(v, r) {
		if (r.get('an')) {
			return r.get('an') + ' - ' + r.get('name');
		} else {
			return r.get('name');
		}
	}
},

{
	name : 'icon',
	type : 'string',
	convert : function(v, r) {
		if (r.get('type') == 'Sts') {
			return '/roda/resources/root/img/series-member.png';
		} else if (r.get('type') == 'M') {
			return '/roda/resources/root/img/roda-m.png';			
		} else if (r.get('type') == 'St') {
			return '/roda/resources/root/img/study.png';
		}
	}
},{
	name : 'an',
	type : 'int'
},{
	name : 'type',
	type : 'string'
} ]
});