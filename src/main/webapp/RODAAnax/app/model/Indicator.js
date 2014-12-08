Ext.define('anax.model.Indicator', {
    extend: 'Ext.data.Model',
    fields: [
             {
                 name: 'geocode',
                 type: 'integer'
             },             
             {
                 name: 'name',
                 type: 'string'
              },             
             {
                 name: 'value',
                 type: 'numeric'
             }
             ]
});