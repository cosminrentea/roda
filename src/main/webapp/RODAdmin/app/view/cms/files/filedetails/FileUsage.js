/**
 * 
 */
Ext.define('RODAdmin.view.cms.files.filedetails.FileUsage', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.fileusage',
    itemId: 'fileusage',
    title:'File Usage',
	collapsible: true,
	   columns: [
        {
            text: 'Property',
            width: 100,
            dataIndex: 'type'
        },
        {
            text: 'Value',
            flex: 1,
            dataIndex: 'text'
        }
	]	
});
