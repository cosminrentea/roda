/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.filedetails.FileProperties', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.fileproperties',
    itemId: 'fileproperties',
    title:'File Properties',
	collapsible: true,
			dockedItems : [
		{
            xtype: 'toolbar',
            itemid: 'fileproptoolbar',
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
					'<tpl if="data.contenttype == \'image\'">',
					'<td><img src="/roda/j/thumbnail/alias/{data.alias}/x/150" width="150"></td>',
					'</tpl>',
					'<td>',
					'File: {data.filename} - {data.filesize}<br>',
					'Alias: {data.alias}<br>',
					'Filepath: {data.filepath}<br>',
					'File Type: {data.filetype}<br>',
					'Directory: {data.directory}<br>',
					'</td></tr></table>',
					'</tpl>'
	]
});

