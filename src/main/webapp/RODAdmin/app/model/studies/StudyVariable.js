/**
 * 
 */
Ext.define('RODAdmin.model.studies.StudyVariable', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'name',
        type: 'string'
    },{
    	name: 'label',
    	type: 'string'
    },{
    	name: 'indice',
    	type: 'integer'
    }]
});



