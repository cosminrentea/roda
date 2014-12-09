
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
        	                	        	 icon: anax.util.Globals['contextPath'] + '/resources/RODAAnax/icon/zoomin.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'zout',
        	                	        	 text: 'Zoom out',
        	                	        	 icon: anax.util.Globals['contextPath'] + '/resources/RODAAnax/icon/zoomout.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pleft',
        	                	        	 text: 'Pan left',
        	                	        	 icon: anax.util.Globals['contextPath'] + '/resources/RODAAnax/icon/panleft.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pright',
        	                	        	 text: 'Pan right',
        	                	        	 icon: anax.util.Globals['contextPath'] + '/resources/RODAAnax/icon/panright.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pup',
        	                	        	 text: 'Pan up',
        	                	        	 icon: anax.util.Globals['contextPath'] + '/resources/RODAAnax/icon/panup.png'
        	                	         }, 
        	                	         {
        	                	        	 xtype: 'button',
        	                	        	 itemId : 'pdown',
        	                	        	 text: 'Pan down',
        	                	        	 icon: anax.util.Globals['contextPath'] + '/resources/RODAAnax/icon/pandown.png'
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
