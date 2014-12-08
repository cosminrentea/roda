
Ext.define('anax.view.AnaxMap', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.anaxmap',
    autoScroll: true,
	itemid: 'anaxmap',
    width: '100%',
    layout: {
        type: 'fit',
    },
	
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {  
        	items: [{
        	        xtype: 'anaxdrawing',
        	        	itemId: 'maindraw'
        			}
        	        ],
        	 dockedItems : [
        	                {
        	                	xtype: 'toolbar',
        	                	itemId: 'anmtoolbar',
        	                	dock : 'top',
        	                	items : [
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'zin',
        	                	        	 text: 'Zoom in',
        	                	        	 icon: 'icon/zoomin.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'zout',
        	                	        	 text: 'Zoom out',
        	                	        	 icon: 'icon/zoomout.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pleft',
        	                	        	 text: 'Pan left',
        	                	        	 icon: 'icon/panleft.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pright',
        	                	        	 text: 'Pan right',
        	                	        	 icon: 'icon/panright.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pup',
        	                	        	 text: 'Pan up',
        	                	        	 icon: 'icon/panup.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pdown',
        	                	        	 text: 'Pan down',
        	                	        	 icon: 'icon/pandown.png'
        	                	         },        	                	         
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'aglevel',
        	                	        	 text: 'Level',
											hidden: true
      	                	        	 },           	                	         

        	                	         
        	                	         ]
        	                	
        	                }
        	                ]       
});
        me.callParent(arguments);
    },
    
});
