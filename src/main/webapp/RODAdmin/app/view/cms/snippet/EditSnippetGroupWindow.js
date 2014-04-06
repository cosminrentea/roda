/**
 * 
 */
Ext.define('RODAdmin.view.cms.snippet.EditSnippetGroupWindow', {

    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.snippetgroupedit',

    height : '60%',
    width : '40%',
    // singleton:true,
    closeAction : 'destroy',
    id : 'snippetgroupedit',

    // requires : ['RODAdmin.util.Util','Ext.ux.form.field.CodeMirror'],

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
//	                    layout : {
//		                    type : 'fit',
//		                    anchor : '100%',
//	                    },

		                items : [
			                {
			                    xtype : 'form',
			                    itemId : 'esnippetform',
			                    bodyPadding : 5,
			                    layout : {
				                    type : 'form',
//				                    anchor : '100%',
			                    },
//			                    items : [
//				                    {
//				                        xtype : 'fieldset',
//				                        // flex : 2,
//				                        title : 'Layout information',
//				                        bodyStyle:{"background-color":"red"}, 
//				                        layout : {
//					                        type : 'fit',
//				                        },
//				                        defaults : {
//				                            afterLabelTextTpl : RODAdmin.util.Util.required,
//				                            anchor : '100%',
//				                            xtype : 'textfield',
//				                            allowBlank : false,
//				                            layout : {
//					                            type : 'vbox',
//				                            // columns:2
//				                            // align : 'stretch'
//				                            },
//				                            labelWidth : 60
//				                        },
				                        items : [
				                                {
				                                    xtype : 'displayfield',
				                                    itemId : 'parentdfield',
				                                    fieldLabel : 'Parent Folder',
				                                    name : 'directory',
				                                    value : ''
				                                }, {
				                                    xtype : 'textfield',
				                                    fieldLabel : 'Name',
				                                    name : 'name',
				                                    itemId : 'name',
				                                    value : ''
				                                }, {
				                                    xtype : 'textareafield',
				                                    fieldLabel : 'Description',
				                                    itemId : 'description',
				                                    height: 200,
				                                    name : 'description',
//				                                    flex: 3,
				                                    // colspan: 2,
				                                    value : ''
				                                }, {
				                                    xtype : 'hiddenfield',
				                                    fieldLabel : 'Label',
				                                    name : 'parent',
				                                    value : '',
				                                    itemId : 'groupid'
				                                }, {
				                                    xtype : 'hiddenfield',
				                                    fieldLabel : 'Label',
				                                    name : 'id',
				                                    value : '',
				                                    itemId : 'id'

				                                }
				                        ]
//				                    }
//			                    ]
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
			                    store : Ext.create('RODAdmin.store.cms.snippet.GroupTree'),
			                    itemId : 'groupselect',
			                    displayField : 'name',
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