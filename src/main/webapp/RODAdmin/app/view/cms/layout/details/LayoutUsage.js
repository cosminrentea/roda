/**
 * 
 */
Ext.define('RODAdmin.view.cms.layout.details.LayoutUsage', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.layoutusage',
    itemId: 'layoutusage',
    title:'Layout Usage',
//    id: 'layoutusage',
	collapsible: true,
	   columns: [
        {
            text: 'Page Id',
            width: 100,
            dataIndex: 'id'
        },
        {
            text: 'Title',
            flex: 1,
            dataIndex: 'name'
        },
        {
            text: 'Url',
            flex: 1,
            dataIndex: 'url'
        },
        {
            text: 'Language',
            xtype: 'templatecolumn',            
            flex: 1,
			tpl: '<div class="lang_{lang}">&nbsp;</div>',
            dataIndex: 'lang'
        },
        {
            text: 'Visible',
            flex: 1,
            dataIndex: 'visible'
        },
        {
            text: 'Page Type',
            flex: 1,
            dataIndex: 'pagetype'
        }
        
        ]	
});
