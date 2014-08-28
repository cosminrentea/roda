
Ext.define('databrowser.model.StudyFileModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'filename',
            type: 'string',
        },
        {
            name: 'filetype',
            type: 'string',
                convert : function(v, r) {
        				var str = r.get('contentType');
        				var tstr = str.trim();
        				var regex = /application\//;
        				return tstr.replace(regex, "");
        			}
        },
        {
            name: 'fileurl',
            type: 'string'
        },
        {
            name: 'filedescription',
            type: 'string'
        },
        {
        	name: 'contentType',
        	type: 'string',
        }
    ]
});
