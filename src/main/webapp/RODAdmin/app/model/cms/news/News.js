/**
 * 
 */
Ext.define('RODAdmin.model.cms.news.News', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
    	type : 'int'
	}, {
        name: 'title',
        type: 'string'
    }, {
        name: 'content',
        type: 'string'
    }, {
        name: 'added',
        type: 'string'
    }, {
		name : 'langId',
		type : 'int'
	}, {
		name : 'langCode',
		type : 'String'
	}
    ]
});



