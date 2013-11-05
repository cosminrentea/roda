Ext.define('RODAdmin.view.cms.snippet.details.SnippetProperties', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.snippetproperties',
    itemId: 'snippetproperties',
    title:'Snippet Properties',
//    id: 'fileproperties',
	collapsible: true,
			dockedItems : [
		{
            xtype: 'toolbar',
            itemid: 'snproptoolbar',
            id : 'snproptoolbar',
			dock : 'bottom',
			items : [
			{xtype: 'tbfill'},
					{
						xtype: 'button',
//						id: 'editfile',
						itemId: 'editsnippet',
						text : 'Edit',
						tooltip : 'Edit this snippet'
					},
					{
						xtype: 'button',
//						id: 'deletefile',
						itemId: 'deletesnippet',
						text : 'Delete',
						tooltip : 'Deletes the snippet'
					},
					{
						xtype: 'button',
						itemId: 'getsnippetaudit',
						text : 'Snippet History',
						tooltip : 'Get Snippet History'
					}]
		}],
	
    tpl : ['<tpl if="data.filetype == \'folder\'">',
					'<h1> {data.name}',
					'</tpl>',
					'<tpl if="data.filetype != \'folder\'">',
					'<H1>{data.name}</H1>',
					'<H2>{data.directory}</H2>',
					'<div style="padding:10px;">',
					'{data.description}',
					'</div>',
					'</tpl>'
	]
});

