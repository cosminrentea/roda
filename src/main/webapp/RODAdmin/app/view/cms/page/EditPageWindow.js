/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.EditPageWindow', {

    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.pageedit',

    height : '90%',
    width : '80%',

    requires : [
	    'RODAdmin.util.Util'
    ],

    layout : {
	    type : 'border'
    },

    config : {
	    cnode : {}
    },

    initComponent : function() {
	    var me = this;
	    Ext.applyIf(me, {
		    items : [
		            {
		                region : 'center',
		                collapsible : false,
		                flex : 3,
		                split : true,
		                layout : 'fit',
		                items : [
			                {
			                    xtype : 'form',
			                    labelWidth : 40,
			                    frame : true,
			                    defaults : {
				                    labelWidth : 80,
			                    },
			                    items : [
			                            {
			                                fieldLabel : 'Parent',
			                                xtype : 'displayfield',
			                                name : 'parent',
			                                anchor : '-18',
			                            },
			                            {
											xtype : 'hiddenfield',
											fieldLabel : 'Parent',
											name : 'parentid',
//											value : '',
//											itemId : 'groupid'
										},
			                            {
			                                fieldLabel : 'Title',
			                                xtype : 'textfield',
			                                anchor : '-18',
			                                name : 'title',
			                                itemId : 'pagetitle',
			                            }, {
			                                fieldLabel : 'URL',
			                                xtype : 'textfield',
			                                anchor : '-18',
			                                name : 'url',
			                                itemId : 'pageurl',
			                            }, {
			                                fieldLabel : 'MenuTitle',
			                                xtype : 'textfield',
			                                anchor : '-18',
			                                name : 'menutitle',
			                                itemId : 'pagemenutitle',
			                            }, {
			                                xtype : 'panel',
			                                margin : '0 0 0 85px',
			                                layout : 'column',
			                                bodyStyle : 'padding:0 18px 0 0; background:transparent;',
			                                border : false,
			                                defaults : {
			                                    layout : 'form',
			                                    xtype : 'panel',
			                                    border : false,
			                                    bodyStyle : 'background:transparent;',
			                                },
			                                items : [
			                                        {
			                                            columnWidth : 0.5,
			                                            items : [
				                                            {
				                                                xtype : 'combo',
				                                                labelWidth : 60,
				                                                name : 'layout',
				                                                fieldLabel : 'Layout',
				                                                anchor : '100%',
				                                                displayField: 'name',
				                                            //    forceSelection : true,
				                                                typeAhead: true,
				                                                valueField: 'indice',
				                                                tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
				                                                store : 'cms.layout.Layout'
				                                            }
			                                            ]
			                                        }, {
			                                            columnWidth : 0.25,
			                                            margin : '0 0 0 10px',
			                                            items : [
				                                            {
				                                                xtype : 'combo',
				                                                labelWidth : 60,
				                                                name: 'lang',
				                                                fieldLabel : 'Language',
				                                                anchor : '100%',
				                                                store : [
				                                                        'en', 'ro'
				                                                ]
				                                            }
			                                            ]
			                                        }, {
			                                            columnWidth : 0.25,
			                                            margin : '0 0 0 10px',
			                                            items : [
				                                            {
				                                                xtype : 'combo',
				                                                labelWidth : 60,
				                                                name: 'published',
//				                                                queryMode: 'local',
				                                                fieldLabel : 'Published',
				                                                displayField: 'name',
				                                                valueField: 'published',
				                                                anchor : '100%',
				                                                store : 'local.PagePublished'
//				                                                store : [["fname":"Yes","value":"true"},{"fname":"No","value":"false"}]
				                                            }
			                                            ]
			                                        }

			                                ]
			                            },
			                            {
			                                xtype : 'fieldset',
			                                margin : '0 0 0 85px',
			                                collapsible : true,
			                                collapsed : true,
			                                title : 'Other options',
			                                items : [
			                                        {
			                                            xtype : 'panel',
			                                            layout : 'column',
			                                            bodyStyle : 'background:transparent;',
			                                            border : false,
			                                            defaults : {
			                                                layout : 'form',
			                                                xtype : 'panel',
			                                                border : false,
			                                                bodyStyle : 'background:transparent;',
			                                            },
			                                            items : [
			                                                    {
			                                                        columnWidth : 0.5,
			                                                        items : [
				                                                        {
				                                                            xtype : 'textfield',
				                                                            labelWidth : 90,
				                                                            fieldLabel : 'External Redirect',
				                                                            anchor : '100%',
				                                                        }
			                                                        ]
			                                                    }, {
			                                                        columnWidth : 0.5,
			                                                        margin : '0 0 0 10px',
			                                                        items : [
				                                                        {
				                                                            xtype : 'textfield',
				                                                            labelWidth : 90,
				                                                            fieldLabel : 'Internal Redirect',
				                                                            anchor : '100%',
				                                                        }
			                                                        ]
			                                                    },
			                                            ]
			                                        }, {
			                                            xtype : 'panel',
			                                            layout : 'column',
			                                            bodyStyle : 'background:transparent;',
			                                            border : false,
			                                            defaults : {
			                                                layout : 'form',
			                                                xtype : 'panel',
			                                                border : false,
			                                                bodyStyle : 'background:transparent;',
			                                            },
			                                            items : [
			                                                    {
			                                                        columnWidth : 0.2,
			                                                        items : [
				                                                        {
				                                                            xtype : 'combo',
				                                                            labelWidth : 50,
				                                                            fieldLabel : 'Default',
				                                                            name: 'default',
				                                                            anchor : '100%',
				                                                            store : [
				                                                                    'Yes', 'No'
				                                                            ]
				                                                        }
			                                                        ]
			                                                    }, {
			                                                        columnWidth : 0.2,
			                                                        margin : '0 0 0 10px',
			                                                        items : [
				                                                        {
				                                                            xtype : 'textfield',
				                                                            name : 'cacheable',
				                                                            labelWidth : 65,
				                                                            fieldLabel : 'Cache',
				                                                            anchor : '100%',
				                                                        }
			                                                        ]
			                                                    }, {
			                                                        columnWidth : 0.2,
			                                                        margin : '0 0 0 10px',
			                                                        items : [
				                                                        {
				                                                            xtype : 'combo',
				                                                            labelWidth : 65,
				                                                            name : 'searchable',
				                                                            fieldLabel : 'Search',
				                                                            anchor : '100%',
				                                                            store : [
				                                                                    'Yes', 'No'
				                                                            ]
				                                                        }
			                                                        ]
			                                                    }, {
			                                                        columnWidth : 0.2,
			                                                        margin : '0 0 0 10px',
			                                                        items : [
				                                                        {
				                                                            xtype : 'combo',
				                                                            labelWidth : 50,
				                                                            fieldLabel : 'Target',
				                                                            anchor : '100%',
				                                                            store : [
				                                                                    '_blonk', '_top'
				                                                            ]
				                                                        }
			                                                        ]
			                                                    }, {
			                                                        columnWidth : 0.2,
			                                                        margin : '0 0 0 10px',
			                                                        items : [


			                                                                 {
			     				                                                xtype : 'combo',
			     				                                                labelWidth : 60,
			     				                                                name : 'pagetype',
			     				                                                fieldLabel : 'Type',
			     				                                                anchor : '100%',
			     				                                                displayField: 'name',
			     				                                            //    forceSelection : true,
			     				                                                typeAhead: true,
			     				                                                valueField: 'indice',
			     				                                                tpl: '<tpl for="."><div class="x-boundlist-item"><strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
			     				                                                store : 'cms.pages.PageType'
			     				                                            }			                                                                 
//			                                                                 
//			                                                                 
//			                                                                 {
//				                                                            xtype : 'textfield',
//				                                                            name : 'pagetype',
//				                                                            labelWidth : 60,
//				                                                            //queryMode: 'local',
//				                                                            fieldLabel : 'Type',
//				                                                            anchor : '100%',
//				                                                            store : [
//				                                                                    'Content', 'Static', '404'
//				                                                            ]
//				                                                        }
			                                                        ]
			                                                    }
			                                            ]
			                                        }
			                                ]
			                            }, {
			                                fieldLabel : 'Synopsis',
			                                xtype : 'textarea',
			                                anchor : '-18',
			                                name : 'synopsis'
			                            },
			                            {
									        xtype: 'codemirror',
        									fieldLabel: 'Content',
			                                anchor : '-98 70%',
        									itemId: 'content',
        									name: 'content',
        									mode: 'htmlmixed',
        									listModes:'',
        									showModes: false,
        									pathModes: 'CodeMirror-2.02/mode',
        									pathExtensions: 'CodeMirror-2.02/lib/util',
        									flex:1,
        									value: ''
										}
			                            
			                    ]
			                }
		                ]
		            }, {
		                region : 'west',
		                collapsible : true,
		                flex : 1,
		                split : true,
		                layout : 'fit',
		                items : [
			                {
			                    xtype : 'treepanel',
			                    store : Ext.create('RODAdmin.store.cms.pages.PageTree'),
			                    itemId : 'parentselect',
			                    displayField : 'title',
			                    useArrows : true,
			                    rootVisible : false,
			                    multiSelect : false,
			                    singleExpand : false,
			                    allowDeselect : true,
			                    autoheight : true
			                }
		                ]
		            }
		    ]
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});