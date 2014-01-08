/**
 * 
 */
Ext.define('RODAdmin.view.common.WindowForm', {
			extend : 'Ext.window.Window',
			alias : 'widget.windowform',

			requires : ['RODAdmin.view.toolbar.CancelSave'],

			height : 400,
			width : 550,
			autoscroll : true,
			layout : {
				type : 'fit'
			},
			modal : true,

			// items must be overriden in subclass

			dockedItems : [{
						xtype : 'cancelsave'
					}]

		});