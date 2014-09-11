/**
 * 
 */
Ext.define('RODAdmin.view.metadata.mcomponents.AddOrg', {
    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.editorg',
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
			                    itemId : 'addorgform',
			                    bodyPadding : 5,
			                    layout : {
				                    type : 'form',
			                    },

								items : [
						         {
										xtype : 'combo',
										labelWidth : 80,
										name : 'orgprefix',
										itemId: 'orgprefix',
										fieldLabel : 'OrgPrefix'
						         }, {
										xtype : 'textfield',
										name : 'shortname',
										labelWidth : 80,												
										fieldLabel : 'Short Name'
								}, {
										xtype : 'textfield',
										name : 'fullname',
										labelWidth : 80,												
										fieldLabel : 'Full Name'
								}, {
										xtype : 'combo',
										labelWidth : 80,
										name : 'orgsuffix',
										itemId: 'orgsuffix',
										fieldLabel : 'Suffix'
								}, {
										xtype : 'fieldset',
										title : 'Address',
										layout : {
												type : 'vbox',
												align: 'stretch'
										},		
										itemId: 'neworgAddress',
										items : [{
												xtype : 'textfield',
												labelWidth : 80,
												name : 'address1',
												fieldLabel : 'Address1'
											}, {
												xtype : 'textfield',
												labelWidth : 80,
												name : 'address2',
												fieldLabel : 'Address2'
											}, {
												xtype : 'textfield',
												labelWidth : 80,
												name : 'postalcode',
												fieldLabel : 'Postal Code'
											}, {
												xtype : 'combo',
												labelWidth : 80,
												name : 'city',
												fieldLabel : 'City'
										}] //end address fieldset
									}]
			                }]
    
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});