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
    },{    
    	name: 'an',
    	type : 'int'
	}, {
        name: 'description',
        type: 'string'
    }, {
        name: 'geo_coverage',
        type: 'string'
    }, {
        name: 'geographicUnit',
        type: 'string'
    }, {
		name : 'unit_analysis',
		type : 'string'
	},	{
		name : 'univers',
		type : 'string'
	},{
		name : 'researchInstrument',
		type : 'string'
	},{
		name : 'seriesId',
		type : 'int'
	},{
		name : 'type',
		type : 'string'
	},{
		name : 'groupid',
		type : 'int'
	},{
		name : 'itemtype',
		type : 'string'
	}
    
    ]
});

