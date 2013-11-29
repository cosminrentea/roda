Ext.define('RODAdmin.view.cms.page.details.PageProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.pageproperties',
    itemId : 'pageproperties',
    title : 'Page Properties',
    // id: 'fileproperties',
    collapsible : true,


    tpl : [
	    '<h1 >{data.id} - {data.title}</h1>',
	    '<div class="lang_{data.lang}"></div>',
	    'URL: {data.url}',
	    '<div class="pagesynopsis">{data.synopsis}</div>',
	    '<div class="pagecontent">{data.content}</div>',
    ]
});
