Ext.define('RODAdmin.model.cms.files.Folder', {
    extend: 'Ext.data.Model',
    fields: [
    {    
    	name: 'id',
    	type : 'int'
	}, {
        name: 'name',
        type: 'string'
    }, {
        name: 'filetype',
        type: 'string'
    }
    ]
});



