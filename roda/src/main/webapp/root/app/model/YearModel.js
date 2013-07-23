Ext.define('databrowser.model.YearModel', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'year',
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