Ext.define('databrowser.View.toolbar.Cancel', {
	extend : 'Ext.toolbar.Toolbar',
	alias : 'widget.cancel',
	flex: 1,
	dock: 'bottom',
	ui: 'footer',
	layout : {
		pack: 'end',
		type: 'hbox',
	},
	items : [
	         {
	        	 xtype : 'button',
	        	 text : 'Close',
	        	 itemId : 'close',
	        	 iconCls : 'cancel'
	         }
	         ]
});