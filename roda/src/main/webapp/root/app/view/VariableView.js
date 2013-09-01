Ext.define('databrowser.view.VariableView', {
	extend: 'Ext.window.Window',
	modal : true,
	minWidth : 600,
	minHeight : 400,
//	width: 600,
//	height: 600,
	constrain : false,
	autoscroll : true,
	store: 'VariableStore',
    loaddata: function(id){

    	//asta e o varianta idioata determinata oarecum de incapacitatea autorilor extjs de a explica cum se fac rahaturile simple    	
    	console.log('did I do smth?');
    	var freqchart = Ext.getCmp('freqchart');
    	var vStore = Ext.StoreManager.get('VariableStore');    	
//    	console.log(sStore);
    	vStore.load({
    		  id: id, //set the id here
    		  scope:this,
    		  callback: function(records, operation, success){
    			console.log(success);  
    		    if(success){
    		    	console.log('variable store loaded?');
    		    	var rec = vStore.first();
    		    	
      		    	console.log(rec.get('name'));
      		    	console.log(rec.get('id'));
      		    	console.log(rec.get('description'));
      		   		freqchart.updateTitle(rec.get('name'));
      		    	freqchart.bindStore(rec.frequenciesStore);
    		    }
    		  }
    		});
    },	
	
	
	layout : {
		type: 'fit'
	},
    alias: 'widget.variableview',
    id: 'variableview',
    itemId: 'variableview',   
    title: 'Variable view',
    	items : [
    	         {
            xtype: 'variabledetails',
            id: 'variabledetails'
    }],
    
    dockedItems : [{
    	xtype: 'toolbar',
    	flex: 1,
    	dock: 'bottom',
    	ui: 'footer',
    	layout : {
    		pack: 'end',
    		type: 'hbox',
    	},
    	items : [
    	         {
    	        	 xtype : 'button',
    	        	 text : 'Close',
    	        	 itemId : 'close',
    	        	 iconCls : 'cancel'
    	         }
    	         ]
    }]
    
//    initComponent: function() {
//    	var me = this;
//    	me.callParent(arguments);
//    }	
    
});