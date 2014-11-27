Ext.define('databrowser.model.TopicsModel', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'name',
                 type: 'string'
             },             
             {
                 name: 'indice',
                 type: 'integer'
             },             
             {
                 name: 'nrstudies',
                 type: 'integer'
             },
             ],
             hasMany: [{
                 model: 'databrowser.model.StudyModel',
                 name: 'studies',
                 associationKey: 'studies'
             }]
});