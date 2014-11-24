
Ext.define('databrowser.model.VariableModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'name',
            type: 'string'
        },
        {
            name: 'description',
            type: 'string'
        },        
        {
            name: 'label',
            type: 'string'
        },
        {
            name: 'indice',
            type: 'integer'
        }
    ],
    hasMany: [{
        model: 'databrowser.model.FrequencyModel',
        name: 'otherStatistics',
        associationKey: 'otherStatistics',
    }]
});