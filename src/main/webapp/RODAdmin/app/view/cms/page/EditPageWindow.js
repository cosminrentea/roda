/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.EditPageWindow', {

//    extend : 'RODAdmin.view.common.WindowForm',
  extend : 'Ext.window.Window',
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
			                                fieldLabel : translations.pg_parent,
			                                xtype : 'displayfield',
			                                name : 'parent',
			                                anchor : '-18',
			                            },
			                            {
											xtype : 'hiddenfield',
											fieldLabel : 'Id',
											name : 'id',
//											value : '',
										},
			                            {
											xtype : 'hiddenfield',
											fieldLabel : 'Parent',
											name : 'parentid',
//											value : '',
//											itemId : 'groupid'
										},
			                            {
			                                fieldLabel : translations.ly_title,
			                                xtype : 'textfield',
			                                anchor : '-18',
			                                name : 'title',
			                                itemId : 'pagetitle',
			                            }, {
			                                fieldLabel : 'URL',
			                                xtype : 'textfield',
			                                anchor : '-18',
			                                name : translations.ly_url,
			                                itemId : 'pageurl',
			                            }, {
			                                fieldLabel : 'MenuTitle',
			                                xtype : 'textfield',
			                                anchor : '-18',
			                                name : translations.pg_menutitle,
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
				                                                fieldLabel : translations.pg_layout,
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
				                                                fieldLabel : translations.lang,
				                                                anchor : '100%',
				                                                store : [
				                                                        'en', 'ro'
				                                                ]
				                                            }
			                                            ]
			                                        }, {
			                                            columnWidth : 0.15,
			                                            margin : '0 0 0 10px',
			                                            items : [
				                                            {
				                                                xtype : 'combo',
				                                                labelWidth : 60,
				                                                name: 'published',
//				                                                queryMode: 'local',
				                                                fieldLabel :  translations.pg_published,
				                                                displayField: 'name',
				                                                valueField: 'published',
				                                                anchor : '100%',
				                                                store : 'local.PagePublished'
//				                                                store : [["fname":"Yes","value":"true"},{"fname":"No","value":"false"}]
				                                            }
			                                            ]
			                                        },
			                                        {
			                                            columnWidth : 0.10,
			                                            margin : '0 0 0 10px',
			                                            items : [
				                                            {
				                                                xtype : 'checkboxfield',
				                                                labelWidth : 60,
				                                                name: 'navigable',
//				                                                queryMode: 'local',
				                                                fieldLabel : translations.pg_navigable,
				                                                anchor : '100%',
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
			                                title :translations.pg_otheroptions,
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
				                                                            name: 'externalredirect',
				                                                            fieldLabel : translations.pg_externalredir,
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
				                                                            name : 'internalredirect',
				                                                            fieldLabel :  translations.pg_internalredir,
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
				                                                            fieldLabel : translations.pg_default,
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
				                                                            fieldLabel :translations.pg_cacheable,
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
				                                                            fieldLabel : translations.pg_searchable,
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
				                                                            name : 'target',
				                                                            fieldLabel : translations.pg_target,
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
			     				                                                fieldLabel : translations.pg_pagetype,
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
			                                fieldLabel : translations.pg_synopsis,
			                                xtype : 'textarea',
			                                anchor : '-18',
			                                name : 'synopsis'
			                            },
			                            {
									        xtype: 'codemirror',
        									fieldLabel: translations.pg_content,
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
		    ],
			dockedItems : 
			{
				dock : 'top',
				xtype : 'toolbar',
//				itemId : 'audittoolbar',
					items : [{
							xtype : 'tbfill'
							},
							{
					            xtype: 'button',
					            text: 'Preview',
					            itemId: 'pagepreview',
					            iconCls: 'pagepreview'
							},
							{
					            xtype: 'button',
					            text: 'Cancel',
					            itemId: 'cancel',
					            iconCls: 'cancel'
					        },
					        {
					            xtype: 'button',
					            text: 'Save',
					            itemId: 'save',
					            iconCls: 'save'
					        }	               
			               ]
			}
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});