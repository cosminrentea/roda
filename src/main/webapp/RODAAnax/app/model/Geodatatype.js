
Ext.define('databrowser.model.YearsTreeModel', {
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

{name : 'id',
	type: 'string',
	convert: function (v, r) {
		if (r.get('year')) {
			return r.get('type') + '-' +r.get('year');
		} else {
			return r.get('type') + '-' +  r.get('indice');
		}
	}
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
			return databrowser.util.Globals['contextPath']+'/resources/root/img/series-member.png';
		} else if (r.get('type') == 'M') {
			return databrowser.util.Globals['contextPath']+'/resources/root/img/roda-m.png';			
		} else if (r.get('type') == 'St') {
			return databrowser.util.Globals['contextPath']+'/resources/root/img/study.png';
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