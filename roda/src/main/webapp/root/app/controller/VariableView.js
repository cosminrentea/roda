Ext.define('databrowser.controller.VariableView', {
	extend : 'Ext.app.Controller',
	views : [ 'VariableView' ],
	
	 refs: [
	        {
	            ref: 'studyVariables',
	            selector: 'studyvariables'
	        }
	    ],
	
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
		var grid = Ext.getCmp('studyvariables');
		var store = grid.getStore();
		console.log(store);
		var selectedRecord = grid.getSelectionModel().getSelection()[0];

//		console.log(selectedRecord.data.indice);
//		var row = grid.getView.store.indexOf(selectedRecord);
		var row = store.find('indice', selectedRecord.data.indice);
		if (row > 0) {	
			var vwin = Ext.getCmp('variableview');
			vwin.loadData(row-1);
		} else {
			Ext.Msg.alert('This is the first variable', 'vv');
		}
	},
	onButtonClickNext : function(button, e, options) {
		//var view = this.getVariableView();
		console.log(this.getStudyVariables().getSelectionModel().getSelection()[0]);
		Ext.Msg.alert('Next Clicked', 'Not yet here... :)');
	},

});
