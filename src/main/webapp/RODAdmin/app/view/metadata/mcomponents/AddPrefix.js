/**
 * 
 */
Ext.define('RODAdmin.view.metadata.mcomponents.AddPrefix', {
    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.editprefix',
//    editId : 0,
    layout: 'fit',
    autoHeight : true,
//    width : '40%',
    // singleton:true,
    closeAction : 'destroy',
	config : {
		cnode : {},
		mode : 'add',
		editId : 0
	},
    initComponent : function() {
	    var me = this;
	    Ext.applyIf(me, {
		                items : [
			                {
			                    xtype : 'form',
			                    itemId : 'addprefixform',
			                    bodyPadding : 5,
			                    layout : {
				                    type : 'form',
			                    },
				                        items : [
				                                 {
				                                    xtype : 'textfield',
				                                    fieldLabel : 'Name',
				                                    name : 'name',
				                                    itemId : 'name',
				                                    value : ''
				                                },{
				                                    xtype : 'hiddenfield',
				                                    name : 'id',
				                                    value : '',
				                                    itemId : 'id'

				                                }
				                        ]}
		                ]
    
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});