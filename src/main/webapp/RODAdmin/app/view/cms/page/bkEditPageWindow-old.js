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
		                // width : '50%',
		                flex : 3,
		                split : true,
		                layout : 'fit',
		                items : [
			                {
			                    xtype : 'form',
			                    itemId : 'epageform',
			                    bodyPadding : 5,
			                    layout : {
				                    type : 'vbox',
			                    },
			                    items : [
				                    {
				                        xtype : 'fieldset',
				                        collapsible: true,
				                        title : 'Page information',
				                        defaults : {
				                            afterLabelTextTpl : RODAdmin.util.Util.required,
				                            xtype : 'textfield',
				                            allowBlank : false,
				                            layout : {
					                            type : 'anchor',
					                            align : 'stretch'					                            
				                            },
				                            labelWidth : 60
				                        },
				                        items : [
				                                {
				                                    xtype : 'displayfield',
				                                    itemId : 'parentdfield',
				                                    fieldLabel : 'Parent Page',
				                                    name : 'parentpath',
				                                    value : ''
				                                }, {
				                                    xtype : 'textfield',
				                                    fieldLabel : 'Title',
				                                    name : 'title',
				                                    itemId : 'pagetitle',
				                                    value : ''
				                                }, {
				                                    xtype : 'hiddenfield',
				                                    fieldLabel : 'Label',
				                                    name : 'parent',
				                                    value : '',
				                                    itemId : 'parentid'
				                                }, {
				                                    xtype : 'hiddenfield',
				                                    fieldLabel : 'Label',
				                                    name : 'id',
				                                    value : '',
				                                    itemId : 'id'

				                                }
				                        ]
				                    },

				                    {
			                        xtype : 'fieldset',
			                        collapsible: true,
			                        title : 'Page content',
			                        defaults : {
			                            afterLabelTextTpl : RODAdmin.util.Util.required,
			                            xtype : 'textarea',
			                            allowBlank : false,
			                            layout : {
				                            type : 'anchor',
			                            // columns:2
				                            align : 'stretch'
			                            },
			                            labelWidth : 60
			                        },
			                        items : [
			                                 {
			                                    xtype : 'textarea',
			                                    fieldLabel : 'Synopsis',
			                                    name : 'synopsis',
			                                    flex: 1,
			                                    itemId : 'synopsis',
			                                    value : ''
			                                }, 
			                                {
			                                    xtype : 'textarea',
			                                    fieldLabel : 'Content',
			                                    name : 'content',
			                                    flex: 3,
			                                    itemId : 'content',
			                                    value : ''
			                                }, 
			                                {
			                                    xtype : 'button',
			                                    text : 'Insert collateral',
			                                    listeners : {
				                                    click : function(button, e, eOpts) {
					                                    // button.up('fieldset').add({xtype:'filefield',
														// name: 'file',
														// label:'File'});
					                                    // var toremove =
														// button.up('fieldset').query('displayfield')[1];
					                                    // button.up('fieldset').remove(toremove);
					                                    // button.hide();
					                                    // var values =
														// this.form.getFieldValues();
					                                    RODAdmin.util.Alert.msg('We try here');
				                                    }
			                                    }
			                                }
			                        ]
			                    } 				                    
				                    
			                    ]
			                }
		                ]
		            }, {
		                region : 'west',
		                collapsible : true,
		                // width : '50%',
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