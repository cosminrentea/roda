Ext.define('RODAdmin.view.dashboard.portlets.StudyPortlet', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.studyportlet',
    
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

        var store = Ext.create('RODAdmin.store.dashboard.LastStudies');

        Ext.apply(this, {
            height: this.height,
            store: store,
            stripeRows: true,
            columnLines: true,
			columns : [
			           	{
		           		text : 'id',
		           		width : 25,
		           		dataIndex : 'id',
		           		sortable : true,
		           		filter : {
		           			type : 'integer'
		           		}
			           	},
			           {
						itemId : 'ft',
						text : 'Study',
						flex : 1,
						sortable : true,
						dataIndex : 'name',
						filterable : true
					}],
        });

        this.callParent(arguments);
    }
});
