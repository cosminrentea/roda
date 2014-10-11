Ext.define('databrowser.store.StudyStore', {
    extend: 'Ext.data.JsonStore',

    requires: [
        'databrowser.model.StudyModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'databrowser.model.StudyModel',
            storeId: 'StudyStore',
            proxy: {
                type: 'rest',
                extraParams : {
                	lang : translations.language
                },
                url: '../studyinfo',  
                appendId: true,
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});