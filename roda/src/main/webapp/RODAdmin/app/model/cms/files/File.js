Ext.define('RODAdmin.model.cms.files.File', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'indice',
    	type : 'int'
	}, {
        name: 'name',
        type: 'string'
    }, {
        name: 'filetype',
        type: 'string'
    }, {
        name: 'filesize',
        type: 'float'
    }, {
        name: 'filepath',
        type: 'string'
    }, {
        name: 'alias',
        type: 'string'
    }, {
        name: 'fileurl',
        type: 'string'
    }, {
        name: 'directory',
        type: 'string'
    },
    {
        name: 'folderid',
        type: 'integer'
    }
    
    ]
});



