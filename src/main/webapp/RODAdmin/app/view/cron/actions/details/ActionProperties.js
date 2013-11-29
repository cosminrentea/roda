Ext.define('RODAdmin.view.cron.actions.details.ActionProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.actionproperties',
    itemId : 'actionproperties',
    title : 'Action Properties',
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
