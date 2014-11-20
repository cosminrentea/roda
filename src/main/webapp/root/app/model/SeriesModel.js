Ext.define('databrowser.model.SeriesModel', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'name',
                 type: 'string'
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
             ],
             hasMany: [{
                 model: 'databrowser.model.StudyModel',
                 name: 'studies',
                 associationKey: 'studies'
             },
	          {
    				model: 'databrowser.model.StudyPersonModel',
    				name: 'persons',
    				associationKey: 'persons'
	          },
	          {
    				model: 'databrowser.model.StudyOrgModel',
    				name: 'orgs',
    				associationKey: 'orgs'
	          },
	          {
    				model: 'databrowser.model.StudyKeywordModel',
    				name: 'keywords',
    				associationKey: 'keywords'
	          },
	          {
    				model: 'databrowser.model.StudyTopicModel',
    				name: 'topics',
    				associationKey: 'translatedTopics'
	          }
             ]
});