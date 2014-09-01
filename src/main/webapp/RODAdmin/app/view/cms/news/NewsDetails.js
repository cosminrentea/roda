/**
 * 
 */
Ext.define('RODAdmin.view.cms.news.NewsDetails', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.newsdetails',
	itemId: 'newsdetails',
	layout: {
		type:'fit',
		//                        padding:'5',
	}, 
	dockedItems : [
	               {
	            	   xtype: 'toolbar',
	            	   itemId: 'newsproptoolbar',
	            	   dock : 'bottom',
	            	   items : [
	            	            {xtype: 'tbfill'},
	            	            {
	            	            	xtype: 'button',
	            	            	itemId: 'editnews',
	            	            	text : 'Edit',
	            	            	tooltip : 'Edit this news item'
	            	            },
	            	            {
	            	            	xtype: 'button',
	            	            	itemId: 'deletenews',
	            	            	text : 'Delete',
	            	            	tooltip : 'Deletes the news item'
	            	            },
	            	            {
	            	            	xtype: 'button',
	            	            	itemId: 'getnewsaudit',
	            	            	text : 'News History',
	            	            	tooltip : 'Get News Item History'
	            	            }]
	               }],

	               items : [
	                        {
	                        	xtype : 'panel',
	                        	itemId : 'newsdata',
	                        	tpl : ['<div class="lang_{data.langCode}">&nbsp;</div><H1>{data.title}</H1>',
	                        	       '<div style="padding:10px;">',
	                        	       '{data.content}',
	                        	       '</div>',
	                        	       '<div style="padding:10px;">',
	                        	       'Added: {data.added}',
	                        	       '</div>'
	                        	       ]
	                        }]
});