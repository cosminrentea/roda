/**
 * 
 */
Ext.define('RODAdmin.model.user.User', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
    	type : 'int'
	}, {
        name: 'username',
        type: 'string'
    }, {
        name: 'firstname',
        type: 'string'
    }, {
        name: 'lastname',
        type: 'string'
    }, {
		name : 'email',
		type : 'string'
		},	{
        name: 'enabled',
        type: 'boolean'
    }
    ]
});



