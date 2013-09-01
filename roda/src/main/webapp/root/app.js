//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true,
    paths: {
        'Ext.ux': 'ux/'
    }
});

Ext.application({

    requires: [
        'Ext.grid.*',
        'Ext.data.*',
        'Ext.util.*',
        'Ext.toolbar.Paging',
        'Ext.ModelManager',
        'databrowser.util.ChartTitleMixin'
    ],
    models: [
        'CatalogsTreeModel',
        'CatalogsModel',
        'YearsTreeModel',
        'StudyModel',
        'SeriesModel',
        'VariableModel',
       'StudyFileModel',
       'YearModel',
       'FrequencyModel'
     ],
    stores: [
        'StudyStore',
        'SeriesStore',
        'CatalogStore',
        'CatalogTreeStore',
        'YearTreeStore',
        'YearStore',
        'VariableStore'
    ],
    views: [
        'DataBrowserPanel',
        'CatalogView',
        'DBCard',
        'DetailsPanel',
        'StudyView',
        'SeriesView',
        'YearView',
        'StudySeriesView',
        'SeriesMembersView',
        'VariableView',
        'FrequencyChart',
        'VariableDetails'
    ],
    autoCreateViewport: true,
    name: 'databrowser',
});
