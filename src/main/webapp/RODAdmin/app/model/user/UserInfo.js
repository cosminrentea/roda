/**
 * 
 */
Ext.define('RODAdmin.model.user.UserInfo', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
    	type : 'int'
	}, {
        name: 'username',
        type: 'string'
    }, {
		name : 'email',
		type : 'string'
		},	{
        name: 'enabled',
        type: 'boolean'
    }
    ],
hasMany: [
         {
         name: 'profile',
         model:'RODAdmin.model.user.UserProfile',
         associationKey: 'profile' 
         }
     ],

});



