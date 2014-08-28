/**
 * 
 */
Ext.define('RODAdmin.view.studies.AddPersonOrg', {
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.addpersonorg',
	height : '60%',
	width : '20%',
	
	requires : ['RODAdmin.util.Util'],

	layout : {
		type : 'border'
	},

    config: {
        cnode: {}
    },


     initComponent : function() {
		var me = this;
		Ext.applyIf(me, {

			items : [{
				region : 'center',
				collapsible : false,
//				width : '50%',
				flex:3,
				split : true,
				layout : 'fit',
				items : [{
					xtype : 'tabpanel',
					itemId: 'personorg',
					bodyPadding : 5,
					layout : {
						type : 'fit',
					},
					items:[
					       {
					    	   title : 'Person',
					    	   items: [
					    	           {
					    	        		xtype : 'form',
					    					itemId: 'estudyform',
					    					bodyPadding : 5,
					    					layout : {
					    						type : 'fit',
					    					},
					    					items: [
					    					        {
					    					        	xtype: 'fieldset',
					    					        	title: 'Select an existing person',
					    					        	items: [
					    					        	        {
						                                                xtype : 'combo',
						                                                labelWidth : 60,
						                                                name : 'experson',
						                                                fieldLabel : 'Person',
//						                                                anchor : '100%',
	//					                                                displayField: 'name',
	//					                                                typeAhead: true,
	//					                                                valueField: 'indice',
	//					                                                tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
	//					                                                store : 'cms.layout.Layout'
					    					        	        }
					    					        	        ]
					    					        	
					    					        },
					    					        {
					    					        	xtype: 'fieldset',
					    					        	title: 'Or, add a new person',
					    					        	items: [
					    					        	        {
					                                                xtype : 'combo',
					                                                labelWidth : 60,
					                                                name : 'prefix',
					                                                fieldLabel : 'Prefix',
//					                                                anchor : '100%',
//					                                                displayField: 'name',
//					                                                typeAhead: true,
//					                                                valueField: 'indice',
//					                                                tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
//					                                                store : 'cms.layout.Layout'
					    					        	        },
					    					        	        {
					    					        	        	xtype: 'textfield',
					    					        	        	name: 'fname',
					    					        	        	fieldLabel: 'First Name'
					    					        	        },
					    					        	        {
					    					        	        	xtype: 'textfield',
					    					        	        	name: 'lname',
					    					        	        	fieldLabel: 'Last Name'
					    					        	        },
					    					        	        {
					                                                xtype : 'combo',
					                                                labelWidth : 80,
					                                                name : 'suffix',
					                                                fieldLabel : 'Suffix',
//					                                                anchor : '100%',
//					                                                displayField: 'name',
//					                                                typeAhead: true,
//					                                                valueField: 'indice',
//					                                                tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
//					                                                store : 'cms.layout.Layout'
					    					        	        },
					    					        	        {
					    					        	        	xtype: 'textfield',
				    					        	        		labelWidth : 80,
					    					        	        	name: 'email',
					    					        	        	fieldLabel: 'Email'
					    					        	        },
					    					        	        {
					    					        	        	xtype: 'textfield',
				    					        	        		labelWidth : 80,
					    					        	        	name: 'phone',
					    					        	        	fieldLabel: 'Phone'
					    					        	        },
					    					        	        {
					    					        	        	xtype: 'fieldset',
					    					        	        	title: 'Address',
					    					        	        	items: [
					    					        	        	{
					    					        	        		xtype: 'textfield',
					    					        	        		labelWidth : 80,
					    					        	        		name: 'address1',
					    					        	        		fieldLabel: 'Address1'
					    					        	        	},
					    					        	        	{
					    					        	        		xtype: 'textfield',
					    					        	        		labelWidth : 80,
					    					        	        		name: 'address2',
					    					        	        		fieldLabel: 'Address2'
					    					        	        	},
					    					        	        	{
					    					        	        		xtype: 'textfield',
					    					        	        		labelWidth : 80,
					    					        	        		name: 'Postal Code',
					    					        	        		fieldLabel: 'Postal Code'
					    					        	        	},{
					    					        	        		xtype : 'combo',
					    					        	        		labelWidth : 80,
					    					        	        		name : 'city',
					    					        	        		fieldLabel : 'City',
////					                                                	anchor : '100%',
////					                                                	displayField: 'name',
////					                                                	typeAhead: true,
////					                                                	valueField: 'indice',
////					                                                	tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
////					                                                	store : 'cms.layout.Layout'
					    					        	        	}
																	]
					    					        	        }
					    					        ]
					    					        }]
					    	           }]
					       },{
					    	   title: 'Organization',
					    	   items: [
					    	           {
					    	        		xtype : 'form',
					    					itemId: 'estudyform',
					    					bodyPadding : 5,
					    					layout : {
					    						type : 'fit',
					    					},
					    					items: [
					    					        {
					    					        	xtype: 'fieldset',
					    					        	title: 'Select an existing person',
					    					        	items: [
					    					        	        {
					                                                xtype : 'combo',
					                                                labelWidth : 60,
					                                                name : 'experson',
					                                                fieldLabel : 'Person',
//					                                                anchor : '100%',
//					                                                displayField: 'name',
//					                                                typeAhead: true,
//					                                                valueField: 'indice',
//					                                                tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
//					                                                store : 'cms.layout.Layout'
					    					        	        	
					    					        	        }]
					    					        }, {
					    					        	xtype: 'fieldset',
					    					        	title: 'Select an existing person',
					    					        	items: [
					    					        	        {
					                                                xtype : 'combo',
					                                                labelWidth : 60,
					                                                name : 'orgprefix',
					                                                fieldLabel : 'OrgPrefix',
//					                                                anchor : '100%',
//					                                                displayField: 'name',
//					                                                typeAhead: true,
//					                                                valueField: 'indice',
//					                                                tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
//					                                                store : 'cms.layout.Layout'
					    					        	        },
					    					        	        {
					    					        	        	xtype: 'textfield',
					    					        	        	name: 'shortname',
					    					        	        	fieldLabel: 'Short Name'
					    					        	        },
					    					        	        {
					    					        	        	xtype: 'textfield',
					    					        	        	name: 'fullname',
					    					        	        	fieldLabel: 'Full Name'
					    					        	        },
					    					        	        {
					                                                xtype : 'combo',
					                                                labelWidth : 80,
					                                                name : 'suffix',
					                                                fieldLabel : 'Suffix',
//					                                                anchor : '100%',
//					                                                displayField: 'name',
//					                                                typeAhead: true,
//					                                                valueField: 'indice',
//					                                                tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
//					                                                store : 'cms.layout.Layout'
					    					        	        }
					    					        	        ]
					    					        },
					    					        {
			    					        	        	xtype: 'fieldset',
			    					        	        	title: 'Address',
			    					        	        	items: [
			    					        	        	{
			    					        	        		xtype: 'textfield',
			    					        	        		labelWidth : 80,
			    					        	        		name: 'address1',
			    					        	        		fieldLabel: 'Address1'
			    					        	        	},
			    					        	        	{
			    					        	        		xtype: 'textfield',
			    					        	        		labelWidth : 80,
			    					        	        		name: 'address2',
			    					        	        		fieldLabel: 'Address2'
			    					        	        	},
			    					        	        	{
			    					        	        		xtype: 'textfield',
			    					        	        		labelWidth : 80,
			    					        	        		name: 'Postal Code',
			    					        	        		fieldLabel: 'Postal Code'
			    					        	        	},{
			    					        	        		xtype : 'combo',
			    					        	        		labelWidth : 80,
			    					        	        		name : 'city',
			    					        	        		fieldLabel : 'City',
////			                                                	anchor : '100%',
////			                                                	displayField: 'name',
////			                                                	typeAhead: true,
////			                                                	valueField: 'indice',
////			                                                	tpl: '<tpl for="."><div class="x-boundlist-item">{directory}/<strong>{name}</strong><br><i>{description}</i><hr></div></tpl>',
////			                                                	store : 'cms.layout.Layout'
			    					        	        	}
															]
			    					        	        }
					    					        	
					    					        
					    					        
					    					        
					    					        
					    					        
					    					        	
					    					        	

					    					        
					    					        
					    					        
					    					        
					    					        
					    					        
					    					        
					    					        
					    					        
					    					        ]
					    					}]
					       }]
				}]
			}]
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});