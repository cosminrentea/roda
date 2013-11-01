Ext.define('RODAdmin.view.cms.Files', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cmsfiles',
	itemId: 'cmsfiles',
	id : 'cmsfiles',
//    store: 'staticData.Categories',
	   layout: {
            type: 'border',
            padding: 5
        },
        defaults: {
            split: true
        },
    
    items: [{
            region: 'center',
            collapsible: false,
  		    width:'50%',
            split: true,
            layout:'fit',
//            html: 'yuppie',
			dockedItems: [{
                    dock: 'top',
                    xtype: 'toolbar',
                    itemid: 'filestoolbar',
                    items: [
                    {
										xtype : 'textfield',
										itemId : 'filtername',
										width : 200
					},                    	
					{xtype: 'tbfill'},					
					{xtype: 'tbseparator'},
                    {
										xtype : 'button',
										itemId : 'tree-view',
										iconCls : 'file-tree-view',
										text: 'tree',
										//enableToggle: true
					},                    	
                    {
										xtype : 'button',
										itemId : 'icon-view',
										iconCls : 'file-icon-view',
										text: 'icon',
										//enableToggle: true
					}                    	
                    		
]
                }],
            items : [
            {
            xtype: 'itemsview'
            	
            	
            }
            ]
            },
            {
            	region: 'east',
            	collapsible: true,
				width:'50%',
				xtype:'panel',
				itemid : 'fdetailscontainer',
				id : 'fdetailscontainer',
            	title : 'dontinkso',
  				layout: {
                         type:'fit',
//                         padding:'5',
//                         align:'center',
//                         align:'stretch'
                },      
            	items: [
            	{
            	xtype: 'filedetails'
            	}
            	]
            }
	]	
  
});