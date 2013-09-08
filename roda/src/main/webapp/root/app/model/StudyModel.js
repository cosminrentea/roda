Ext.define('databrowser.model.StudyModel', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'name',
                 type: 'string'
             },    
             {
                 name: 'indice',
                 type: 'integer'
             },    
             {
                 name: 'author',
                 type: 'string'
             },
             {
                 name: 'an',
                 type: 'integer'
             },
             {
                 name: 'description',
                 type: 'string'
             },
             {
                 name: 'countries',
                 type: 'string'
             },
             {
                 name: 'geo_coverage',
                 type: 'string'
             },
             {
                 name: 'unit_analysis',
                 type: 'string'
             },
             {
                 name: 'universe',
                 type: 'string'
             },
             {
                 name: 'weighting',
                 type: 'string'
             },
             {
                 name: 'geo_unit',
                 type: 'string'
             },
             {
                 name: 'seriesId',
                 type: 'integer'
             }
             
             ],
		hasMany: [
		          {
				model: 'databrowser.model.VariableModel',
				name: 'variables',
				associationKey: 'variables'
		          },
		          {
				model: 'databrowser.model.StudyFileModel',
				name: 'files',
				associationKey: 'files'
		          }
		]
});