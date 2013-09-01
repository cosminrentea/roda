Ext.define('databrowser.view.FrequencyChart', {
	extend : 'Ext.chart.Chart',
	alias : 'widget.freqchart',
	mixins: {
		chartTitle: "databrowser.util.ChartTitleMixin"
	},
	animate: true,
	shadow:true,
	insetPadding : 60,
	store: 'VariableStore',
	theme : 'Base:gradients',
	axes: [
	       {
	    	   type : 'Numeric',
	    	   position: 'bottom',
	    	   fields: ['count'],
	    	   label: {
	    		   renderer: Ext.util.Format.numberRenderer(0,0)
	    	   		},
	       		title: 'Cases',
	       		grid: true,
	       		minimum: 0
	       },{
	    	  type: 'Category',
	    	  position: 'left',
	    	  fields: ['name'],
	    	  title: 'Categorie'
	       }],
	 series: [{
	          type : 'bar',
	          axis: 'bottom',
	          highlight: true,
	          tips: {
	        	  trackMouse: true,
	        	  width: 140,
	        	  height: 28,
	        	  renderer: function(storeItem, item) {
	        		  this.setTitle(storeItem.get('name') + ': ' + storeItem.get('count'));
	        	  }
	          },
	          label: {
	        	  display: 'insideEnd',
	        	  'text-anchor': 'middle',
	        	  field: 'count',
	        	  renderer: Ext.util.Format.numberRenderer('0'),
	        	  orientation: 'horizontal',
	        	  color: '#333'
	          },
	          xField: 'name',
	          yField: 'count',

	 }],
	 
	 initComponent: function () {
		 this.mixins.chartTitle.initComponent.call(this);
		 this.callParent(arguments);
	}
});