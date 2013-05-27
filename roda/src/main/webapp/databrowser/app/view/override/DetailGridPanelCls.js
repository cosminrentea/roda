Ext.define('databrowser.view.override.DetailGridPanelCls', {
    override: 'databrowser.view.DetailGridPanelCls',
    viewConfig: {
            id: 'DetailsGridView',
            trackOver: false,
            stripeRows: false,
            plugins: [{
                ptype: 'preview',
                bodyField: 'author',
                expanded: false,
                pluginId: 'preview'
            }]
        }
});