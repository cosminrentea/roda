/**
 * 
 */
Ext.define('RODAdmin.model.user.GroupInfo', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
    	type : 'int'
	}, {
        name: 'name',
        type: 'string'
    }, {
		name : 'description',
		type : 'string'
	},	{
    	name: 'nrusers',
    	type : 'int'
		}
    ]
});



