Ext.define("databrowser.util.ChartTitleMixin", {
	createTitleItem : function() {
		this.chartTitle = new Ext.draw.Sprite({
			type : "text",
			"text-anchor" : "middle",
			fill : "black",
			"font-size" : "12px",
			"font-weight" : "bold",
			"font-family" : "Arial",
			text : this.title
		});
		this.on("resize", function(cmp, width) {
			this.chartTitle.setAttributes({
				translate : {
					x : (width / 2) - 80,
					y : 10
				}
			}, true);
		});
		this.items = this.items || [];
		this.items.push(this.chartTitle);
	},
	updateTitle : function(newTitle) {
		this.chartTitle.setAttributes({
			text : newTitle
		}, true);
	},
	initComponent : function() {
		this.createTitleItem();
	}
});