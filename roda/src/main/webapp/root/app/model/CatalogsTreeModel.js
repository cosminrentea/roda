Ext.define('databrowser.model.CatalogsTreeModel', {
	extend : 'Ext.data.Model',

	fields : [ {
		name : 'name',
		type : 'string'
	}, {
		name : 'indice',
		type : 'integer'
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
				return 'img/series.png';
			} else if (r.get('type') == 'M') {
				return 'img/roda-m.png';
			} else if (r.get('type') == 'C') {
				return 'img/catalog.png';
			} else if (r.get('type') == 'St') {
				return 'img/study.png';
			} else if (r.get('type') == 'Sts') {
				return 'img/series-member.png';
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
