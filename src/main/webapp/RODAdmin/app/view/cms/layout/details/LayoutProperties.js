/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.details.LayoutProperties', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.layoutproperties',
    itemId: 'layoutproperties',
    title:'Layout Properties',
//    id: 'fileproperties',
	collapsible: true,
			dockedItems : [
		{
            xtype: 'toolbar',
            itemid: 'lyproptoolbar',
            id : 'lyproptoolbar',
			dock : 'bottom',
			items : [
			{xtype: 'tbfill'},
					{
						xtype: 'button',
//						id: 'editfile',
						itemId: 'editlayout',
						text : 'Edit',
						tooltip : 'Edit this layout'
					},
					{
						xtype: 'button',
//						id: 'deletefile',
						itemId: 'deletelayout',
						text : 'Delete',
						tooltip : 'Deletes the layout'
					},
					{
						xtype: 'button',
						itemId: 'getlayoutaudit',
						text : 'Layout History',
						tooltip : 'Get Layout History'
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
					'<div style="padding:10px;">',
					'{data.content}',
					'</div>',
					'</tpl>'
	]
});

