Ext.define('databrowser.controller.VariableView', {
	extend : 'Ext.app.Controller',
	views : [ 'VariableView' ],
	init : function(application) {
		this.control({
			"variableview button#close" : {
				click : this.onButtonClickClose
			},
			"variableview button#previousvar" : {
				click : this.onButtonClickPrevious
			},
			"variableview button#nextvar" : {
				click : this.onButtonClickNext
			},
		});

	},
	onButtonClickClose : function(button, e, options) {
		button.up('variableview').close();
	},
	onButtonClickPrevious : function(button, e, options) {
		Ext.Msg.alert('Previous Clicked', 'Not yet here... :)');
	},
	onButtonClickNext : function(button, e, options) {
		Ext.Msg.alert('Next Clicked', 'Not yet here... :)');
	},

});
