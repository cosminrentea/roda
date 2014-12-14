Ext.define('RODAdmin.view.studies.CBEditor.ConvertConfirm', {
	extend : 'Ext.window.Window',
	alias : 'widget.convertconfirm',
	height : '80%',
	width : '40%',
	closeAction: 'destroy',
	layout: 'fit',
	itemId:'convertconfirm',
	requires : ['RODAdmin.util.Util'],
	dockedItems : [{
		xtype : 'toolbar',
	    flex: 1,
	    dock: 'bottom',
	    ui: 'footer',
	    layout: {
	        pack: 'end',
	        type: 'hbox'
	    },
	    items: [
	        {
	            xtype: 'button',
	            text: 'Cancel',
	            itemId: 'cancel',
	            iconCls: 'cancel'
	        },
	        {
	            xtype: 'button',
	            text: 'Convert',
	            itemId: 'convert',
	            iconCls: 'convert'
	        }
        ]
			
	}],

	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {

			items : [{
				xtype: 'panel',
				bodyPadding: 20,
				tpl: ['checking study file...',
				      '<H1>{data.studyProposal.studytitle}</H1>',
				     '<tpl if="verdict == 0">Study can be imported</tpl><tpl if = "verdict != 0">Study cannot be imported</tpl>',
					 '<tpl for="errors">',
				     '<tpl if="type == \'fatal\'">',
					 '<div style="width: 100%; height: 50px; background-color:#f0ece0; margin-bottom: 10px; padding: 10px; font-size: 16px; color: #F00;">',
					 '</tpl>',
					 '<tpl if="type == \'serious\'">',
					 '<div style="width: 100%; height: 50px; background-color:#f0ece0; margin-bottom: 10px; padding: 10px; font-size: 16px; color: #FF0;">',
					 '</tpl>',
					 '<tpl if="type == \'info\'">',
					 '<div style="width: 100%; height: 50px; background-color:#f0ece0; margin-bottom: 10px; padding: 10px; font-size: 16px; color: #F0F;">',
					 '</tpl>',
					 '{message}',	
					 '</div>',
					 '</tpl>'],
				itemId : 'convertstatus',
//				html: 'come on'
				
			}]
		});
		me.callParent(arguments);
		me.initConfig(arguments)
	}
});