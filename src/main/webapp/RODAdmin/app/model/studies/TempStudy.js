/**
 * 
 */
Ext.define('RODAdmin.model.studies.TempStudy', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'alias',
    	type : 'string'
	}, {
        name: 'directory',
        type: 'string'
    }, {
        name: 'filesize',
        type: 'integer'
    }, {
		name : 'filetype',
		type : 'string'
	},{
		name : 'indice',
		type : 'integer'
	},{
		name : 'name',
		type : 'string'
	}],
	  hasMany : [
	               {
	                   model :'RODAdmin.model.cms.files.FileProperties',
	                   name : 'filepropertiesset',
	                   associationKey : 'filepropertiesset'
	               }
	            ]
});

