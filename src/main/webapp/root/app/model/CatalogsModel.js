Ext.define('databrowser.model.CatalogsModel', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'name',
                 type: 'string'
             },             
             {
                 name: 'index',
                 type: 'integer'
             },
             ],
             hasMany: [{
                 model: 'databrowser.model.StudyModel',
                 name: 'studies',
                 associationKey: 'studies'
             }]
});