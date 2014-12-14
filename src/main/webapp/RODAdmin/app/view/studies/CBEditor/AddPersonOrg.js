/**
 * 
 */
Ext.define('RODAdmin.view.studies.CBEditor.AddPersonOrg', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.addpersonorg',
	height : '60%',
	width : '20%',

	requires : ['RODAdmin.util.Util'],
	

	layout : {
		type : 'fit'
	},

	config : {
		cnode : {},
		originalStore: {}
	},

	initComponent : function() {
  	    var personstore = Ext.create('RODAdmin.store.metadata.Persons');		
  	    var orgstore = Ext.create('RODAdmin.store.metadata.Orgs');	

  	    var prefixstore = Ext.create('RODAdmin.store.metadata.Prefixes');	
  	    var suffixstore = Ext.create('RODAdmin.store.metadata.Sufixes');	
  	    var citystore = Ext.create('RODAdmin.store.metadata.City');	
  	    
  	    var me = this;
		Ext.applyIf(me, {
				layout : 'fit',
				items : [{
					xtype : 'tabpanel',
					itemId : 'personorg',
					bodyPadding : 5,
					layout : {
						type : 'fit'
					},
					items : [{
								xtype : 'form',
								autoScroll : true,
								itemId : 'personform',
								title : 'Person',
								bodyPadding : 5,
								layout : {
									type : 'vbox',
									align: 'stretch'
								},
								items : [{
												xtype : 'fieldset',
												itemId : 'experson',
												collapsible : true,
												autoHeight : true,
												layout : {
													type : 'vbox',
													align: 'stretch'
												},												
												title : 'Select an existing person',
												items : [{
														xtype : 'combo',
														labelWidth : 80,
														name : 'experson',
														itemId : 'expersonselect',
														fieldLabel : 'Person',
														displayField : 'name',
														valueField : 'id',
														store : personstore
													}]
												},{
												xtype : 'fieldset',
												itemId : 'newperson',
												title : 'Or, add a new person',	
												collapsible : true,
												autoHeight : true,
												layout : {
													type : 'vbox',
													align: 'stretch'
												},														
												items : 	[
												        	 {
																xtype : 'combo',
																labelWidth : 80,
																name : 'prefix',
																itemId: 'cprefix',
																fieldLabel : 'Prefix',
																displayField : 'name',
																valueField : 'id',
																store: prefixstore	
																}
												        	 , {
																	xtype : 'textfield',
																	name : 'fname',
																	labelWidth : 80,																	
																	fieldLabel : 'First Name'
																}, {
																	xtype : 'textfield',
																	name : 'lname',
																	labelWidth : 80,																	
																	fieldLabel : 'Last Name'
																}, 
																{
																	xtype : 'combo',
																	labelWidth : 80,
																	name : 'suffix',
																	itemId: 'csuffix',
																	fieldLabel : 'Suffix',
																	displayField : 'name',
																	valueField : 'id',
																	store: suffixstore	
															}
																
																, {
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
																			},
																			{
																				xtype : 'combo',
																				labelWidth : 80,
																				name : 'city',
																				fieldLabel : 'City',
																				displayField : 'name',
																				valueField : 'id',
																				store: citystore	
																					
																			}
																			
																			]  //end fieldset address
														}] //end fieldset add new person items
												}] //end person form fieldsets
											}, // end person form
							{
								title : 'Organization',
								xtype : 'form',
								itemId : 'orgform',
								autoScroll : true,								
								bodyPadding : 5,
								layout : {
									type : 'vbox',
									align: 'stretch'
								},
								items : [{
											xtype : 'fieldset',
											title : 'Select an existing organization',
											layout : {
													type : 'vbox',
													align: 'stretch'
											},		
											itemId : 'exorg',
											items : [{
														xtype : 'combo',
														labelWidth : 80,
														name : 'exorg',
														itemId : 'exorgselect',
														fieldLabel : 'Organisation',
														displayField : 'name',
														valueField : 'id',
														store: orgstore	
													}]
										}, {
											xtype : 'fieldset',
											title : 'Select an existing organisation',
											itemId : 'neworg',
											layout : {
													type : 'vbox',
													align: 'stretch'
											},		
											items : [{
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
										}] //end fieldset add new org
							}] //end org form fieldsets
								
						}] //end panel items
				}] // end window items
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});