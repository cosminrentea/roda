Ext.define('databrowser.view.StackedBarChart', {
	extend : 'Ext.chart.Chart',
	alias : 'widget.sbchart',
	animate: true,
	shadow:true,

	initComponent : function() {
	    var me = this;
	    Ext.applyIf(me, {
	    	insetPadding : 60,
	    	//		store: 'VariableStore',
	    	theme : 'Base:gradients',
            legend: {
                position: 'right'
            },
	    	axes: [
	    	       {
	    	    	   type : 'Numeric',
	    	    	   position: 'left',
	    	    	   fields: me.datafields,
	    	    	   label: {
	    	    		   renderer: Ext.util.Format.numberRenderer(0,0)
	    	   			},
	    	   			title: false,
	    	   			grid: true,
	    	   			minimum: 0,
	    	       },{
	    	    	   type: 'Category',
	    	    	   position: 'bottom',
	    	    	   fields: me.catfield,
	    	    	   title: me.catfield,
	    	       }],
	    	       series: [{
	    	    	   type : 'column',
	    	    	   axis: 'left',
	    	    	   highlight: true,
	    	    	   stacked: true,
	    	    	   tips: {
	    	    		   trackMouse: true,
	    	    		   renderer: function(storeItem, item) {
	    	    			   this.setWidth(100);
	    	    			   this.setHeight(50);
	    	    			   var label = storeItem.data.name;
	    	    			   console.log(storeItem.data[me.datafields[0]]);
	    	    			   if (item.value[1] == storeItem.data[me.datafields[0]]) {
	    	    				   label = label + '<br />' + me.datafields[0];
	    	    			   }
	    	    			   if (item.value[1] == storeItem.data[me.datafields[1]]) {
	    	    				   label = label + '<br />' + me.datafields[1];
	    	    			   }
	    	    			   this.setTitle(label + '<br />' + item.value[1]);
	    	    		   }
	    	    	   },
	    	    	   xField: me.catfield,
	    	    	   yField: me.datafields,
	    	       }],
	 
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});