/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.filedetails.FileProperties', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.fileproperties',
    itemId: 'fileproperties',
    title:'File Properties',
    id: 'fileproperties',
	collapsible: true,
			dockedItems : [
		{
            xtype: 'toolbar',
            itemid: 'fileproptoolbar',
            id : 'fileproptoolbar',
			dock : 'bottom',
			items : [
			{xtype: 'tbfill'},
					{
						xtype: 'button',
						itemId: 'editfile',
						text : 'Edit File',
						tooltip : 'Edit this file'
					},
					{
						xtype: 'button',
						itemId: 'deletefile',
						text : 'Delete File',
						tooltip : 'Deletes the file'
					},
					{
						xtype: 'button',
						id: 'getfileaudit',
						itemId: 'getfileaudit',
						text : 'File History',
						tooltip : 'Get File History'
					}]
		}],
	
    tpl : ['<tpl if="data.filetype == \'folder\'">',
					'Folder: {data.text} - {data.filesize}',
					'</tpl>',
					'<tpl if="data.filetype != \'folder\'">',
					'<table width:100%><tr>',
					'<td><img src="{data.fileurl}" width="150"></td>',
					'<td>',
					'File: {data.filename} - {data.filesize}<br>',
					'Alias: {data.alias}<br>',
					'Filepath: {data.filepath}<br>',
					'File Type: {data.filetype}<br>',
					'</td></tr></table>',
					'</tpl>'
	]
});

