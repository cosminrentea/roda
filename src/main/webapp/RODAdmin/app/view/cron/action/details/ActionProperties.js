Ext.define('RODAdmin.view.cron.action.details.ActionProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.actionproperties',
    itemId : 'actionproperties',
    title : 'Action Properties',
    collapsible : true,


    tpl : [
	    '<h1 >{data.id} - {data.name}</h1>',
	    '<div class="lang_{data.lang}"></div>',
	    'Class: {data.classname}',
	    '<div class="pagesynopsis">{data.description}</div>',
	    'Cron: {data.cron}</div>',
	    'Next execution: {data.timestamp_next_execution}<br>',
	    'Last execution: {data.timestamp_last_execution}<br>',
	    'Enabled: {data.enabled}<br>'
	    ]
});
