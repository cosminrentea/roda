//@require @packageOverrides
Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'Ext.ux' : 'ux/',
		'Ext.ux.plugins' : 'ux/plugins',
	}
});

Ext.application({

	requires : [ 'Ext.grid.*', 'Ext.data.*', 'Ext.util.*',
			'Ext.toolbar.Paging', 'Ext.ModelManager',
			'Ext.ux.plugins.FitToParent' ],
	models : [ 
	           'ItemsModel',
	           'ExSprite',
	           'Sprite',
	           'Years',
	           'ItemsModel' 
	           ],
	stores : [ 
	           'ItStore', 
	           'YearStore', 
	           'Sprite', 
	           'Indicator'
	           ],
	views : [ 
	          'AnaxPanel',
	          'Anaxcontrol',
	          'AnaxMap',
	          'AnaxDrawing'
	          ],
	controllers : [
	               'IT',
	               'History' 
	               ],
	autoCreateViewport : false,
	
	
	name : 'anax',
	
	
	appFolder : 'app',
	init : function() {
		Ext.Ajax.timeout = 200000; // 200 seconds
		Ext.override(Ext.form.Basic, {
			timeout : Ext.Ajax.timeout / 1000
		});
		Ext.override(Ext.data.proxy.Server, {
			timeout : Ext.Ajax.timeout
		});
		Ext.override(Ext.data.Connection, {
			timeout : Ext.Ajax.timeout
		});
		Ext.create('anax.view.AnaxPanel', {
			renderTo : 'dbcontainer',
			plugins : [ 'fittoparent' ],
		})
	},

	launch : function() {
		console.log('launch application');
		Ext.tip.QuickTipManager.init();

		var me = this;
		Ext.util.History.init(function() {
			console.log('history init firing event');
			var hash = document.location.hash;
			me.getHistoryController().fireEvent('tokenchangeinit',
					hash.replace('#', ''));

		})
		Ext.util.History.on('change', function(token) {
			console.log('history change firing event');
			me.getHistoryController().fireEvent('tokenchange', token);
		});

	}

});
