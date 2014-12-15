
Ext.define('databrowser.model.StudyOrgModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'id',
            type: 'integer',
        },
        {
            name: 'fullName',
            type: 'string'
        },
        {
        	name: 'fname',
        	type: 'string',
        	convert: function (v, r) {
    			return r.get('fullName');
    		}
        },
        {
            name: 'lname',
            type: 'string'
        },
        {
            name: 'mname',
            type: 'string'
        },
        ]
});
