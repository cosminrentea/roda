Ext.define('RODAdmin.store.studies.CBEditor.CollectionMode', {
    extend: 'Ext.data.Store',
 	fields: ['id', 'name'],
	 data: [{
     	   "id": 1,
            "name": "Face to face interview"
    }, {
        "id": 2,
        "name": "Telephone Interview"
    }, {
        "id": 3,
        "name": "Email interview"
    } ]
});
