Ext.define('databrowser.model.TopicTreeModel', {
	extend : 'Ext.data.Model',
	idProperty: 'indice',
	fields : [ {
		name : 'translation',
		type : 'string',
	}, 
	{
		name : 'name',
		type : 'string',
	}, 
	{
		name : 'indice',
		type : 'integer'
	}, {
		name : 'children'
	}, {
		name : 'icon',
		type : 'string',
		convert : function(v, r) {
			if (r.get('type') == 'S') {
				return databrowser.util.Globals['contextPath']+'/resources/root/img/series.png';
			} else if (r.get('type') == 'M') {
				return databrowser.util.Globals['contextPath']+'/resources/root/img/roda-m.png';
			} else if (r.get('type') == 'C') {
				return databrowser.util.Globals['contextPath']+'/resources/root/img/catalog.png';
			} else if (r.get('type') == 'St') {
				return databrowser.util.Globals['contextPath']+'/resources/root/img/study.png';
			} else if (r.get('type') == 'Sts') {
				return databrowser.util.Globals['contextPath']+'/resources/root/img/series-member.png';
			}
		}
	}
	]
});
