Ext.define('databrowser.controller.CatalogView', {
	extend : 'Ext.app.Controller',
	views : [ 'CatalogView' ],
	
	 refs: [
	        {
	            ref: 'catalogview',
	            selector: 'catalogview'
	        },
	        ],
	
	init : function(application) {
		this.control({
			"catalogview button#SButton" : {
				 click : this.onSButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"catalogview button#MButton" : {
				 click : this.onMButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"catalogview button#CButton" : {
				 click : this.onCButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
		});

	},

	onSButtonClick : function(button, e, eOpts) {
		console.log('sbutton controller click');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().setCurrentview('simple');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().getView().refresh();
	},

	onCButtonClick : function(button, e, eOpts) {
		console.log('cbutton controller click');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().setCurrentview('complex');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().getView().refresh();

	},

	onMButtonClick : function(button, e, eOpts) {
		console.log('mbutton controller click');
		//sa vedem ce facem. Intai sa vedem daca putem sa luam configul
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().setCurrentview('medium');
		console.log(this.getCatalogview().getCurrentview());
		this.getCatalogview().getView().refresh();

	},


});