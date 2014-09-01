/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.details.LayoutProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.layoutproperties',
    itemId : 'layoutproperties',
    title : 'Layout Properties',
    width : "100%",
    height : "100%",
    layout : 'border',
    collapsible : true,
    dockedItems : [
	    {
	        xtype : 'toolbar',
	        itemId : 'lyproptoolbar',
	        dock : 'top',
	        items : [
	                {
		                xtype : 'tbfill'
	                }, {
	                    xtype : 'button',
	                    itemId : 'editlayout',
	                    text : 'Edit',
	                    tooltip : 'Edit this layout'
	                }, {
	                    xtype : 'button',
	                    itemId : 'deletelayout',
	                    text : 'Delete',
	                    tooltip : 'Deletes the layout'
	                }, {
	                    xtype : 'button',
	                    itemId : 'getlayoutaudit',
	                    text : 'Layout History',
	                    tooltip : 'Get Layout History'
	                }
	        ]
	    }
    ],
    items : [
            {
                region : 'center',
                collapsible : false,
                width : '30%',
                split : true,
                xtype : 'panel',
                itemId : 'lydata',
                flex : 1,
                tpl : [
                        '<tpl if="data.filetype == \'folder\'">', '<h1> {data.name}', '</tpl>',
                        '<tpl if="data.filetype != \'folder\'">', '<H1>{data.name}</H1>', '<H2>Folder: {data.directory}</H2>',
                        '<div style="padding:10px;">', '{data.description}', '</div>', '</tpl>'
                ]
            }, {
                region : 'east',
                collapsible : true,
                resizable : true,
                width : '70%',
                split : true,
                itemId : 'lyenvelope',
                flex : 3,
                layout : 'fit',
                xtype : 'panel',
                items : [
	                {
	                    xtype : 'codemirror',
	                    itemId : 'lycontent',	                    
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
