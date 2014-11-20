/**
 * 
 */
Ext.define('RODAdmin.view.cms.news.NewsItemsview', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.newsitemsview',
			itemId : 'newsitemsview',
			header: false,
			width : '100%',
			layout:'fit',
			items : [{
						xtype : 'grid',
						itemId : 'newsiconview',
						store : 'cms.news.News',
						header: false,
						columns : [
						           {	
						           text : translations.id,
						           width : 35,
						           dataIndex : 'id',
						           sortable : true,
						           filter : {
						           	type : 'integer'
						           }
						           },
						           {
									text : translations.title,
									flex : 3,
									sortable : true,
									dataIndex : 'title',
									filterable : true
								}, {
									text : translations.date,
									width : 150,
									dataIndex : 'added',
									sortable : true,
									filterable : true
								}, {
									text : translations.lang,
									width: 100,
									xtype: 'templatecolumn',
									tpl: '<div class="lang_{langCode}">&nbsp;</div>',
									sortable : true,
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
												text : translations.reloadgrid,
												tooltip : 'Refresh grid '
											},
											{
												xtype : 'button',
												itemId : 'addnews',
												text : translations.add,
												tooltip : 'Add a news item'
											},
											{
												xtype : 'button',
												itemId : 'showfilterdata',
												text : translations.allfilter,
												tooltip : 'Get Filter Data for Grid'
											}, {
												text : translations.clearfilter,
												xtype : 'button',
												itemId : 'clearfilterdata'
											}]
								}]
					}]
			});
