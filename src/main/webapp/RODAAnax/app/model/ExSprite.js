Ext.define('anax.model.ExSprite', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'name',
                 type: 'string'
             },             
             {
                 name: 'id',
                 type: 'integer'
             },
             {
            	 name: 'details',
            	 type: 'string'
             }
             ],
             hasMany: [{
                 model: 'anax.model.Sprite',
                 name: 'sprites',
                 associationKey: 'sprites'
             }]
});