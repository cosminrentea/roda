/**
 * 
 */
Ext.define('RODAdmin.view.common.InformationWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.informationwindow',

			requires : ['RODAdmin.view.toolbar.CloseTb'],

			height : '60%',
			width : '50%',
			autoscroll : true,
			layout : {
				type : 'fit'
			},
			modal : true,

			// items must be overriden in subclass

			dockedItems : [{
						xtype : 'closetb'
					}]

		});