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
        'Ext.ModelManager'
    ],
    models: [
        'CatalogsTreeModel',
        'CatalogsModel',
        'YearsTreeModel',
        'StudyModel',
        'SeriesModel',
        'VariableModel',
       'StudyFileModel',
       'YearModel'
     ],
    stores: [
        'StudyStore',
        'SeriesStore',
        'CatalogStore',
        'CatalogTreeStore',
        'YearTreeStore',
        'YearStore'
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
        'SeriesMembersView'
    ],
    autoCreateViewport: true,
    name: 'databrowser',
});
