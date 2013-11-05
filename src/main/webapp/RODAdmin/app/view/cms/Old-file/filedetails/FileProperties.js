Ext.define('RODAdmin.view.cms.file.filedetails.FileProperties', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.fileproperties',
    itemId: 'fileproperties',
    title:'File Properties',
    id: 'fileproperties',
	collapsible: true,
    tpl : ['<tpl if="data.filetype == \'folder\'">',
					'Folder: {data.text} - {data.filesize}',
					'</tpl>',
					'<tpl if="data.filetype != \'folder\'">',
					'File: {data.text} - {data.filesize}',
					'<img src="{data.fileurl}" width="200">',
					'</tpl>'
	]
});

