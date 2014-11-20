Ext
        .define(
                'databrowser.view.StudySeriesView',
                {
                    extend : 'Ext.tab.Panel',
                    // extend: 'Ext.grid.Panel',
                    alias : 'widget.studyseriesview',
                    autoRender : true,
                    // id: 'studyseriesview',
                    itemId : 'studyseriesview',
                    width : '100%',
                    header : true,
                    hideHeaders : false,
                    store : 'StudyStore',
                    loaddata : function(id) {

	                    // asta e o varianta idioata determinata oarecum de
						// incapacitatea autorilor extjs de a explica cum se fac
						// rahaturile simple
	                    this.setActiveTab(0);
	                    this.setLoading(translations.loading);
	                    var dtab = Ext.getCmp('ssdetails');
	                    var variablesgrid = Ext.getCmp('studyseriesvariables');
	                    var filestab = Ext.getCmp('studyseriesdocuments');
	                    // var seriestab = Ext.getCmp('studyseriesseries');
	                    var sStore = Ext.StoreManager.get(this.store);
	                    sStore.load({
	                        id : id, // set the id here
	                        scope : this,
	                        callback : function(records, operation, success) {
		                        this.setLoading(false);
		                        if (success) {
			                        var rec = sStore.first();

			                        var templatedata = records[0].data;
			                        if (rec.personsStore) {
				                        var personsdata = Ext.Array.pluck(rec.personsStore.data.items, 'data');
			                        }
			                        if (rec.orgsStore) {
				                        var orgsdata = Ext.Array.pluck(rec.orgsStore.data.items, 'data');
			                        }
			                        if (rec.keywordsStore) {
				                        var kwsdata = Ext.Array.pluck(rec.keywordsStore.data.items, 'data');
			                        }
			                        if (rec.topicsStore) {
				                        var topicsdata = Ext.Array.pluck(rec.topicsStore.data.items, 'data');
			                        }
			                        templatedata.orgs = orgsdata;
			                        templatedata.persons = personsdata;
			                        templatedata.keywords = kwsdata;
			                        templatedata.topics = topicsdata;
			                        dtab.update(templatedata);

			                        variablesgrid.getView().bindStore(rec.variablesStore);
			                        filestab.bindStore(rec.filesStore);
			                        this.setTitle(records[0].data.an + ' - ' + records[0].data.name);
			                        // aici trebuie sa incarcam storeul de serie
			                        console.log(records[0].data);
			                        console.log('name: ' + records[0].data.name);
			                        console.log('seriesId: ' + records[0].data.seriesId);
			                        var seriesStore = Ext.StoreManager.get('SeriesStore');
			                        seriesStore.load({
			                            id : records[0].data.seriesId,
			                            callback : function(records, operation, success) {
				                            var sdetails = Ext.getCmp('studyseriesdetails');
				                            var studyseriesstudies = Ext.getCmp('studyseriesstudies');
				                            var templatedata = records[0].data;
				                            if (rec.personsStore) {
					                            var personsdata = Ext.Array.pluck(rec.personsStore.data.items, 'data');
				                            }
				                            if (rec.orgsStore) {
					                            var orgsdata = Ext.Array.pluck(rec.orgsStore.data.items, 'data');
				                            }
				                            if (rec.keywordsStore) {
					                            var kwsdata = Ext.Array.pluck(rec.keywordsStore.data.items, 'data');
				                            }
				                            if (rec.topicsStore) {
					                            var topicsdata = Ext.Array.pluck(rec.topicsStore.data.items, 'data');
				                            }
				                            templatedata.orgs = orgsdata;
				                            templatedata.persons = personsdata;
				                            templatedata.keywords = kwsdata;
				                            templatedata.topics = topicsdata;
				                            sdetails.update(templatedata);
				                            studyseriesstudies.getView().bindStore(records[0].studiesStore);

			                            }
			                        })
		                        }
	                        }
	                    });
                    },
                    title : translations.seriesview,
                    items : [
                            {
                                autoScroll : true,
                                layout : 'fit',
                                title : translations.stdetails,
                                id : 'ssdetails',
                                tpl : new Ext.XTemplate(
                                                        '<div style="padding:10px;">',
                                                        '<H2>{name}</H2>',
                                                        '<H3>{author}</H3>',
                                                        '<p>{description}</p>',
                                                        '<table width="100%" border="0" cellspacing="2" class="sdetailstbl"',
                                                        '<tr><th>' + translations.stduniverse
                                                                + '</th><td> {universe}</td></tr>',
                                                        '<tr><th>' + translations.stdgeocover
                                                                + '</th><td> {geo_coverage}</td></tr>',
                                                        '<tr><th>' + translations.stdgeounit
                                                                + ':</th><td> {geo_unit}</td></tr>',
                                                        '<tr><th>' + translations.stdrestype
                                                                + ':</th><td> {research_instrument}</td></tr>',
                                                        '<tr><th>' + translations.stdunitanalysis
                                                                + ':</th><td> {unit_analysis}</td></tr>',
                                                        '<tr><th>' + translations.stdweighting
                                                                + ':</th><td> {weighting}</td></tr>',
                                                        '<tr><th>' + translations.topics + ':</th><td>',
                                                        '<tpl for="topics">',
                                                        '{translation}',
                                                        '</tpl>',
                                                        '</td></tr>',
                                                        '<tr><th>' + translations.keywords + ':</th><td>',
                                                        '<tpl for="keywords">',
                                                        '{name}, ',
                                                        '</tpl>',
                                                        '</td></tr>',
                                                        '</table>',
                                                        '</div>',
                                                        '<table width="100%" border="0" cellspacing="2" class="persorgcontainer">',
                                                        '<tr><td width="50%" valign="top"><div class="orgs"><table>',
                                                        '<tpl for="orgs">',
                                                        '<tr><td class="orgic"></td><td>{fname} {lname}</td></tr>',
                                                        '</tpl>', '</table></div></td>',
                                                        '<td width="50%" valign="top">',
                                                        '<div class="persons"><table>', '<tpl for="persons">',
                                                        '<tr><td class="personic"></td><td>{fname} {lname}</td></tr>',
                                                        '</tpl></table></div>', '</td>', '</tr>', '</table>'),
                            },
                            {
                                autoScroll : true,
                                layout : {
	                                type : 'border',
                                },
                                title : translations.stvariables,
                                itemId : 'svariables',
                                items : [
                                        {
                                            xtype : 'gridpanel',
                                            region : 'center',
                                            collapsible : false,
                                            split : false,
                                            // itemId: 'studyseriesvariables',
                                            id : 'studyseriesvariables',
                                            width : '100%',
                                            flex : 1,
                                            autoScroll : true,
                                            remoteSort : false,
                                            viewConfig : {
                                                plugins : {
                                                    ptype : 'gridviewdragdrop',
                                                    dragGroup : 'firstGridDDGroup',
                                                    dropGroup : 'secondGridDDGroup'
                                                },
                                                copy : true,
                                            },
                                            columns : [
                                                    {
                                                        xtype : 'gridcolumn',
                                                        dataIndex : 'name',
                                                        hideable : false,
                                                        text : translations.stvgname,
                                                        width : 100,
                                                        fixed : true,
                                                        renderer : function(value, metaData, record, rowIndex,
                                                                colIndex, store) {
	                                                        metaData.css = 'freqchart';
	                                                        return value;
                                                        }
                                                    }, {
                                                        xtype : 'gridcolumn',
                                                        dataIndex : 'label',
                                                        hideable : false,
                                                        text : translations.stvglabel,
                                                        flex : 1
                                                    },
                                            ]
                                        },
                                        {
                                            xtype : 'tabpanel',
                                            itemId : 'srvardet',
                                            region : 'east',
                                            collapsible : true,
                                            split : true,
                                            flex : 1,
                                            layout : 'fit',
                                            items : [
                                                    {
                                                        xtype : 'panel',
                                                        title : translations.stvariabledetails,
                                                        itemId : 'srvardetails',
                                                        autoScroll : true,
                                                        layout : {
                                                            type : 'vbox',
                                                            align : 'stretch'
                                                        },
                                                    // layout : 'fit',
                                                    // items : [
                                                    // {
                                                    // xtype : 'freqchart',
                                                    // itemId : 'ilfreqchart',
                                                    // }
                                                    // ]
                                                    },
                                                    {
                                                        xtype : 'panel',
                                                        autoScroll : true,
                                                        title : translations.stanalyze,
                                                        itemId : 'srvaranalyze',
                                                        items : [
                                                                {
                                                                    xtype : 'gridpanel',
                                                                    itemId : 'sranalysisvar',
                                                                    width : '100%',
                                                                    height : 100,
                                                                    store : 'SrMemoryVariableStore',
                                                                    flex : 1,
                                                                    viewConfig : {
	                                                                    plugins : {
	                                                                        ptype : 'gridviewdragdrop',
	                                                                        dropGroup : 'firstGridDDGroup'
	                                                                    }
                                                                    },
                                                                    columns : [
                                                                            {
                                                                                xtype : 'gridcolumn',
                                                                                dataIndex : 'name',
                                                                                hideable : false,
                                                                                text : translations.stanname,
                                                                                width : 100,
                                                                                fixed : true,
                                                                            },
                                                                            {
                                                                                xtype : 'gridcolumn',
                                                                                dataIndex : 'label',
                                                                                hideable : false,
                                                                                text : translations.stanlabel,
                                                                                flex : 1
                                                                            },
                                                                            {
                                                                                xtype : 'actioncolumn',
                                                                                width : 30,
                                                                                sortable : false,
                                                                                menuDisabled : true,
                                                                                itemId : 'actionpinvresp',
                                                                                items : [
	                                                                                {
	                                                                                    icon : '/roda/resources/root/img/delete.gif',
	                                                                                    tooltip : translations.standelvar,
	                                                                                    scope : this,
	                                                                                    handler : function(view,
	                                                                                            rowIndex, colIndex,
	                                                                                            item, e, record, row) {
		                                                                                    var mygrid = view
		                                                                                            .up('grid');
		                                                                                    mygrid
		                                                                                            .fireEvent(
		                                                                                                       'deleteRecord',
		                                                                                                       mygrid,
		                                                                                                       record,
		                                                                                                       rowIndex,
		                                                                                                       row);
	                                                                                    }
	                                                                                }
                                                                                ]
                                                                            }
                                                                    ]

                                                                }, {
                                                                    xtype : 'button',
                                                                    itemId : 'sendToAnalysis',
                                                                    text : translations.stansend,
                                                                    width : '100%'
                                                                }, {
                                                                    xtype : 'panel',
                                                                    itemId : 'analResults',
                                                                    bodyPadding : 5,
                                                                    autoScroll : true,
                                                                    layout : {
                                                                        type : 'vbox',
                                                                        align : 'center'
                                                                    },
                                                                }
                                                        ]
                                                    }
                                            ]

                                        }

                                ]
                            },
                            {
                                autoScroll : true,
                                layout : 'fit',
                                title : 'Documente',
                                id : 'ssfiles',
                                items : [
	                                {
	                                    id : 'studyseriesdocuments',
	                                    xtype : 'dataview',
	                                    tpl : new Ext.XTemplate('<div class="studyfiles">', '<tpl for=".">',
	                                                            '<div class="studyfiledl">',
	                                                            '<H2 class="file-{filetype}">{filename}</H2>',
	                                                            '<p>{filedescription}</p>', '<a href="{fileurl}">'
	                                                                    + translations.dwnlfile + '</a>', '</div>',
	                                                            '</tpl>', '</div>'),
	                                }
                                ]
                            },
                            {
                                autoScroll : true,
                                layout : 'accordion',
                                title : translations.serseries,
                                icon : 'img/series.png',
                                id : 'sseries',
                                items : [
                                        {
                                            title : translations.serdetails,
                                            id : 'studyseriesdetails',
                                            autoScroll : true,
                                            layout : 'fit',
                                            tpl : new Ext.XTemplate(
                                                                    '<div style="padding:10px;">',
                                                                    '<H2>{name}</H2>',
                                                                    '<H3>{author}</H3>',
                                                                    '<p>{description}</p>',
                                                                    '</div>',
                                                                    '<table width="100%" border="0" cellspacing="2" class="sdetailstbl"',
                                                                    '<tr><th>' + translations.stduniverse
                                                                            + '</th><td> {universe}</td></tr>',
                                                                    '<tr><th>' + translations.stdgeocover
                                                                            + '</th><td> {geo_coverage}</td></tr>',
                                                                    '<tr><th>' + translations.stdgeounit
                                                                            + ':</th><td> {geo_unit}</td></tr>',
                                                                    '<tr><th>' + translations.topics + ':</th><td>',
                                                                    '<tpl for="topics">',
                                                                    '{translation}',
                                                                    '</tpl>',
                                                                    '</td></tr>',
                                                                    '<tr><th>' + translations.keywords + ':</th><td>',
                                                                    '<tpl for="keywords">',
                                                                    '{name}, ',
                                                                    '</tpl>',
                                                                    '</td></tr>',
                                                                    '</table>',
                                                                    '</div>',
                                                                    '<table width="100%" border="0" cellspacing="2" class="persorgcontainer">',
                                                                    '<tr><td width="50%" valign="top"><div class="orgs"><table>',
                                                                    '<tpl for="orgs">',
                                                                    '<tr><td class="orgic"></td><td>{fname} {lname}</td></tr>',
                                                                    '</tpl>',
                                                                    '</table></div></td>',
                                                                    '<td width="50%" valign="top">',
                                                                    '<div class="persons"><table>',
                                                                    '<tpl for="persons">',
                                                                    '<tr><td class="personic"></td><td>{fname} {lname}</td></tr>',
                                                                    '</tpl></table></div>', '</td>', '</tr>',
                                                                    '</table>'),
                                        }, {
                                            title : translations.serotherst,
                                            xtype : 'seriesmembersview',
                                            id : 'studyseriesstudies',
                                        }
                                ]

                            }
                    ],
                    // store: 'CatalogDetailStore',
                    initComponent : function() {
	                    var me = this;
	                    me.callParent(arguments);
                    }

                });