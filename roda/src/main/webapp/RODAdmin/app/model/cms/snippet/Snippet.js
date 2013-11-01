Ext.define('RODAdmin.model.cms.snippet.Snippet', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'indice',
    	type : 'int'
	}, {
        name: 'name',
        type: 'string'
    }, {
        name: 'description',
        type: 'string'
    }, {
        name: 'directory',
        type: 'string'
    }, {
		name : 'itemtype',
		type : 'string'
		},	{
        name: 'groupid',
        type: 'integer'
    }, {
        name: 'pagesnumber',
        type: 'integer'
    }
    
    ]
});



