/**
 * 
 */
Ext.define('RODAdmin.model.studies.Catalog', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'indice',
		type : 'int'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'nrStudies',
		type : 'int'
	}, {
		name : 'type',
		type : 'string'
	}
	],
    hasMany : [
               {
                   model :'RODAdmin.model.studies.Study',
                   name : 'studies',
                   associationKey : 'studies'
               }
               ]
});
