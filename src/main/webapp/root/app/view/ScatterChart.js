Ext.define('databrowser.view.ScatterChart', {
	extend : 'Ext.chart.Chart',
	alias : 'widget.scatter',
	animate: true,
	shadow:true,
	theme : 'Base:gradients',
	
	initComponent : function() {
	    var me = this;
	    
	    console.log('xaxistitle' + me.xaxistitle);
	    console.log('yaxistitle' + me.yaxistitle);
	    console.log('xfields' + me.xfields);	    
	    console.log('yfields' + me.yfields);	    
	    
	    
	    Ext.applyIf(me, {	
	    	axes: [{
	    		type: 'Numeric',
	    		position: 'left',
	    		fields: [me.yfields],
	    		title: me.xaxistitle,
	    		grid: true,
	    		minimum: 0
	    	}, {
	    		type: 'Numeric',
	    		position: 'bottom',
	    		fields: [me.xfields],
	    		title: me.yaxistitle
	    	}],
	    	series: [{
	    		type: 'scatter',
	    		markerConfig: {
	    			type: 'circle',
	    			size: 5,
	    			radius: 5
	    		},
	    		axis: 'left',
	    		xField: me.xfields,
	    		yField: me.yfields,
	    	}, 
	    	],
	    });
	    me.callParent(arguments);
	    me.initConfig(arguments)
    }
});