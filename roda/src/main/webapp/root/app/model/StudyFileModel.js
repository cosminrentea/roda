
Ext.define('databrowser.model.StudyFileModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'filename',
            type: 'string'
        },
        {
            name: 'filetype',
            type: 'string'
        },
        {
            name: 'fileurl',
            type: 'string'
        },
        {
            name: 'filedescription',
            type: 'string'
        }
    ]
});
