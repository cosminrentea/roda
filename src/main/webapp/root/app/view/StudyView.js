/*
 * File: app/view/DetailGridPanelCls.js
 *
 * This file was generated by Sencha Architect version 2.2.1.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('databrowser.view.StudyView', {
    extend : 'Ext.tab.Panel',
    alias : 'widget.studyview',
    autoRender : true,
    id : 'studyview',
    itemId : 'studyview',
    width : '100%',
    header : true,
    hideHeaders : false,
    store : 'StudyStore',
    loaddata : function(id) {

	    // asta e o varianta idioata determinata oarecum de incapacitatea
		// autorilor extjs de a explica cum se fac rahaturile simple
	    this.setActiveTab(0);
	    var dtab = Ext.getCmp('sdetails');
	    var variablesgrid = Ext.getCmp('studyvariables');
	    var filestab = Ext.getCmp('studydocuments');
	    var sStore = Ext.StoreManager.get(this.store);
	    sStore.load({
	        id : id, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
		        if (success) {
			        var rec = sStore.first();
			        console.log(rec.filesStore);
			        console.log('study store loaded');
			        dtab.update(records[0].data);
			        variablesgrid.getView().bindStore(rec.variablesStore);
			        console.log(variablesgrid.getStore());
			        filestab.bindStore(rec.filesStore);
		        }
	        }
	    });
    },
    title : 'Study View',
    items : [
            {
                autoScroll : true,
                layout : 'fit',
                title : 'Detalii',
                id : 'sdetails',
                tpl : new Ext.XTemplate('<div style="padding:10px;">', '<H2>{name}</H2>', '<H3>{author}</H3>',
                                        '<p>{description}</p>',
                                        '<table width="100%" border="0" cellspacing="2" class="sdetailstbl"',
                                        '<tr><th>Univers:</th><td> {universe}</td></tr>',
                                        '<tr><th>Acoperire geografica:</th><td> {geo_coverage}</td></tr>',
                                        '<tr><th>Unitate geografica:</th><td> {geo_unit}</td></tr>',
                                        '<tr><th>Tipul cercetarii:</th><td> {research_instrument}</td></tr>',
                                        '<tr><th>Unitate de analiza:</th><td> {unit_analysis}</td></tr>',
                                        '<tr><th>Ponderare:</th><td> {weighting}</td></tr>', '</table>', '</div>'),
            },
            {
                autoScroll : true,
                layout : {
	                type : 'border',
                },
                title : 'Variabiles',
                itemId : 'svariables',
                items : [
                        {
                            xtype : 'grid',
                            region : 'center',
                            collapsible : true,
                            split : false,
                            id : 'studyvariables',
                            width : '100%',
                            flex : 1,
                            autoScroll : true,
                            remoteSort : false,
                            columns : [
                                    {
                                        xtype : 'gridcolumn',
                                        dataIndex : 'name',
                                        hideable : false,
                                        text : 'name',
                                        width : 100,
                                        fixed : true,
                                        renderer : function(value, metaData, record, rowIndex, colIndex, store) {
	                                        metaData.css = 'freqchart';
	                                        return value;
                                        }
                                    }, {
                                        xtype : 'gridcolumn',
                                        dataIndex : 'label',
                                        hideable : false,
                                        text : 'label',
                                        flex : 1
                                    },

                            ]
                        },
                        {
                            xtype : 'tabpanel',
                            region : 'south',
                            collapsible : true,
                            split : true,
                            flex : 1,
                            layout : 'fit',
                            items : [
                                    {
                                        xtype : 'panel',
                                        title : 'variable details',
                                        itemId: 'vardetails',
                                        autoScroll : true,
                                        //layout : 'fit',
                                        layout : {
                                            type : 'vbox',
                                            align : 'center'
                                        },
//                                        items : [
//	                                        {
//	                                            xtype : 'freqchart',
//	                                            id : 'ilfreqchart',
//	                                        }
//                                        ]
                                    },
                                    {
                                        xtype : 'panel',
                                        autoScroll : true,
                                        title : 'Analyse',
                                        items : [
                                                {
                                                    xtype : 'gridpanel',
                                                    id : 'analysisvar',
                                                    width : '100%',
                                                    store : 'MemoryVariableStore',
                                                    flex : 1,
                                                    columns : [
                                                            {
                                                                xtype : 'gridcolumn',
                                                                dataIndex : 'name',
                                                                hideable : false,
                                                                text : 'name',
                                                                width : 100,
                                                                fixed : true,
                                                            },
                                                            {
                                                                xtype : 'gridcolumn',
                                                                dataIndex : 'label',
                                                                hideable : false,
                                                                text : 'label',
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
	                                                                    tooltip : 'Delete variable',
	                                                                    scope : this,
	                                                                    handler : function(view, rowIndex, colIndex,
	                                                                            item, e, record, row) {
		                                                                    var mygrid = view.up('grid');
		                                                                    mygrid.fireEvent('deleteRecord', mygrid,
		                                                                                     record, rowIndex, row);
	                                                                    }
	                                                                }
                                                                ]
                                                            }
                                                    ]

                                                }, {
                                                    xtype : 'button',
                                                    itemId : 'sendToAnalysis',
                                                    text : 'Analyze',
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
            // end gridpanel

            },
            {
                autoScroll : true,
                layout : 'fit',
                title : 'Date',
                id : 'sdata'
            },
            {
                autoScroll : true,
                layout : 'fit',
                title : 'Documente',
                id : 'sfiles',
                items : [
	                {
	                    id : 'studydocuments',
	                    xtype : 'dataview',
	                    tpl : new Ext.XTemplate('<div class="studyfiles">', '<tpl for=".">',
	                                            '<div class="studyfiledl">', '<H2>{filename}</H2>',
	                                            '<p>{filedescription}</p>',
	                                            '<a  class="file-{filetype}" href="{fileurl}">Descarca fisierul</a>',
	                                            '</div>', '</tpl>', '</div>'),
	                }
                ]
            }
    ],
    initComponent : function() {
	    var me = this;
	    me.callParent(arguments);
    }

});