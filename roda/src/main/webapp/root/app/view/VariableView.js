Ext.define('databrowser.view.VariableView', {
	extend : 'Ext.window.Window',
	modal : true,
	 minWidth : 600,
	 minHeight : 400,
	//width : 600,
	//height : 600,
	 constrain : false,
	 autoscroll : true,
	// store: 'VariableStore',
	loaddata : function(id) {
		// asta e o varianta idioata determinata oarecum de incapacitatea
		// autorilor extjs de a explica cum se fac rahaturile simple
		var freqchart = Ext.getCmp('freqchart');
		var vStore = Ext.StoreManager.get('VariableStore');
		vStore.load({
			id : id, // set the id here
			scope : this,
			callback : function(records, operation, success) {
				if (success) {
					var rec = vStore.first();
					if (rec.get('nrfreq') == 0) {
						freqchart.getEl().hide();
					} else {
					freqchart.bindStore(rec.otherStatisticsStore);
					}
				}
			}
		});
	},

	layout : {
		type : 'fit'
	},
	alias : 'widget.variableview',
	id : 'variableview',
	itemId : 'variableview',
	title : 'Variable view',
	items : [ {
		xtype : 'variabledetails',
		id : 'variabledetails'
	} ],

	dockedItems : [ {
		xtype : 'toolbar',
		flex : 1,
		dock : 'bottom',
		ui : 'footer',
		layout : {
			pack : 'end',
			type : 'hbox',
		},

		items : [ {
			xtype : 'button',
			text : 'Anterior',
			itemId : 'previousvar',
			iconCls : 'previous-button'
		}, {
			xtype : 'button',
			text : 'Next',
			itemId : 'nextvar',
			iconCls : 'next-button'
		}, {
			xtype : 'button',
			text : 'Close',
			itemId : 'close',
			iconCls : 'cancel'
		}

		]
	} ],

 initComponent: function() {
 var me = this;
 me.callParent(arguments);
 }

});