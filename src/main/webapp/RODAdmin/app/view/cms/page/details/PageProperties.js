/**
 * 
 */
Ext.define('RODAdmin.view.cms.page.details.PageProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.pageproperties',
    itemId : 'pageproperties',
    title : translations.pg_properties,
    bodyPadding: 20,
    collapsible : true,


    tpl : [
	    '<h1 >{data.indice} - {data.title}</h1>',
	    '<div class="lang_{data.lang}"></div>',
	    'URL: {data.url}',
	    '<div class="pagesynopsis">{data.synopsis}</div>',
	    '<div class="pagecontent">{data.content}</div>',
    ]
});
