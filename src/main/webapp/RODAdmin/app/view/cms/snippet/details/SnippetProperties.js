/**
 * 
 */
Ext.define('RODAdmin.view.cms.snippet.details.SnippetProperties', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.snippetproperties',
    itemId: 'snippetproperties',
    title:'Snippet Properties',
//    id: 'fileproperties',
    width : "100%",
    height : "100%",
    layout : 'border',
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
	
		  items : [
		            {
		                region : 'center',
		                collapsible : false,
		                width : '30%',
		                split : true,
		                xtype : 'panel',
		                itemId : 'sndata',
		                flex : 1,
		                id : 'sndata',
		                tpl : [
		                        '<tpl if="data.filetype == \'folder\'">', '<h1> {data.name}', '</tpl>',
		                        '<tpl if="data.filetype != \'folder\'">', '<H1>{data.name}</H1>', '<H2>{data.directory}</H2>',
		                        '<div style="padding:10px;">', '{data.description}', '</div>', '</tpl>'
		                ]
		            }, {
		                region : 'east',
		                collapsible : true,
		                resizable : true,
		                width : '70%',
		                split : true,
		                id : 'snenvelope',
//		                title : 'smth',
		                flex : 3,
		                layout : 'fit',
		                xtype : 'panel',
		                items : [
			                {
			                    xtype : 'codemirror',
			                    id : 'sncontent',
			                    mode : 'htmlmixed',
			                    readOnly : true,
			                    enableFixedGutter : true,
			                    listModes : '',
			                    showAutoIndent : false,
			                    showLineNumbers : false,
			                    enableGutter : true,
			                    showModes : false,
			                    pathModes : 'CodeMirror-2.02/mode',
			                    pathExtensions : 'CodeMirror-2.02/lib/util',
			                    flex : 1,
			                    value : ''
			                }
		                ]
		            }
		    ]
});

