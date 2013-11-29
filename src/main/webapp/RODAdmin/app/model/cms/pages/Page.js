Ext.define('RODAdmin.model.cms.pages.Page', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'indice',
    	type : 'int'
	}, {
        name: 'title',
        type: 'string'
    }, {
        name: 'lang',
        type: 'string'
    }, {
        name: 'menutitle',
        type: 'string'
    }, {
		name : 'url',
		type : 'string'
		},	{
        name: 'default',
        type: 'boolean'
    }, {
        name: 'externalredirect',
        type: 'string'
    }, {
        name: 'internalredirect',
        type: 'string'
    }, {
        name: 'layout',
        type: 'string'
    },  {
        name: 'cacheable',
        type: 'integer'
    }, {
        name: 'searchable',
        type: 'boolean'
    },{
        name: 'published',
        type: 'boolean'
    },{
        name: 'target',
        type: 'string'
    },{
        name: 'pagetype',
        type: 'string'
    },
    ]
});



