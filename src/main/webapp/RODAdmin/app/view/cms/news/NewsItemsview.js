/**
 * 
 */
Ext.define('RODAdmin.view.cms.news.NewsItemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.newsitemsview',
			itemId : 'newsitemsview',
			width : '100%',
			layout:'fit',
			items : [{
						xtype : 'grid',
						itemId : 'newsiconview',
						store : 'cms.news.News',
						columns : [
						           {	
						           text : 'id',
						           flex : 1,
						           dataIndex : 'id',
						           sortable : true,
						           filter : {
						           	type : 'integer'
						           }
						           },
						           {
									text : 'Title',
									flex : 1,
									sortable : true,
									dataIndex : 'title',
									filterable : true
								}, {
									text : 'Date',
									flex : 1,
									dataIndex : 'added',
									sortable : true,
									filterable : true
								}, {
									text : 'Language',
									flex : 1,
									xtype: 'templatecolumn',
									tpl: '<div class="lang_{langCode}">&nbsp;</div>',
									sortable : true,
//									filterable : true
								}
								],
						dockedItems : [{
									xtype : 'toolbar',
									itemId : 'newsiconviewtoolbar',
									dock : 'bottom',
									items : [{
												xtype : 'tbfill'
											},
											{
												xtype : 'button',
												itemId : 'reload',
												text : 'Refresh Grid',
												tooltip : 'Refresh grid '
											},
											{
												xtype : 'button',
												itemId : 'addnews',
												text : 'Add news Item',
												tooltip : 'Add a news item'
											},
											{
												xtype : 'button',
												itemId : 'showfilterdata',
												text : 'All Filter Data',
												tooltip : 'Get Filter Data for Grid'
											}, {
												text : 'Clear Filter Data',
												xtype : 'button',
												itemId : 'clearfilterdata'
											}]
								}]
					}]
			});
