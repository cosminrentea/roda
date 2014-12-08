Ext.define('anax.model.Sprite', {
    extend: 'Ext.data.Model',
        
    fields: [
             {
                 name: 'type',
                 type: 'string'
             },             
             {
                 name: 'id',
                 type: 'integer'
             },
             {
                 name: 'path',
                 type: 'string'
             },
             {
                 name: 'stroke-width',
                 type: 'numeric'
             },
             {
                 name: 'fill',
                 type: 'string'
             },
             ],

});