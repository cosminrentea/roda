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
    	this.setLoading('Loading....');	    
    	this.setActiveTab(0);
	    var dtab = Ext.getCmp('sdetails');
	    var variablesgrid = Ext.getCmp('studyvariables');
	    var filestab = Ext.getCmp('studydocuments');
	    var sStore = Ext.StoreManager.get(this.store);
	    sStore.load({
	        id : id, // set the id here
	        scope : this,
	        callback : function(records, operation, success) {
	        	this.setLoading(false);
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
    title : translations.studyview,
    items : [
            {
                autoScroll : true,
                layout : 'fit',
                title : translations.stdetails,
                id : 'sdetails',
                tpl : new Ext.XTemplate('<div style="padding:10px;">', '<H2>{name}</H2>', '<H3>{author}</H3>',
                                        '<p>{description}</p>',
                                        '<table width="100%" border="0" cellspacing="2" class="sdetailstbl"',
                                        '<tr><th>'+ translations.stduniverse +'</th><td> {universe}</td></tr>',
                                        '<tr><th>'+ translations.stdgeocover +'</th><td> {geo_coverage}</td></tr>',
                                        '<tr><th>'+ translations.stdgeounit +':</th><td> {geo_unit}</td></tr>',
                                        '<tr><th>'+ translations.stdrestype +':</th><td> {research_instrument}</td></tr>',
                                        '<tr><th>'+ translations.stdunitanalysis +':</th><td> {unit_analysis}</td></tr>',
                                        '<tr><th>'+ translations.stdweighting +':</th><td> {weighting}</td></tr>', '</table>', '</div>',
                                        '<tpl for="persons">',
                                        	'<b>cucu{fname}</b>',
                                        '</tpl>'
                
                						),
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
                            xtype : 'grid',
                            region : 'center',
                            collapsible : false,
                            split : false,
                            id : 'studyvariables',
                            width : '100%',
                            flex : 1,
                            autoScroll : true,
                            remoteSort : false,

                            viewConfig: {
                                plugins: {
                                    ptype: 'gridviewdragdrop',
                                    dragGroup: 'firstGridDDGroup',
                                    dropGroup: 'secondGridDDGroup'
                                },
                                copy: true,
                            },
                            
                            
                            
                            columns : [
                                    {
                                        xtype : 'gridcolumn',
                                        dataIndex : 'name',
                                        hideable : false,
                                        text : translations.stvgname,
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
                                        text : translations.stvglabel,
                                        flex : 1
                                    },

                            ]
                        },
                        {
                            xtype : 'tabpanel',
                            itemId : 'vardet',
                            region : 'east',
                            collapsible : true,
                            split : true,
                            flex : 1,
                            layout : 'fit',
                            items : [
                                    {
                                        xtype : 'panel',
                                        title : translations.stvariabledetails,
                                        itemId: 'vardetails',
                                        autoScroll : true,
                                        //layout : 'fit',
                                        layout : {
                                          type : 'vbox',
                                            align : 'stretch'
                                        },
                                    },
                                    {
                                        xtype : 'panel',
                                        autoScroll : true,
                                        itemId: 'varanalyze',
                                        title : translations.stanalyze,
                                        items : [
                                                {
                                                    xtype : 'gridpanel',
                                                    id : 'analysisvar',
                                                    width : '100%',
                                                    height: 100,
                                                    store : 'MemoryVariableStore',
                                                    flex : 1,
                                                    
                                                    
                                                    
                                                    
                                                    viewConfig: {
                                                        plugins: {
                                                            ptype: 'gridviewdragdrop',
//                                                            dragGroup: 'secondGridDDGroup',
                                                            dropGroup: 'firstGridDDGroup'
                                                        },
//                                                        listeners: {
//                                                            drop: function(node, data, dropRec, dropPosition) {
//                                                                var dropOn = dropRec ? ' ' + dropPosition + ' ' + dropRec.get('name') : ' on empty view';
//                                                                Ext.example.msg("Drag from left to right", 'Dropped ' + data.records[0].get('name') + dropOn);
//                                                            }
//                                                        }
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
                                                    text : translations.stansend,
                                                    width : '100%'
                                                }, {
                                                    xtype : 'panel',
                                                    itemId : 'analResults',
                                                    bodyPadding : 5,
                                                    autoScroll : true,
                                                    layout : {
                                                        type : 'vbox',
                                                        align : 'stretch'
//                                                        align : 'center'
                                                    },
                                                }
                                        ]
                                    }
                            ]

                        }

                ]
            // end gridpanel

            },
//            {
//                autoScroll : true,
//                layout : 'fit',
//                title : translations.stdate,
//                id : 'sdata'
//            },
            {
                autoScroll : true,
                layout : 'fit',
                title : translations.stdocuments,
                id : 'sfiles',
                items : [
	                {
	                    id : 'studydocuments',
	                    xtype : 'dataview',
	                    tpl : new Ext.XTemplate('<div class="studyfiles">', '<tpl for=".">',
	                                            '<div class="studyfiledl">', '<H2>{filename}</H2>',
	                                            '<p>{filedescription}</p>',
	                                            '<a  class="file-{filetype}" href="{fileurl}">' + translations.dwnlfile + '</a>',
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