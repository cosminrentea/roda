Ext.define('anax.model.GeoDatatype', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'id',
                 type: 'integer'
             },             
             {
                 name: 'startdate',
                 type: 'date',
				 dateFormat: 'Y-m-d'
              },             
             {
                 name: 'enddate',
                 type: 'date',
                 dateFormat: 'Y-m-d'
             },
             {
                 name: 'mapid',
                 type: 'integer'
             },
             {
                 name: 'name',
                 type: 'string'
             },
             {
                 name: 'description',
                 type: 'string'
             },
             {
                 name: 'code',
                 type: 'string'
             },
             ]
});