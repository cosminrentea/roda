Ext.define('databrowser.controller.YearView', {
	extend : 'Ext.app.Controller',
	views : [ 'YearView' ],
	
	 refs: [
	        {
	            ref: 'yearview',
	            selector: 'yearview'
	        },
	        ],
	
	init : function(application) {
		this.control({
			"yearview button#YSButton" : {
				 click : this.onYSButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"yearview button#YMButton" : {
				 click : this.onYMButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
			"yearview button#YCButton" : {
				 click : this.onYCButtonClick
				// selectionchange: this.onMenuGetRDetails
			},
		});

	},

	onYSButtonClick : function(button, e, eOpts) {
		console.log('sbutton controller click');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().setCurrentview('simple');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().getView().refresh();
	},

	onYCButtonClick : function(button, e, eOpts) {
		console.log('cbutton controller click');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().setCurrentview('complex');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().getView().refresh();

	},

	onYMButtonClick : function(button, e, eOpts) {
		console.log('mbutton controller click');
		//sa vedem ce facem. Intai sa vedem daca putem sa luam configul
		console.log(this.getYearview().getCurrentview());
		this.getYearview().setCurrentview('medium');
		console.log(this.getYearview().getCurrentview());
		this.getYearview().getView().refresh();

	},


});