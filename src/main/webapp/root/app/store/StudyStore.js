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
                url: '../../j/studyinfo',  
                appendId: true,
//                url: 'data/study.json',
                reader: {
                    type: 'json',
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});