Ext.define('databrowser.model.TopicTreeModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'name',
		type : 'string'
	}, {
		name : 'indice',
		type : 'integer'
	}, {
		name : 'children'
	}
	]
});
