/**
 * 
 */
Ext.define('RODAdmin.view.metadata.mcomponents.AddPerson', {
    extend : 'RODAdmin.view.common.WindowForm',
    alias : 'widget.editperson',
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

								items : 	[{
									xtype : 'combo',
									labelWidth : 80,
									name : 'prefix',
									itemId: 'cprefix',
									fieldLabel : 'Prefix'
									}, {
										xtype : 'textfield',
										name : 'fname',
										labelWidth : 80,																	
										fieldLabel : 'First Name'
									}, {
										xtype : 'textfield',
										name : 'lname',
										labelWidth : 80,																	
										fieldLabel : 'Last Name'
									}, {
										xtype : 'combo',
										labelWidth : 80,
										name : 'suffix',
										itemId: 'csuffix',
										fieldLabel : 'Suffix'
								}, {
										xtype : 'textfield',
										labelWidth : 80,
										name : 'email',
										fieldLabel : 'Email'
								}, {
										xtype : 'textfield',
										labelWidth : 80,
										name : 'phone',
										fieldLabel : 'Phone'
								}, {
										xtype : 'fieldset',
										title : 'Address',
										layout : {
												type : 'vbox',
												align: 'stretch'
										},		
										itemId: 'newpersonAddress',
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
												}]  //end fieldset address
							}] //end fieldset add new person items
			                
			                }]
    
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});