/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.EditPageWindow', {

    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.pageedit',

    height : '90%',
    width : '60%',

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
			                                anchor : '-18',
			                            }, {
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
				                                                fieldLabel : 'Layout',
				                                                anchor : '100%',
				                                                store : [
				                                                        'Item 1', 'Item 2'
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
				                                                fieldLabel : 'Published',
				                                                anchor : '100%',
				                                                store : [
				                                                        'Yes', 'No'
				                                                ]
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
				                                                            fieldLabel : 'Externalredirect',
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
				                                                            fieldLabel : 'Internalredirect',
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
				                                                            labelWidth : 65,
				                                                            fieldLabel : 'Cacheable',
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
				                                                            fieldLabel : 'Searchable',
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
				                                                            xtype : 'textfield',
				                                                            labelWidth : 60,
				                                                            fieldLabel : 'Pagetype',
				                                                            anchor : '100%',
				                                                            store : [
				                                                                    'Content', 'Static', '404'
				                                                            ]
				                                                        }
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
			                            }, {
			                                fieldLabel : 'Content',
			                                xtype : 'htmleditor',
			                                anchor : '-18 -80',
			                                name : 'content',
			                                plugins: [
					                                 Ext.create('Ext.ux.form.HtmlEditor.RButtons')
						                           ],

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