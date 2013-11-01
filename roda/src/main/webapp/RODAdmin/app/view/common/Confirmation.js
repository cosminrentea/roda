Ext.define('RODAdmin.view.common.Confirmation', {
			extend : 'Ext.window.Window',
			alias : 'widget.confirmation',

			requires : ['RODAdmin.view.toolbar.CancelSave'],

			height : 200,
			width : 250,
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