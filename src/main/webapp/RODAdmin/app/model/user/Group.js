/**
 * 
 */
Ext.define('RODAdmin.model.user.Group', {
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
        name: 'nrusers',
        type: 'integer'
    }, {
        name: 'enabled',
        type: 'boolean'
    }
    ]
});



