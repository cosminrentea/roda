/**
 * 
 */
Ext.define('RODAdmin.model.studies.StudyItem', {
    extend : 'Ext.data.Model',
    fields : [
            {
                name : 'indice',
                type : 'int'
            }, {
                name : 'seriesId',
                type : 'int'
            }, {
                name : 'an',
                type : 'int'
            }, {
                name : 'name',
                type : 'string'
            }, {
                name : 'description',
                type : 'string'
            }, {
                name : 'type',
                type : 'string'
            }, {
                name : 'geo_coverage',
                type : 'string'
            }, {
                name : 'geo_unit',
                type : 'string'
            }, {
                name : 'research_instrument',
                type : 'string'
            }, {
                name : 'unit_analysis',
                type : 'string'
            }, {
                name : 'universe',
                type : 'string'
            }
    ],
    hasMany : [
            {
                model : 'RODAdmin.model.studies.StudyVariable',
                name : 'variables',
                associationKey : 'variables'
            }, {
                model : 'RODAdmin.model.studies.StudyKeywords',
                name : 'keywords',
                associationKey : 'keywords'
            }
    ]
});
