/**
 * 
 */
Ext.define('RODAdmin.view.cron.action.EditCronActionWindow', {
	             
	extend : 'RODAdmin.view.common.WindowForm',
	alias : 'widget.cronactionedit',

//	height : '90%',
//	width : '60%',

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
				split : true,
				layout : 'fit',
					items : [{
								xtype : 'fieldset',
								title : 'Edit action ',
								defaults : {
									afterLabelTextTpl : RODAdmin.util.Util.required,
									anchor : '100%',
									xtype : 'textfield',
									allowBlank : false,
									layout : {
										type : 'vbox',
									},									
									labelWidth : 60
								},
								items : [
								        {
											xtype: 'textfield',
											fieldLabel : 'Name',
											name : 'name',
											itemId: 'layoutname',
											value : ''
										},	{
									        xtype: 'textareafield',
        									fieldLabel: 'Description',
        									itemId: 'description',
        									name: 'description',
											colspan: 2,
											fieldLabel: 'Description',
        									value: ''
										},
								        {
											xtype: 'textfield',
											fieldLabel : 'Cron',
											name : 'cron',
											itemId: 'cron',
											value : ''
										}, {
									        xtype: 'combo',
									        fieldLabel: 'Class',
        									itemId: 'classname',
        									name: 'classname',
											// colspan: 2,
        									flex:1,
        									value: '',
       										 store : [
                                                'ro.roda.scheduler.tasks.Vacuum', 'ro.roda.scheduler.tasks.Backup', 'ro.roda.scheduler.tasks.CleanPagePreviews'
	                                                ]
										},	{
											xtype : 'combo',
											fieldLabel : 'Enabled',
											name : 'enabled',
											value : '',
											itemId : 'enabled',
											store:['Yes','No'],
										},	{
											xtype : 'hiddenfield',
											fieldLabel : 'Label',
											name : 'id',
											value : '',
											itemId : 'id'
										}	
										]
							}
					]
// }]
// end regions
			}]
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});