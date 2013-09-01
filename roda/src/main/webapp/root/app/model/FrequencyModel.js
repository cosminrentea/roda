Ext.define('databrowser.model.FrequencyModel', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'name',
                 type: 'string'
             },             
             {
                 name: 'count',
                 type: 'integer'
             },
             ],
});