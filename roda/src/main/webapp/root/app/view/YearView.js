
Ext.define('databrowser.view.YearView', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.yearview',
    autoRender: true,
    width: '100%',
    header: true,
    hideHeaders: true,
    title: 'Year view',
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
                            id: 'YearLocalSearchTextField',
                            width: 190,
                            emptyText: 'Cautare locala'
                        },
                        {
                            xtype: 'button',
                            id: 'YearAdvancedSearchButton',
                            width: 156,
                            text: 'Cautare avansata'
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
                            //width: 72,
                            header: false,
                            frame:false,
                            title: 'Buttons',
                            columns: 3,
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'YSButton',
                                    enableToggle: true,
//                                    text: 'S',
                                    icon: 'img/simple.png',
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
                                    icon: 'img/mediu.png',
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
                                    icon: 'img/complex.png',
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
                    afterPageText: 'din {0}',
                    beforePageText: 'Pagina',
                    displayInfo: true,
                    displayMsg: 'Afisare {0} - {1} din {2}',
                    emptyMsg: 'Nu exista date',
                    firstText: 'Prima pag.',
                    lastText: 'Ultima',
                    nextText: 'Prima',
                    prevText: 'Anterior',
                    refreshText: 'Actualizare',
                    items: [
                        {
                            xtype: 'textfield',
                            id: 'YearNumberOfRecords',
                            width: 89,
                            fieldLabel: 'Inregistrari',
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

                        var sButton = Ext.getCmp('YSButton'),
                            mButton = Ext.getCmp('YMButton'),
                            cButton = Ext.getCmp('YCButton');
                        if (cButton.pressed || mButton.pressed)
                        {    
                            body = '' +
                            '<div style="margin: 0px 0px 0px 0px; width: 100%">' +
                            '<table style="table-layout: fixed; width: 100%">' +
                            '<colgroup>' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 11%" />' +
                            '<col style="width: 12%" />' +    
                            '</colgroup>' +
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
                            '<b>Archive date:</b> ' + record.get("an") + '<br/>' +
                            '<b>Metadata access:</b> Open<br/>' +
                            '<b>Data access:</b> Open<br/>' +
                            '<a href="">' +
                            '<b>Adauga la catalog</b>' +
                            '</a>' +
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
                            '</tr>' +
                            (mButton.pressed ? '' : ('<tr>' +
                            '<td colspan="2" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>Countries:</b> ' + record.get("countries") +
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '<td colspan="2" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>Geographic coverage: </b>' + record.get("geo_coverage") +
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '<td colspan="2" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>Unitatea de analiza: </b>' + record.get("unit_analysis") +
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '<td colspan="3" valign="top">' +
                            '<div style="word-wrap: break-word">' +
                            '<p style="font-size: 10px">' +
                            '<b>Univers:</b> ' + record.get("universe") + 
                            '</p>' +
                            '</div>' +
                            '</td>' +
                            '</tr>')) +
                            '</table>' +
                            '</div>' +
                            '<hr style="width: 100%; border: 2px groove">';
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
                    text: 'DetailGridColumn'
                }
            ]
        });

        me.callParent(arguments);
    },

    onYSButtonClick: function(button, e, eOpts) {
        this.getView().refresh();

    },

    onYMButtonClick: function(button, e, eOpts) {
        this.getView().refresh();


    },

    onYCButtonClick: function(button, e, eOpts) {
        this.getView().refresh();
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