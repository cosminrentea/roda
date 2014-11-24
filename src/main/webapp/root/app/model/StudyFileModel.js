
Ext.define('databrowser.model.StudyFileModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'filename',
            type: 'string',
        },
        {
            name: 'url',
            type: 'string'
        },
        {
            name: 'description',
            type: 'string'
        },
        {
        	name: 'contentType',
        	type: 'string',
        }
    ]
});
