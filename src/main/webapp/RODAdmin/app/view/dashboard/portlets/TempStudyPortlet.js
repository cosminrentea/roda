Ext.define('RODAdmin.view.dashboard.portlets.TempStudyPortlet', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.tempstudyportlet',
    
    height: 300,

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */

    initComponent: function(){

        var store = Ext.create('RODAdmin.store.dashboard.TempStudies');

        Ext.apply(this, {
            height: this.height,
            store: store,
            stripeRows: true,
            columnLines: true,
			columns : [
			           	{
		           		text : 'title',
		           		width : 25,
		           		dataIndex : 'title',
		           		sortable : true,
		           		flex: 2,
			           	},
			           {
						itemId : 'startedby',
						text : 'Started by',
						flex : 1,
						sortable : true,
						dataIndex : 'startedby',
						filterable : true
			           },
			           {
						itemId : 'started',
						text : 'Started',
						flex : 1,
						sortable : true,
						dataIndex : 'started',
						filterable : true
			           }
			           	],
        });

        this.callParent(arguments);
    }
});
