/**
 * 
 */
Ext.define('RODAdmin.model.studies.Study', {
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
		name : 'type',
		type : 'string'
	},{
		name : 'itemtype',
		type : 'string'
	}
    
    ]
});

