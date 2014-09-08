Ext.define('databrowser.view.StackedBarChart', {
	extend : 'Ext.chart.Chart',
	alias : 'widget.sbchart',
	animate: true,
	shadow:true,
    legend: {
        position: 'right'
    },
	initComponent : function() {
	    var me = this;
	    Ext.applyIf(me, {
	    	insetPadding : 60,
	    	//		store: 'VariableStore',
	    	theme : 'Base:gradients',

	    	axes: [
	    	       {
	    	    	   type : 'Numeric',
	    	    	   position: 'bottom',
	    	    	   fields: me.datafields,
	    	    	   label: {
	    	    		   renderer: Ext.util.Format.numberRenderer(0,0)
	    	   			},
	    	   			title: false,
	    	   			grid: true,
	    	   			minimum: 0,
	    	       },{
	    	    	   type: 'Category',
	    	    	   position: 'left',
	    	    	   fields: me.catfield,
	    	    	   title: me.catfield,
	    	       }],
	    	       series: [{
	    	    	   type : 'bar',
	    	    	   axis: 'left',
	    	    	   highlight: true,
	    	    	   stacked: true,
	    	    	   tips: {
	    	    		   trackMouse: true,
	    	    		   renderer: function(storeItem, item) {
	    	    			   this.setWidth(100);
	    	    			   this.setHeight(50);
	    	    			   var label = storeItem.data.name;
	    	    			   console.log('label: ' + label);
	    	    			   console.log(storeItem.data[me.datafields[0]]);
	    	    			   
	    	    			   if (item.value[1] == storeItem.data[me.datafields[0]]) {
	    	    				   console.log('first');
	    	    				   label = label + '<br />' + me.datafields[0];
	    	    				   console.log(label);
	    	    			   }
	    	    			   else if (item.value[1] == storeItem.data[me.datafields[1]]) {
	    	    				   console.log('last');
	    	    				   label = label + '<br />' + me.datafields[1];
	    	    				   console.log(label);
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