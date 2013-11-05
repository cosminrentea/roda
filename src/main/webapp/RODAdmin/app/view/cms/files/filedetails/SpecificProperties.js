Ext.define('RODAdmin.view.cms.files.filedetails.SpecificProperties', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.filespecificproperties',
    itemId: 'filespecificproperties',
    title:'Specific Properties',
    id: 'filespecificproperties',
	collapsible: true,
    columns: [
        {
            text: 'Property',
            width: 100,
            dataIndex: 'name'
        },
        {
            text: 'Value',
            flex: 1,
            dataIndex: 'value'
        }
]	
});
