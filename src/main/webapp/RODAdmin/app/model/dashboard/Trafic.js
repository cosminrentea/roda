Ext.define('RODAdmin.model.dashboard.Trafic', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'day', type: 'string'},
        {name: 'visits',     type: 'integer'},
        {name: 'visitors',  type: 'integer'},
        {name: 'views',  type: 'integer'},
    ]
});
