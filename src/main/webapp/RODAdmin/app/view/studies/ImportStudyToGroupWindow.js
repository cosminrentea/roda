/**
 * 
 */
Ext.define('RODAdmin.view.studies.ImportStudyToGroupWindow', {

    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.studygimport',

    height : '70%',
    width : '40%',

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
				            itemId : 'estudyform',
				            bodyPadding : 5,
				            layout : {
					            type : 'fit',
				            },
				            items : [
					            {
					                xtype : 'fieldset',
					                // flex : 2,
					                itemId: 'sfiles',
					                title : 'Study files',
					                defaults : {
					                    afterLabelTextTpl : RODAdmin.util.Util.required,
					                    anchor : '100%',
					                    xtype : 'textfield',
					                    allowBlank : false,
					                    layout : {
						                    type : 'vbox',
					                    // columns:2
					                    // align : 'stretch'
					                    },
					                    labelWidth : 60
					                },
					                items : [
					                        {
					                            xtype : 'grid',
					                            itemId : 'filesdisplay',
					                            store : 'studies.CBEditor.Files',
					                            columns : [
					                                    {
					                                        itemId : 'id',
					                                        text : 'ID',
					                                        sortable : true,
					                                        hidden : true,
					                                        dataIndex : 'id'
					                                    },
					                                    {
					                                        itemId : 'name',
					                                        text : 'File name',
					                                        sortable : true,
					                                        flex : 1,
					                                        dataIndex : 'fname'
					                                    },
					                                    {
					                                        itemId : 'ftype',
					                                        text : 'File type',
					                                        sortable : true,
					                                        flex : 1,
					                                        dataIndex : 'ftype',
					                                    },
					                                    {
					                                        itemId : 'uptype',
					                                        text : 'Upload type',
					                                        sortable : true,
					                                        flex : 1,
					                                        dataIndex : 'uptype',
					                                    },
					                                    {
					                                        itemId : 'uploadid',
					                                        text : 'Upload id',
					                                        sortable : true,
					                                        flex : 1,
					                                        dataIndex : 'uploadid',
					                                    },
					                                    {
					                                        itemId : 'furi',
					                                        text : 'URI',
					                                        flex : 2,
					                                        sortable : true,
					                                        dataIndex : 'uri'
					                                    },
					                                    {
					                                        xtype : 'actioncolumn',
					                                        width : 60,
					                                        sortable : false,
					                                        menuDisabled : true,
					                                        itemId : 'actionfiledisplay',
					                                        items : [
						                                        {
						                                            icon : 'images/delete.gif',
						                                            tooltip : 'Delete file',
						                                            scope : this,
						                                            handler : function(view, rowIndex, colIndex, item,
						                                                    e, record, row) {
							                                            console.log('firing event delete');
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
					                            itemId : 'addfile',
					                            text : 'Add File'
					                        }

					                ]
					            }
				            ]
				        }
			        ]
			    }
		    ]
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});