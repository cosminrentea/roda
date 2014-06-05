
Ext.define('databrowser.store.YearTreeStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'databrowser.model.YearsTreeModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.YearsTreeModel',
            storeId: 'YearTreeStore',
            root: {
                name: 'Root',
                expandable: true,
                children: {
                    
                }
            },
            proxy: {
                type: 'ajax',
                timeout:200000,
                url: '../../j/yearstree',  
//                url: 'data/years-tree.json',
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});