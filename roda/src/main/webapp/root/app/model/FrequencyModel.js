Ext.define('databrowser.model.FrequencyModel', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'name',
                 type: 'string'
             },             
             {
                 name: 'value',
                 type: 'integer'
             },
             {
                 name: 'id',
                 type: 'integer'
             },
             {
                 name: 'description',
                 type: 'string'
             },

             ],
});