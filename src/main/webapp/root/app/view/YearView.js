
Ext.define('databrowser.view.YearView', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.yearview',
    autoRender: true,
    width: '100%',
    header: true,
    hideHeaders: true,
    title: translations.yearview,
	config : {
		currentview : 'simple',
	},
    loadStudy: function(id) {
    	var year = this.title;
    	var stitle = this.getView().store.findRecord('indice', id).data.name;
    	var san = this.getView().store.findRecord('indice', id).data.an;
        var dbcard = Ext.getCmp('dbcard');
    	dbcard.layout.setActiveItem('studyview');	
    	var studyviewob = Ext.getCmp('studyview');
    	studyviewob.setTitle(year + ' -> ' + stitle);
    	studyviewob.loaddata(id);
    },    
    loaddata: function(id){
    	var cStore = Ext.StoreManager.get('YearStore');    	
    	cStore.load({
    		id: id, //set the id here
    		  scope:this,
    		  callback: function(records, operation, success){
    		    if(success){
    		    	var rec = cStore.first();
    		    	this.getView().bindStore(rec.studiesStore);
    		    }
    		  }
    		});
    },
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            viewConfig: {
                getRowClass: function(record, rowIndex, rowParams, store) {
                    return 'x-hide-display';
                },
                frame: true,
                id: 'YearGridView',
                trackOver: false,
                stripeRows: false
            },
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    id: 'YearToolbar',
                    width:300,
                    items: [
                        {
                            xtype: 'textfield',
							itemId : 'searchbox',
							id : 'yearSearch',
                            width: 190,
                            emptyText: 'Cautare locala'
                        },
                        {
                            xtype: 'tbseparator',
                            id: 'YearDataBrowserToolbarSeparator1'
                        },
                        {
                            xtype: 'tbfill',
                        },
                        {
                            xtype: 'buttongroup',
                            autoRender: false,
                            id: 'YearSMCButtonGroup',
                            header: false,
                            frame:false,
                            title: 'Buttons',
                            columns: 3,
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'YSButton',
                                    enableToggle: true,
                                    icon: databrowser.util.Globals['contextPath'] + '/resources/root/img/simple.png',
                                    toggleGroup: 'YearSMCButtonGroup',
                                    listeners: {
                                        click: {
                                            fn: me.onYSButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    id: 'YMButton',
                                    enableToggle: true,
                                    icon: databrowser.util.Globals['contextPath'] + '/resources/root/img/middle.png',
                                    toggleGroup: 'YearSMCButtonGroup',
                                    listeners: {
                                        click: {
                                            fn: me.onYMButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    id: 'YCButton',
                                    enableToggle: true,
                                    icon: databrowser.util.Globals['contextPath'] + '/resources/root/img/complex.png',
                                    toggleGroup: 'YearSMCButtonGroup',
                                    listeners: {
                                        click: {
                                            fn: me.onYCButtonClick,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'pagingtoolbar',
                    dock: 'bottom',
                    id: 'YearPagingToolbar',
                    width: '100%',
                    afterPageText: translations.of + ' {0}',
                    beforePageText: translations.beforePageText,
                    displayInfo: true,
                    displayMsg: translations.displayitems  + ' {0} - {1} ' + translations.of + ' {2}',
                    emptyMsg: translations.noitems,
                    firstText: translations.pagefirstText,
                    lastText: translations.pagelastText,
                    nextText: translations.pagenextText,
                    prevText: translations.pageprevText,
                    refreshText: translations.pagerefreshText,
                    items: [
                        {
                            xtype: 'textfield',
                            id: 'YearNumberOfRecords',
                            width: 89,
                            fieldLabel: translations.nrrecords,
                            labelPad: 0,
                            labelWidth: 60,
                            value: 20,
                            enableKeyEvents: true,
                            vtype: '',
                            listeners: {
                                blur: {
                                    fn: me.onNumberOfRecordsBlur,
                                    scope: me
                                },
                                specialkey: {
                                    fn: me.onNumberOfRecordsSpecialkey,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            features: [
                {
                    ftype: 'rowbody',
                    getAdditionalData: function(data, idx, record, orig) {
                        var headerCt = this.view.headerCt,
                            colspan = headerCt.getColumnCount(); 
                            var body;
                        var catview = Ext.ComponentQuery.query('yearview');
						var yview = catview[0].getCurrentview();
						if (yview == 'medium'|| yview == 'complex') {  
                           
							var bodystart = '' +
                            '<div style="margin: 0px 0px 0px 0px; width: 100%"><table style="table-layout: fixed; width: 100%">' +
                            '<tr>' +
                            '<td colspan="6" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<a href="javascript:Ext.getCmp(\'yearview\').loadStudy('+record.get('indice') + ')">' +
                            '<p style="font-size: 14px">' +
                            record.get("name") + '<br/>' +
                            '</p>' +
                            '</a>' +
                            '<p style="font-size: 10px">' +
                            '<b>' + record.get("an") + '</b>&nbsp&nbsp&nbsp&nbsp&nbsp' + record.get("author") +
                            '</p>'+
                            '</div>'+
                            '</td>' +
                            '<td colspan="3" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>' + translations.archivedate + ':</b> ' + record.get("an") + '<br/>' +
                            '<b>' + translations.metadataaccess + ':</b> Open<br/>' +
                            '<b>' + translations.dataaccess+ ':</b> Open<br/>' +
                            '</p>' +
                            '</div>'+
                            '</td>'+
                            '</tr>' +
                            '<tr>' +
                            '<td colspan="9" valign="middle">' +
                            '<div style="width: 100%; word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            record.get("description") +
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '</tr>';
                            
                            				
                  			var complexinsert = '<tr>' + 
                          '<td colspan="2" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>' + translations.countries + ':</b> ' + record.get("countries") +
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '<td colspan="2" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>'+translations.stdgeocover+': </b>' + record.get("geo_coverage") +
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '<td colspan="2" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>' + translations.stdunitanalysis+ ': </b>' + record.get("unit_analysis") +
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '<td colspan="3" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>'+ translations.stduniverse+':</b> ' + record.get("universe") + 
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '</tr>';
                  			
                            var bodyend = '</table>' +
                            '</div>' +
                            '<hr style="width: 100%; border: 2px groove">';
                            if (yview == 'complex') {
                            	body = bodystart + complexinsert + bodyend;
                            } else {
                                body = bodystart + bodyend;
                            }
                        }
                        else
                        {
                            body = '' +
                            '<div margin: 0px 0px 0px 0px style="width: 100%">' +
                            '<table style="table-layout: fixed; width: 100%">' +
                            '<colgroup>' +
                            '<col style="width: 80%" />' +
                            '<col style="width: 20%" />' +      
                            '</colgroup>' +
                            '<tr>' +
                            '<td colspan="1" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 12px">' +
                            '<b>' + record.get("an") + '</b>&nbsp&nbsp&nbsp&nbsp&nbsp' +
                            '<a href="javascript:Ext.getCmp(\'yearview\').loadStudy('+record.get('indice') + ')">' +            
                            '<b>' + record.get("name") + '</b>' +
                            '</a>' +
                            '</p>' +          
                            '</div>' +
                            '</td>' +      
                            '</tr>' +
                            '</table>' +
                            '</div>';
                        }
                        return {
                            rowBody: body,
                            rowBodyCls: this.rowBodyCls,
                            rowBodyColspan: colspan
                        };
                    }
                }
            ],
            columns: [
                {
                    xtype: 'gridcolumn',
                    width: '100%',
                    text: translations.detailgridcolumn,
                }
            ]
        });

        me.callParent(arguments);
    },

    onNumberOfRecordsBlur: function(component, e, eOpts) {
        this.store.pageSize = parseInt(component.value, 10);   
        var pagingToolbar = Ext.getCmp('YearPagingToolbar');
        pagingToolbar.doRefresh();
    },

    onNumberOfRecordsSpecialkey: function(field, e, eOpts) {
        if(e.getKey()==e.ENTER)
        {
            this.store.pageSize = parseInt(field.value, 10);
            var pagingToolbar = Ext.getCmp('YearPagingToolbar');
            pagingToolbar.doRefresh();
        }  
    }
});