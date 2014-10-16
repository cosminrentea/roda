Ext.define('databrowser.model.CatalogsTreeModel', {
	extend : 'Ext.data.Model',
	idProperty: 'id',
	fields : [ {
		name : 'name',
		type : 'string'
	}, {
		name : 'indice',
		type : 'integer'
	},
	{name : 'id',
		type: 'string',
		convert: function (v, r) {
			return r.get('type') + '-' + r.get('indice');
		}
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
	}, {
		name : 'children'
	}, {
		name : 'icon',
		type : 'string',
		convert : function(v, r) {
			if (r.get('type') == 'S') {
				return '/roda/resources/root/img/series.png';
			} else if (r.get('type') == 'M') {
				return '/roda/resources/root/img/roda-m.png';
			} else if (r.get('type') == 'C') {
				return '/roda/resources/root/img/catalog.png';
			} else if (r.get('type') == 'St') {
				return '/roda/resources/root/img/study.png';
			} else if (r.get('type') == 'Sts') {
				return '/roda/resources/root/img/series-member.png';
			}
		}
	}, {
		name : 'an',
		type : 'int'
	}, {
		name : 'type',
		type : 'string'
	}, {
		name : 'author',
		type : 'string'
	} ]
});
