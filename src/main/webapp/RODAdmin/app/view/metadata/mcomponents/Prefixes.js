Ext.define('RODAdmin.view.metadata.mcomponents.Prefixes', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.mcprefixes',
    
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

        var store = Ext.create('RODAdmin.store.metadata.Prefixes');
        console.log(store);
        store.load();
        Ext.apply(this, {
            height: this.height,
            store: store,
            stripeRows: true,
            dockedItems : [
       	                {
       	                    dock : 'top',
       	                    xtype : 'toolbar',
       	                    itemId : 'mtoolbar',
       	                    items : [
       	                             {
       	                            	xtype : 'button',
       	                                itemId : 'add',
       	                                text : 'Add'
       	                            }
       	                    ]
       	                }
                       ],
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
						text : 'Name',
						flex : 1,
						sortable : true,
						dataIndex : 'name',
						filterable : true
					},
						{
							xtype : 'actioncolumn',
							width : 60,
							sortable : false,
							menuDisabled : true,
							itemId : 'actionquestionsdisplay',
							items : [{
								icon : '/roda/admin/images/cog_edit.png',
								tooltip : 'Edit Question',
								scope : this,
								handler : function(view, rowIndex,
										colIndex, item, e, record, row) {
									console.log('firing event edit');
									var mygrid = view.up('grid');
									mygrid.fireEvent('editRecord',
											mygrid, record, rowIndex,
											row);
								}
							}, {
								icon : '/roda/admin/images/delete.gif',
								tooltip : 'Delete Question',
								scope : this,
								handler : function(view, rowIndex,
										colIndex, item, e, record, row) {
									console.log('firing event delete');
									var mygrid = view.up('grid');
									mygrid.fireEvent('deleteRecord',
											mygrid, record, rowIndex,
											row);
								}
							}]
						}]
        });

        this.callParent(arguments);
    }
});
