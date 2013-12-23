/**
 * 
 */
Ext.define('RODAdmin.model.menu.Root', {
    extend: 'Ext.data.Model',

    uses: [
        'RODAdmin.model.menu.Item'
    ],

    idProperty: 'id',

    fields: [
        {
            name: 'text'
        },
        {
            name: 'iconCls'
        },
        {
            name: 'id'
        }
    ],

    hasMany: {
        model: 'RODAdmin.model.menu.Item',
        foreignKey: 'parent_id',
        name: 'items'
    }
});