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
			}
		});

	},
	onButtonClickClose : function(button, e, options) {
		button.up('variableview').close();
	}

});
