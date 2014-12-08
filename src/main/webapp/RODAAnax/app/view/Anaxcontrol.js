Ext.define('anax.view.Anaxcontrol', {
	extend: 'Ext.tab.Panel',
	alias: 'widget.anaxcontrol',
//	id: 'browser',
	itemId: 'anaxcontrol',
	activeItem: 0,
	width: '100%',
//	xtype: 'tabpanel',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
	        	items: [
	        	        {
	        	        	xtype: 'treepanel',
	        	        	autoScroll: true,
	        	        	title : 'Indicator',
	        	        	itemId: 'ItTreePanel',
	        	        	layout:'fit',
		       	        	store: 'ItStore',
	        	        	displayField: 'text',
//	        	        	title: translations.catalogs,
	        	        	viewConfig: {
	        	        		rootVisible: true
	        	        	}
	        	        }, 
	        	        {
	        	        	xtype: 'treepanel',
	        	        	autoScroll: true,
	        	        	title : 'Ani',
	        	        	itemId: 'YearsTreePanel',
	        	        	layout:'fit',
	        	        	store: 'YearStore',
	        	        	displayField: 'text',
//	        	        	title: translations.catalogs,
	        	        	viewConfig: {
	        	        		rootVisible: false
	        	        	}
	        	        }
	        ]
     });
       me.callParent(arguments);
    }
});

