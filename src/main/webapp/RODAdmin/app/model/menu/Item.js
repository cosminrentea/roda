Ext.define('RODAdmin.model.menu.Item', {
    extend: 'Ext.data.Model',

    uses: [
        'RODAdmin.model.menu.Root'
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
            name: 'className'
        },
        {
            name: 'id'
        },
        {
            name: 'parent_id'
        }
    ],

    belongsTo: {
        model: 'RODAdmin.model.menu.Root',
        foreignKey: 'parent_id'
    }
});