/**
 * 
 */
Ext.define('RODAdmin.model.cms.news.NewsItem', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'title',
		type : 'string'
	}, {
		name : 'content',
		type : 'string'
	}, {
		name : 'added',
		type : 'string'
	}, {
		name : 'langId',
		type : 'int'
	}, {
		name : 'langCode',
		type : 'String'
	}
		
	]
});
