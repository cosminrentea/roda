Ext.define('databrowser.model.TopicTreeModel', {
	extend : 'Ext.data.Model',
	idProperty: 'indice',
	fields : [ {
		name : 'translation',
		type : 'string',
	}, {
		name : 'indice',
		type : 'integer'
	}, {
		name : 'children'
	}
	]
});
