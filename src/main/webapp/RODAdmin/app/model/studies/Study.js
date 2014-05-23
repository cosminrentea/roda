/**
 * 
 */
Ext.define('RODAdmin.model.studies.Study', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
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
        name: 'geographicCoverage',
        type: 'string'
    }, {
		name : 'unitAnalysis',
		type : 'string'
	},	{
		name : 'universe',
		type : 'string'
	},{
		name : 'researchInstrument',
		type : 'string'
	}
    
    ]
});

